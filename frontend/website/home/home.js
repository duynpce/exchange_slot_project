const avatarBtn = document.getElementById("dropdownAvatar");
const dropdownMenu = document.getElementById("dropdownMenu");
const member_welcome = document.getElementById("member_welcome");
const logout = document.getElementById("logout");
const API_URL = "https://exchangeslot-api-testing.onrender.com/api/v1/exchange";
const tableBody = document.getElementById("results_table_body");
const prevBtn = document.getElementById("prev_page_btn");
const nextBtn = document.getElementById("next_page_btn");
const pageInfo = document.getElementById("page_info");

let currentPage = 1;
let totalPages = 1;
const limit = 10; // Số lượng mục trên mỗi trang
const now = new Date();
const hour = now.getHours();

document.getElementById("Add").onclick = function openMenu() {
  document.getElementById("addMenu").style.display = "block";
};

document.getElementById("closeMenu").onclick = function closeMenu() {
  document.getElementById("addMenu").style.display = "none";
};

document.getElementById("Menu_submit").onclick = function closeMenu() {
  if (form.checkValidity()) {
    document.getElementById("addMenu").style.display = "none";
  } else {
    form.reportValidity();
  }
};

avatarBtn.addEventListener("click", () => {
  dropdownMenu.style.display =
    dropdownMenu.style.display === "block" ? "none" : "block";
});

document.addEventListener("click", (e) => {
  if (!e.target.closest(".user_menu")) {
    dropdownMenu.style.display = "none";
  }
});

window.onload = () => {
  const username = localStorage.getItem("username");

  if (username) {
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
  } else {
    member_welcome.textContent = "Welcome";
  }
};

logout.addEventListener("click", () => {
  localStorage.removeItem("token");
  localStorage.removeItem("username");
  window.location.href = "../home/home.html";
  alert("Logout Successfully");
});

/**
 * Hàm gọi API để lấy danh sách yêu cầu trao đổi theo trang
 * @param {number} page - Số trang cần lấy dữ liệu
 */
async function fetchExchangeData(page) {
  // Hiển thị trạng thái đang tải (tùy chọn)
  tableBody.innerHTML =
    '<tr><td colspan="4" style="text-align:center;">Loading...</td></tr>';

  try {
    const response = await fetch(`${API_URL}?page=${page}&limit=${limit}`);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Failed to fetch exchange data:", error);
    tableBody.innerHTML =
      '<tr><td colspan="4" style="text-align:center; color:red;">Failed to load data.</td></tr>';
    return null;
  }
}

/**
 * Hàm hiển thị dữ liệu lấy được từ API ra bảng
 * @param {Array} data - Mảng dữ liệu các yêu cầu
 */
function displayData(data) {
  // Xóa nội dung cũ
  tableBody.innerHTML = "";

  if (!data || data.length === 0) {
    tableBody.innerHTML =
      '<tr><td colspan="4" style="text-align:center;">No data available.</td></tr>';
    return;
  }

  data.forEach((item) => {
    const contactInfo = `
            ${item.contact.email ? `Email: ${item.contact.email}<br>` : ""}
            ${item.contact.zalo ? `Zalo: ${item.contact.zalo}<br>` : ""}
            ${
              item.contact.facebook
                ? `Facebook: <a href="${item.contact.facebook}" target="_blank">Link</a>`
                : ""
            }
        `;

    const row = `
            <tr>
                <td>${item.username}</td>
                <td>${item.currentSlot}</td>
                <td>${item.exchangeSlot}</td>
                <td>${contactInfo}</td>
            </tr>
        `;
    tableBody.innerHTML += row;
  });
}

/**
 * Cập nhật trạng thái của các nút điều khiển pagination
 */
function updatePaginationControls() {
  pageInfo.textContent = `Page ${currentPage} of ${totalPages}`;
  prevBtn.disabled = currentPage === 1;
  nextBtn.disabled = currentPage === totalPages;
}

async function loadPage(page) {
  const responseData = await fetchExchangeData(page);

  if (responseData && responseData.data) {
    currentPage = page;
    totalPages = responseData.pagination.totalPages;

    displayData(responseData.data);
    updatePaginationControls();
  }
}

prevBtn.addEventListener("click", () => {
  if (currentPage > 1) {
    loadPage(currentPage - 1);
  }
});

nextBtn.addEventListener("click", () => {
  if (currentPage < totalPages) {
    loadPage(currentPage + 1);
  }
});

window.addEventListener("load", () => {
  loadPage(1);
});

//API ADD REQUEST
//API DELETE REQUEST
//API GET LIST
