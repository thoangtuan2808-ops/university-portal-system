document.addEventListener('DOMContentLoaded', () => {
    
    // --- 9.1 Sidebar Toggle Logic ---
    const sidebar = document.getElementById('sidebar');
    const toggleSidebarBtn = document.getElementById('toggleSidebarBtn');
    if(toggleSidebarBtn && sidebar){ // Đã an toàn
        toggleSidebarBtn.addEventListener('click', () => {
            sidebar.classList.toggle('collapsed');
        });
    }
    if (sidebar && window.innerWidth <= 768) {
        sidebar.classList.add('collapsed');
    }

    // --- 9.2 Password Visibility Toggle Logic ---
    const password = document.getElementById('password');
    const togglePasswordBtn = document.getElementById('togglePassword');
    
    // BỌC IF BẢO VỆ Ở ĐÂY
    if (togglePasswordBtn && password) { 
        const toggleIcon = togglePasswordBtn.querySelector('i');
        togglePasswordBtn.addEventListener('click', () => {
            const isPassword = password.getAttribute('type') === 'password';
            password.setAttribute('type', isPassword ? 'text' : 'password');
            if (isPassword) {
                toggleIcon.classList.remove('fa-eye');
                toggleIcon.classList.add('fa-eye-slash');
            } else {
                toggleIcon.classList.remove('fa-eye-slash');
                toggleIcon.classList.add('fa-eye');
            }
        });
    }

    // --- 9.3 Scroll to Top Button Logic (CẬP NHẬT) ---
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

    // --- Form Submit Dummy Prevention ---
    const loginForm = document.getElementById('loginForm');
    if(loginForm){ // Đã an toàn
        loginForm.addEventListener('submit', async(e)=>{
           e.preventDefault();
            const username = document.getElementById('username').value.trim();
            const password = document.getElementById('password').value.trim();
            console.log("Đang gửi yêu cầu xác thực...");
            try {
                const reponse = await fetch("http://localhost:8080/UniversityPortalBackend/api/login", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    body: new URLSearchParams({
                        "username": username,
                        "password": password
                    })
                });
                const result = await reponse.json();
                if(result.status==="success"){
                    localStorage.setItem("userToken", result.token);
                    localStorage.setItem("userRoleId", result.roleId);
                    localStorage.setItem("userName", username);
                    alert("Đăng nhập thành công!");
                    if(result.roleId===3){
                        window.location.href="student.html";
                    }
                    else if (result.roleId===2){
                        window.location.href="teacher.html";
                    }
                    else{
                        alert("Giao diên web hiện tại chỉ dành riêng cho giảng viên/sinh vien");
                        localStorage.clear();
                    }
                } else {
                    alert(result.message || "Đăng nhập thất bại. Vui lòng kiểm tra lại!");
                }
            } catch (error) {
                console.error("Lỗi kết nối API: ", error);
                alert("Lỗi hệ thống. Không thể kết nối đến máy chủ Apache Tomcat!");
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



