document.addEventListener("DOMContentLoaded", function() {
    // Nút Edit/Save/Cancel
    const editBtn = document.getElementById("editBtn");
    const saveBtn = document.getElementById("saveBtn");
    const cancelBtn = document.getElementById("cancelBtn");

    // Teacher Information
    const teacherInfoView = document.getElementById("teacherInfoView");
    const teacherInfoEdit = document.getElementById("teacherInfoEdit");

    // Block Add Certification
    const addCertBlock = document.getElementById("addCertBlock");
    const showAddCertBtn = document.getElementById("showAddCertBtn");
    const addCertForm = document.getElementById("addCertForm");
    const cancelAddCertBtn = document.getElementById("cancelAddCertBtn");

    // Avatar
    const avatarInput = document.getElementById('avatarInput');
    const avatarPreview = document.getElementById('avatarPreview');
    const profilePictureField = document.getElementById('profilePictureField');
    const triggerFileInput = document.getElementById('triggerFileInput');
    const fileInput = document.getElementById('fileInput');

    // Khi vào trang: ẩn Add Certification, Teacher Info edit, trường upload avatar
    if(addCertBlock) addCertBlock.style.display = "none";
    if(addCertForm) addCertForm.style.display = "none";
    if(teacherInfoEdit) teacherInfoEdit.style.display = "none";
    if(saveBtn) saveBtn.style.display = "none";
    if(cancelBtn) cancelBtn.style.display = "none";
    if(profilePictureField) profilePictureField.style.display = "none";

    // Khi bấm Edit Profile: hiện Add Certification, Teacher Info edit, trường upload avatar
    if(editBtn) editBtn.onclick = function(e) {
        e.preventDefault();
        if(addCertBlock) addCertBlock.style.display = "block";
        if(teacherInfoView) teacherInfoView.style.display = "none";
        if(teacherInfoEdit) teacherInfoEdit.style.display = "block";
        if(profilePictureField) profilePictureField.style.display = "block";
        editBtn.style.display = "none";
        if(saveBtn) saveBtn.style.display = "inline-block";
        if(cancelBtn) cancelBtn.style.display = "inline-block";
    };

    // Khi bấm Cancel Profile: ẩn Add Certification, Teacher Info edit, trường upload avatar
    if(cancelBtn) cancelBtn.onclick = function() {
        if(addCertBlock) addCertBlock.style.display = "none";
        if(addCertForm) addCertForm.style.display = "none";
        if(teacherInfoView) teacherInfoView.style.display = "block";
        if(teacherInfoEdit) teacherInfoEdit.style.display = "none";
        if(profilePictureField) profilePictureField.style.display = "none";
        if(editBtn) editBtn.style.display = "inline-block";
        if(saveBtn) saveBtn.style.display = "none";
        cancelBtn.style.display = "none";
        // Reset preview nếu cần
        if(avatarInput) avatarInput.value = "";
    };

    // Khi bấm Add Certification: hiện form
    if(showAddCertBtn) showAddCertBtn.onclick = function(e) {
        e.preventDefault();
        if(addCertForm) addCertForm.style.display = "block";
        showAddCertBtn.style.display = "none";
    };

    // Khi bấm Cancel trong form: ẩn form
    if(cancelAddCertBtn) cancelAddCertBtn.onclick = function() {
        if(addCertForm) addCertForm.style.display = "none";
        if(showAddCertBtn) showAddCertBtn.style.display = "inline-block";
        if(addCertForm) addCertForm.reset();
    };

    // Avatar preview khi chọn file trong form edit
    if (avatarInput && avatarPreview) {
        avatarInput.addEventListener('change', function() {
            if (this.files && this.files[0]) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    avatarPreview.src = e.target.result;
                };
                reader.readAsDataURL(this.files[0]);
            }
        });
    }

    // Avatar preview khi chọn file bằng nút camera (ngoài form)
    if (triggerFileInput && fileInput && avatarPreview) {
        triggerFileInput.addEventListener('click', function(e) {
            e.preventDefault();
            fileInput.click();
        });
        fileInput.addEventListener('change', function() {
            if (this.files && this.files[0]) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    avatarPreview.src = e.target.result;
                };
                reader.readAsDataURL(this.files[0]);
            }
        });
    }
      const toggleBtn = document.getElementById('toggleBookingList');
    const bookingList = document.getElementById('bookingListContainer');
    let isShown = false;
    if (toggleBtn && bookingList) {
        toggleBtn.onclick = function() {
            isShown = !isShown;
            bookingList.style.display = isShown ? 'block' : 'none';
            toggleBtn.textContent = isShown ? 'Hide Booking List' : 'Show Booking List';
        };
    }

    // Sửa nội dung booking (ví dụ đơn giản)
    document.querySelectorAll('.edit-booking-btn').forEach(btn => {
        btn.onclick = function() {
            alert('Chức năng sửa booking! (Bạn có thể mở form edit ở đây)');
            // Ở đây bạn có thể mở modal hoặc form để sửa booking
        };
    });
    document.querySelectorAll('.toggle-visibility-btn').forEach(btn => {
    btn.onclick = function() {
        if (btn.textContent === "Hide") {
            btn.textContent = "Show";
            // Gọi API hoặc xử lý ẩn booking ở trang khác tại đây
        } else {
            btn.textContent = "Hide";
            // Gọi API hoặc xử lý hiện booking ở trang khác tại đây
        }
    };
});
});