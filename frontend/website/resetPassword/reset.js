import { login_api, reset_password_api } from "../../apiconfig.js";

//TOGGLE PASSWORD//
document.querySelectorAll(".togglePassword").forEach(icon => {
    icon.addEventListener("click", () => {
        console.log("Click icon mắt", icon); // test
        const wrapper = icon.closest(".reset_row");
        if (!wrapper) return;

        const input = wrapper.querySelector(".password_input");
        if (!input) return;

        input.type = input.type === "password" ? "text" : "password";

        wrapper.querySelectorAll(".togglePassword").forEach(i =>
                i.classList.toggle("hidden")
        );
    });
});
//CHECK PASSWORD API
const authentication = document.getElementById("authentication");
async function check_password() {
    const userName = document.getElementById("userNameInput").value.trim();
    const oldPassword = document.getElementById("oldInput").value.trim();

    try{
        const res = await fetch(login_api, {
            method: POST,
            headers: {
                "Content-type": "application/json",
                "ngrok-skip-browser-warning": "69420" // nếu dùng ngrok
            },
            body: JSON.stringify({
                userName: userName,
                password: oldPassword
            })
        });

        const data = await res.json();
        if(res.ok && data.message === "login sucess"){
            document.getElementById("newInput").disabled = false;
            document.getElementsByClassName("confirmNewInput").disabled = false;
            authentication.textContent = "Verifying Success!";
            authentication.style.color = "lime";
        }
        else{
            authentication.textContent = "Fail to verify user!";
            authentication.style.color = "tomato";
        }
    }
    catch(error){
        console.error("Reset password error:", error);
        alert("Cannot connect to server");
    }

}
//RESET PASSWORD API
async function reset_password() {
    const userName = document.getElementById("userNameInput").value.trim();
    const newPassword = document.getElementById("confirmNewInput").value.trim();

    try{
        const res = await fetch(reset_password_api, {
            method: "PATCH",
            headers: {
                "Content-type": "application/json",
                "ngrok-skip-browser-warning": "69420" // nếu dùng ngrok
            },
            body: JSON.stringify({
                userName: userName,
                password: newPassword
            })
        });

        const data = await res.json();
        if(res.ok){
            alert(data.message || "Change password successfully!");
            window.location.href = "../login/login.html";
        }
        else{
            alert(data.error || "Fail to change password");
        }
    }

    catch(error){
        console.error("Reset password error:", error);
        alert("Cannot connect to server");
    }
}
//CHECK VALID PASSWORD
function checkPassword(password){
    let alpha;
    let lower;
    let num;
    let special;
    for(let i = 0;i < password.length; i++){
        if(password[i] <= 'Z' && password[i] >= 'A')
            alpha = true;
        else if(password[i] <= 'z' && password[i] >= 'a')
            lower= true;
        else if(password[i] <= '9' && password[i] >= '0')
            num = true;
        else
            special = true;
        
        
        if(alpha && lower && num && special)
            return true;
    }
    return false;
}

//MAIN
document.getElementById("checkPassword").addEventListener("click", () => {
    check_password();
})

document.getElementById("form").addEventListener("submit", (e) => {
    e.preventDefault();

    const password = document.getElementById("newInput").value.trim();
    const password_typo = document.getElementById("password_typo");
    const password_error = document.getElementById("password_error");

    if(password.length < 8){
        password_typo.innerHTML = '<i class="fa-solid fa-circle-xmark"></i> <b>Password is too short!</b>';
        password_typo.style.color = "tomato";
    }
    else if(password.length > 32){
        password_typo.innerHTML = '<i class="fa-solid fa-circle-xmark"></i> <b>Password is too long!</b>';
        password_typo.style.color = "tomato";
    }
    else{
        if(checkPassword(password)){
            if(password === confirm_password){
                password_error.innerHTML = '<i class="fa-solid fa-circle-check"></i> <b>Set Password successfully</b>';
                password_error.style.color = "lime";
                password_typo.innerHTML ='';
                reset_password();
            }
            else{
                password_error.innerHTML = '<i class="fa-solid fa-circle-xmark"></i> <b>Password does not match!</b>';
                password_error.style.color = "tomato";
                password_typo.innerHTML ='';
            }
        }
        else{
            password_typo.innerHTML = '<i class="fa-solid fa-circle-xmark"></i> <b>Password does not meet requirements</b>';
            password_typo.style.color = "tomato";
        }
    }
});

