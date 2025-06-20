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

document.addEventListener('DOMContentLoaded', function() {
    const onlineBtn = document.getElementById('online');
    const offlineBtn = document.getElementById('offline');
    const onlineAppointments = document.getElementById('online-appointments');
    const offlineAppointments = document.getElementById('offline-appointments');

    onlineBtn.addEventListener('click', function() {
        onlineBtn.classList.add('active');
        offlineBtn.classList.remove('active');
        onlineAppointments.style.display = 'block';
        offlineAppointments.style.display = 'none';
    });

    offlineBtn.addEventListener('click', function() {
        offlineBtn.classList.add('active');
        onlineBtn.classList.remove('active');
        offlineAppointments.style.display = 'block';
        onlineAppointments.style.display = 'none';
    });
});