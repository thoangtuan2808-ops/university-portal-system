document.addEventListener('DOMContentLoaded', () => {
    
    // ==========================================
    // 1. AUTH GUARD - CHỐT CHẶN BẢO VỆ TRANG CỦA SINH VIÊN
    // ==========================================
    const token = localStorage.getItem("userToken");
    // Ép kiểu về chuỗi để đảm bảo so sánh chính xác tuyệt đối
    const roleId = String(localStorage.getItem("userRoleId")); 
    const userName = localStorage.getItem("userName");

    // Nếu không có thẻ bài, hoặc không phải Sinh viên (Role 3) -> Đá văng!
    if (!token || roleId !== "3") {
        alert("Bạn chưa đăng nhập hoặc không có quyền truy cập trang này!");
        window.location.href = "index.html"; 
        return; // Lệnh return này cực kỳ quan trọng, nó chặn không cho các đoạn code bên dưới chạy tiếp
    }

    // ==========================================
    // 2. CẬP NHẬT THÔNG TIN CÁ NHÂN LÊN GIAO DIỆN
    // ==========================================
    const studentNameEl = document.querySelector(".student-name");
    const studentIdEl = document.querySelector(".student-id");
    
    // Thay thế tên giả định "Nguyễn Văn A" bằng tài khoản thật
    if (studentNameEl) studentNameEl.textContent = userName; 
    if (studentIdEl) studentIdEl.textContent = "Tài khoản: " + userName;

    // ==========================================
    // 3. LOGIC GIAO DIỆN (UI)
    // ==========================================
    
    // --- 3.1 Sidebar Toggle Logic ---
    const sidebar = document.getElementById('sidebar');
    const toggleSidebarBtn = document.getElementById('toggleSidebarBtn');
    
    if (toggleSidebarBtn && sidebar) {
        toggleSidebarBtn.addEventListener('click', () => {
            sidebar.classList.toggle('collapsed');
        });
    }

    if (sidebar && window.innerWidth <= 768) {
        sidebar.classList.add('collapsed');
    }

    // --- 3.2 Scroll to Top Button Logic ---
    const scrollTopBtn = document.getElementById('scrollTopBtn');
    const pageWrapper = document.getElementById('pageWrapper'); 

    if (scrollTopBtn && pageWrapper) {
        scrollTopBtn.style.opacity = '0';
        scrollTopBtn.style.pointerEvents = 'none';
        scrollTopBtn.style.transition = 'opacity 0.3s ease';

        pageWrapper.addEventListener('scroll', () => {
            if (pageWrapper.scrollTop > 300) {
                scrollTopBtn.style.opacity = '1';
                scrollTopBtn.style.pointerEvents = 'auto';
            } else {
                scrollTopBtn.style.opacity = '0';
                scrollTopBtn.style.pointerEvents = 'none';
            }
        });

        scrollTopBtn.addEventListener('click', () => {
            pageWrapper.scrollTo({
                top: 0,
                behavior: 'smooth'
            });
        });
    }

    // --- 3.3 Profile Dropdown Toggle ---
    const userProfileBtn = document.getElementById('userProfileBtn');
    const profileDropdown = document.getElementById('profileDropdown');

    if(userProfileBtn && profileDropdown) {
        // Toggle the dropdown when clicking the avatar
        userProfileBtn.addEventListener('click', (e) => {
            e.stopPropagation(); // Ngăn chặn sự kiện click lan ra ngoài body
            profileDropdown.classList.toggle('show');
        });

        // Đóng dropdown khi click ra ngoài bất kỳ đâu trên màn hình
        window.addEventListener('click', (e) => {
            if (!userProfileBtn.contains(e.target) && !profileDropdown.contains(e.target)) {
                if (profileDropdown.classList.contains('show')) {
                    profileDropdown.classList.remove('show');
                }
            }
        });
    }

    // --- 3.4 Logic xử lý nút Đăng xuất ---
    // --- 3.4 Logic xử lý nút Đăng xuất (ĐÃ NÂNG CẤP) ---
    const logoutBtn = document.querySelector('.logout-btn a');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', async (e) => {
            e.preventDefault(); // Chặn hành vi chuyển trang mặc định
            
            if(confirm("Bạn có chắc chắn muốn đăng xuất?")) {
                const roleId = localStorage.getItem("userRoleId");

                try {
                    // 1. Gửi tín hiệu báo Đăng xuất cho Server (để trừ số lượng)
                    if (roleId) {
                        await fetch("http://localhost:8080/UniversityPortalBackend/api/logout", {
                            method: "POST",
                            headers: {
                                "Content-Type": "application/x-www-form-urlencoded"
                            },
                            body: new URLSearchParams({
                                "roleId": roleId
                            })
                        });
                    }
                } catch (error) {
                    console.error("Lỗi khi báo đăng xuất cho Server:", error);
                } finally {
                    // 2. Dù Server có nhận được hay không, vẫn phải xóa thẻ bài ở máy cá nhân
                    localStorage.clear(); 
                    
                    // 3. Đá văng ra cửa ngoài
                    window.location.href = "index.html"; 
                }
            }
        });
    }
    // 4. LOGIC LẤY THỐNG KÊ TRUY CẬP THỰC TẾ
    // ==========================================
    async function fetchStatistics() {
        try {
            const response = await fetch("http://localhost:8080/UniversityPortalBackend/api/stats", {
                method: "GET"
            });
            
            const result = await response.json();
            
            // Cập nhật số liệu lên giao diện
            const statTotal = document.getElementById('stat-total');
            const statStudent = document.getElementById('stat-student');
            const statTeacher = document.getElementById('stat-teacher');
            
            if (statTotal) statTotal.textContent = result.total;
            if (statStudent) statStudent.textContent = result.students;
            if (statTeacher) statTeacher.textContent = result.teachers;
            
        } catch (error) {
            console.error("Không thể lấy dữ liệu thống kê:", error);
        }
    }
    // Gọi lần đầu tiên khi trang vừa load xong
    fetchStatistics();
    // Thiết lập tự động lấy lại dữ liệu sau mỗi 10 giây (10000 ms)
    setInterval(fetchStatistics, 10000); 
});
