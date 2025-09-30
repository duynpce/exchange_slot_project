const avatarBtn = document.getElementById("dropdownAvatar");
const dropdownMenu = document.getElementById("dropdownMenu");
const member_welcome = document.getElementById("member_welcome");
const logout = document.getElementById("logout");

const now = new Date();
const hour = now.getHours();

document.getElementById("Add").onclick = function openMenu(){
    document.getElementById("addMenu").style.display = "block";
}

document.getElementById("closeMenu").onclick = function closeMenu(){
    document.getElementById("addMenu").style.display = "none";
}

document.getElementById("Menu_submit").onclick = function closeMenu(){
    if(form.checkValidity()) {
        document.getElementById("addMenu").style.display = "none";
    } 
    else{
        form.reportValidity();
    }
}

avatarBtn.addEventListener("click", () => {
    dropdownMenu.style.display =
        dropdownMenu.style.display === "block" ? "none" : "block";
});

document.addEventListener("click", (e) => {
    if(!e.target.closest(".user_menu")){
        dropdownMenu.style.display = "none";
    }
});

window.onload = () => {
    const username = localStorage.getItem("username");

    if(username){
        if(hour >= 0 && hour <= 4)
            member_welcome.textContent = "Good Night, " + username + "!";
        else if(hour >= 5 && hour <= 10)
            member_welcome.textContent = "Have a nice day, " + username + "!";
        else if(hour >=11 && hour <= 12)
            member_welcome.textContent = "Happy Midday, " + username + "!";
        else if(hour >= 13 && hour <= 18)
            member_welcome.textContent = "Good Afternoon, " + username + "!";
        else if(hour >=19 && hour <= 21)
            member_welcome.textContent = "Good Evening, " + username + "!";
        else
            member_welcome.textContent = "Good Night, " + username + "!";
    }
    else{
        member_welcome.textContent = "Welcome";
    }
}

logout.addEventListener("click", () =>{
    localStorage.removeItem("token");
    localStorage.removeItem("username");
    window.location.href = "../home/home.html";
    alert("Logout Successfully");
});
//API ADD REQUEST
//API DELETE REQUEST
//API GET LIST