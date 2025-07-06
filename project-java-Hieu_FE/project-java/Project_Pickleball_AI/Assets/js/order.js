// assets/js/order.js
document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('orderForm');
    
    if (form) {
        form.addEventListener('submit', function(event) {
            event.preventDefault(); // Ngăn chuyển hướng mặc định
            
            // Kiểm tra tính hợp lệ của form
            if (form.checkValidity()) {
                // Lấy dữ liệu từ form
                const formData = {
                    fullname: document.getElementById('fullname').value,
                    phone: document.getElementById('phone-number').value,
                    email: document.getElementById('email').value,
                    address: document.getElementById('address').value,
                    reason: document.getElementById('contact-reason').value,
                    message: document.getElementById('message').value,
                    timestamp: new Date().toISOString()
                };
                
                // In dữ liệu ra console để kiểm tra
                console.log('Form Data:', formData);
                
                // Hiển thị alert mặc định
                alert('Form submitted successfully!');
                
                // Xóa form sau khi gửi
                form.reset();
            } else {
                // Hiển thị alert lỗi nếu thiếu trường
                alert('Please fill out all required fields.');
            }
        });
    } else {
        console.error('Form with ID "orderForm" not found.');
    }
});