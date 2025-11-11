import { account_api } from "../../utils/apiconfig.js";
import { isTokenExpired } from "../../utils/isTokenExpired.js";

const token = localStorage.getItem("accessToken");
const member_welcome = document.getElementById("member_welcome");
const now = new Date();

const newStudentCode = document.getElementById("studentCodeInput");
const newClassCode = document.getElementById("currentClassInput");

function formatDate(){

    const days = ["Sun","Mon","Tue","Wed","Thu","Fri","Sat"];
    const months = ["January","February","March","April","May","June","July","August","September","October","November","December"];

    const dayName = days[now.getDay()];
    const day = String(now.getDate()).padStart(2, '0');
    const month = months[now.getMonth()];
    const year = now.getFullYear();

    return `${dayName}, ${month} ${day} ${year}`;
}
async function getProfile() {
    try{
        const res = await fetch(account_api);

        const data = res.json();
        
        console.log(data);
        localStorage.setItem("id", data.id);
        localStorage.setItem("classCode", data.classCode);
        localStorage.setItem("studentCode", data.studentCode);
        localStorage.setItem("accountName", data.accountName);
        localStorage.setItem("role", data.role);
        
    }
    catch(error){
        console.log(error);
    }
}
window.onload = () => {
    document.getElementById("date").textContent = formatDate();
    const hour = now.getHours();
    const expired = isTokenExpired(token);
    if(expired || !token){
        member_welcome.textContent = "Welcome, please log in first!";
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
        localStorage.removeItem("username");
        document.getElementById("login_required").style.display = "block";
    }
    else{
        const username = localStorage.getItem("username");
        if(username){
            if (hour >= 0 && hour <= 4)
                member_welcome.textContent = "Good Night, " + username + "!";
            else if (hour >= 5 && hour <= 10)
                member_welcome.textContent = "Have a nice day, " + username + "!";
            else if (hour >= 11 && hour <= 12)
                member_welcome.textContent = "Happy Midday, " + username + "!";
            else if (hour >= 13 && hour <= 18)
                member_welcome.textContent = "Good Afternoon, " + username + "!";
            else if (hour >= 19 && hour <= 21)
                member_welcome.textContent = "Good Evening, " + username + "!";
            else member_welcome.textContent = "Good Night, " + username + "!";
        }
        document.getElementById("username").textContent = username;
        document.getElementById("login_required").style.display = "none";
        getProfile();
    }
}

//=========================CHANGE INFORMATION===================
async function changeInformation(newStudentCode, newClassCode){
    try{
        const res = await fetch(account_api, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            },
            body: JSON.stringify({newStudentCode, newClassCode})
        })

        const data = await res.json();
        if(res.status === 200){
            alert("Change information successfully");
        }
        else if(res.status === 400 ){
            alert(data.error +": " + data,message);
        }
    }catch(error){
        console.log(error);
        alert("Cannot connect to server");
    }
}

const editBtn = document.getElementById("edit");
const editConfirmBtn = document.getElementById("edit_confirm");
const editCancelBtn = document.getElementById("edit_cancel");

editBtn.addEventListener("click" , () => {
    newStudentCode.disabled = false;
    newClassCode.disabled = false;
    editBtn.innerHTML = `<i class="fa-solid fa-hammer"></i>Editing...`;
    editBtn.classList.add("lock");
    document.getElementById("information_button").style.opacity = "1";
})

editConfirmBtn.addEventListener("click", () => {
    newClassCode.disabled = true;
    newStudentCode.disabled = true;
    editBtn.innerHTML = `<i class="fa-solid fa-hammer"></i>Edit`;
    editBtn.classList.remove("lock");
    document.getElementById("information_button").style.opacity = "0";
    newClassCode.value = localStorage.getItem("classCode");
    newStudentCode.value= localStorage.getItem("studentCode");
    changeInformation(newStudentCode.value, newClassCode.value);
})
editCancelBtn.addEventListener("click", () => {
    newClassCode.disabled = true;
    newStudentCode.disabled = true;
    editBtn.innerHTML = `<i class="fa-solid fa-hammer"></i>Edit`;
    editBtn.classList.remove("lock");
    document.getElementById("information_button").style.opacity = "0";
    newClassCode.value = localStorage.getItem("classCode");
    newStudentCode.value= localStorage.getItem("studentCode");
})