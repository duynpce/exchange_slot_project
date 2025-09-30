const openPass = document.getElementById("openPass");
const closePass = document.getElementById("closePass");
const passwordInput = document.getElementById("password_type");

openPass.addEventListener("click", () => {
    openPass.style.display = "none";
    closePass.style.display = "block";
    passwordInput.type = "text";
});

closePass.addEventListener("click", () => {
    openPass.style.display = "block";
    closePass.style.display = "none";
    passwordInput.type = "password";
});

async function login(username, password) {
    try {
        const res = await fetch("/account/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                 //ngrok thì giữ header này
                "ngrok-skip-browser-warning": "69420",
            },
            body: JSON.stringify({
                userName: username,
                password: password
            })
        });

        if (!res.ok) {
            const errData = await res.json();
            alert(errData.error || "Login failed");
            return;
        }

        const data = await res.json();
        if (data.accessToken && data.refreshToken) {
            alert(data.message || "Login successfully!");
            localStorage.setItem("accessToken", data.accessToken);
            localStorage.setItem("refreshToken", data.refreshToken);
            localStorage.setItem("username", username);
            window.location.href = "../home/home.html";
        } else {
            alert(data.message || "Login failed, please check username or password");
        }
    } catch (error) {
        console.error("Login Error:", error);
        alert("Cannot connect to server!");
    }
}

document.querySelector("form").addEventListener("submit", (e) => {
    e.preventDefault();

    const username = document.getElementById("username").value.trim();
    const password = passwordInput.value.trim();

    if (!username || !password) {
        alert("Please enter username and password!");
        return;
    }
    login(username, password);
});
