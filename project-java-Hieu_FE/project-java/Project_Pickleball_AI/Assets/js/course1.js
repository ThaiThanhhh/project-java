function toggleSection(header) {
    var content = header.parentElement.querySelector('.toggle-content');
    var icon = header.querySelector('.toggle-icon');
    if (content.style.display === 'none' || content.style.display === '') {
        content.style.display = 'block';
        icon.innerHTML = '&#9660;'; // Down arrow
    } else {
        content.style.display = 'none';
        icon.innerHTML = '&#9650;'; // Up arrow
    }
}

// Mặc định hiển thị tất cả toggle-content
document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('.toggle-content').forEach(function(content) {
        content.style.display = 'block';
    });
    document.querySelectorAll('.toggle-icon').forEach(function(icon) {
        icon.innerHTML = '&#9660;';
    });
});