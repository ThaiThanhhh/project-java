document.getElementById('triggerFileInput').addEventListener('click', function() {
    if (document.getElementById('fileInput').disabled) {
        console.log("Avatar replacement is already done.");
        return;
    }
    document.getElementById('fileInput').click();
});

document.getElementById('fileInput').addEventListener('change', function(e) {
    if (e.target.files.length > 0 && !this.disabled) {
        const file = e.target.files[0];
        const reader = new FileReader();
        reader.onload = function(event) {
            document.getElementById('preview').src = event.target.result;
            document.getElementById('fileInput').disabled = true;
            document.getElementById('triggerFileInput').disabled = true;
            document.getElementById('triggerFileInput').style.opacity = '0.5';
            document.getElementById('triggerFileInput').style.cursor = 'not-allowed';
        };
        reader.readAsDataURL(file);
    }
});

// Thêm các hàm xử lý cho các nút "Add"
document.addEventListener('DOMContentLoaded', function() {
    // Xử lý avatar (code hiện có)
    document.getElementById('triggerFileInput').addEventListener('click', function() {
        if (document.getElementById('fileInput').disabled) {
            console.log("Avatar replacement is already done.");
            return;
        }
        document.getElementById('fileInput').click();
    });

    document.getElementById('fileInput').addEventListener('change', function(e) {
        if (e.target.files.length > 0 && !this.disabled) {
            const file = e.target.files[0];
            const reader = new FileReader();
            reader.onload = function(event) {
                document.getElementById('preview').src = event.target.result;
                document.getElementById('fileInput').disabled = true;
                document.getElementById('triggerFileInput').disabled = true;
                document.getElementById('triggerFileInput').style.opacity = '0.5';
                document.getElementById('triggerFileInput').style.cursor = 'not-allowed';
            };
            reader.readAsDataURL(file);
        }
    });

    // Xử lý nút Add Location
    document.querySelector('.detail-card:nth-child(4) .add-button').addEventListener('click', function() {
        const locationName = prompt("Enter new teaching location:");
        if (locationName && locationName.trim() !== '') {
            const locationsList = document.querySelector('.detail-card:nth-child(4) .locations-list');
            const newLocation = document.createElement('div');
            newLocation.className = 'location-item';
            newLocation.innerHTML = `<span>${locationName.trim()}</span>`;
            locationsList.appendChild(newLocation);
        }
    });

    // Xử lý nút Add Pricing
    document.querySelector('.detail-card:nth-child(5) .add-button').addEventListener('click', function() {
        const serviceName = prompt("Enter service name (e.g., Private Lesson):");
        if (!serviceName || serviceName.trim() === '') return;
        
        // Thêm đơn vị VND vào prompt để người dùng biết
        const price = prompt("Enter price in VND (e.g., 200000):");
        if (!price || price.trim() === '') return;
        
        // Kiểm tra nếu người dùng nhập số
        const priceNumber = parseFloat(price);
        if (isNaN(priceNumber)) {
            alert("Please enter a valid number for price");
            return;
        }
        
        // Định dạng số với dấu phân cách và thêm VND
        const formattedPrice = new Intl.NumberFormat('vi-VN').format(priceNumber) + ' VND';
        
        const pricingList = document.querySelector('.detail-card:nth-child(5) .pricing-list');
        const newPricing = document.createElement('div');
        newPricing.className = 'pricing-item';
        newPricing.innerHTML = `
            <span>${serviceName.trim()}:</span>
            <span>${formattedPrice}</span>
        `;
        pricingList.appendChild(newPricing);
    });

    // Xử lý nút Add Schedule
    document.querySelector('.detail-card:nth-child(6) .add-button').addEventListener('click', function() {
        const day = prompt("Enter day of week (e.g., Monday):");
        if (!day || day.trim() === '') return;
        
        const timeRange = prompt("Enter time range (e.g., 9:00 AM - 12:00 PM):");
        if (!timeRange || timeRange.trim() === '') return;
        
        const scheduleList = document.querySelector('.detail-card:nth-child(6) .schedule-list');
        const newSchedule = document.createElement('div');
        newSchedule.className = 'schedule-item';
        newSchedule.innerHTML = `<span>${day.trim()}: ${timeRange.trim()}</span>`;
        scheduleList.appendChild(newSchedule);
    });

    // Xử lý nút Save cho Teaching Methods
    document.querySelector('.save-button').addEventListener('click', function() {
        alert("Teaching methods saved successfully!");
    });
});