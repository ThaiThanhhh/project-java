document.addEventListener('DOMContentLoaded', () => {
    const videoFrame = document.getElementById('main-video');
    const lessonHeaders = document.querySelectorAll('.lesson-header');
    const videoItems = document.querySelectorAll('.video-item');

    // Xử lý khi click vào tiêu đề bài học
    lessonHeaders.forEach(header => {
        header.addEventListener('click', () => {
            // Đóng tất cả các bài học khác
            lessonHeaders.forEach(h => {
                if (h !== header) {
                    h.classList.remove('active');
                    h.nextElementSibling.style.display = 'none';
                }
            });
            
            // Toggle bài học hiện tại
            header.classList.toggle('active');
            const videoList = header.nextElementSibling;
            videoList.style.display = header.classList.contains('active') ? 'block' : 'none';
            
            // Nếu đang mở và có video mặc định, phát video đầu tiên
            if (header.classList.contains('active') && header.dataset.video) {
                videoFrame.src = `https://www.youtube.com/embed/${header.dataset.video}?rel=0&autoplay=1`;
                
                // Đánh dấu video đang chọn
                videoItems.forEach(item => item.classList.remove('active'));
                const firstVideo = videoList.querySelector('.video-item');
                if (firstVideo) firstVideo.classList.add('active');
            }
        });
    });

    // Xử lý khi click vào video item
    videoItems.forEach(item => {
        item.addEventListener('click', () => {
            const videoId = item.dataset.video;
            if (videoId) {
                videoFrame.src = `https://www.youtube.com/embed/${videoId}?rel=0&autoplay=1`;
                
                // Đánh dấu video đang chọn
                videoItems.forEach(i => i.classList.remove('active'));
                item.classList.add('active');
            }
        });
    });

    // Mở bài học đầu tiên khi tải trang
    if (lessonHeaders.length > 0) {
        lessonHeaders[0].click();
    }
});