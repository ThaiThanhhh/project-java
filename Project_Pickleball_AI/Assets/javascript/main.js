document.addEventListener('DOMContentLoaded', function() {
    const header = document.querySelector('.container__header');
    
    // Scroll effect
    window.addEventListener('scroll', function() {
    const header = document.querySelector('.container__header');
    if (header) {
        if (window.scrollY > 0) {
            header.classList.add('scrolled');
        } else {
            header.classList.remove('scrolled');
        }
    }
});
    
    // Mobile menu toggle (responsive - có thể thêm sau)
    const mobileMenuBtn = document.querySelector('.mobile-menu-btn');
    if (mobileMenuBtn) {
        mobileMenuBtn.addEventListener('click', function() {
            document.querySelector('.nav-container').classList.toggle('active');
        });
    }
    
    // Close dropdowns when clicking outside
    document.addEventListener('click', function(e) {
        if (!e.target.matches('.nav-link')) {
            const dropdowns = document.querySelectorAll('.dropdown-content');
            dropdowns.forEach(function(dropdown) {
                dropdown.style.display = 'none';
            });
        }
    });
});
// giup tinh toan thanh cuon va khong cho header che thanh cuon
document.addEventListener('DOMContentLoaded', function() {
    // Tính chiều rộng thanh cuộn
    const scrollbarWidth = window.innerWidth - document.documentElement.clientWidth;
    
    // Lưu giá trị vào biến CSS
    document.documentElement.style.setProperty('--scrollbar-width', `${scrollbarWidth}px`);
});