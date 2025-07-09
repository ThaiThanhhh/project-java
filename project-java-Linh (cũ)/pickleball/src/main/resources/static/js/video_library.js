// // Video data
// const videos = {
//     basic: [
//         { title: "How to Hold a Racket", thumbnail: "https://youtu.be/gp6N_DhvacM?si=5w8EebkeC4ixBGVJ", duration: "02:00", youtubeId: "gp6N_DhvacM" },
//         { title: " Dink Forehand", thumbnail: "https://youtu.be/-GaiEA1rCQc?si=uQ6cYlLVGvWVbvQS", duration: "02:47", youtubeId: "-GaiEA1rCQc" },
//         { title: "Dink Backhand", thumbnail: "https://youtu.be/IUSxXvgpWMs?si=y0kVY_UNgGAu558z", duration: "01:59", youtubeId: "IUSxXvgpWMs" },
//         { title: "Drop Forehand", thumbnail: "https://youtu.be/vWnVnG0qQAU?si=5hLHzqJHY73ugCTN", duration: "02:22", youtubeId: "vWnVnG0qQAU" },
//         { title: "Drop Backhand", thumbnail: "https://youtu.be/P-vxM_97DKQ?si=GpWPewmlEc7JBGFO", duration: "02:23", youtubeId: "P-vxM_97DKQ" },
//         { title: "Drive Forehand", thumbnail: "https://youtu.be/izbqLI0QJYA?si=W7Ou4TSDcLzlyeWs", duration: "01:51", youtubeId: "izbqLI0QJYA" },
//          { title: "Learn Topspin", thumbnail: "https://youtu.be/zUsv0NIVD0c?si=vqt_3Z30hON5OzuB", duration: "08:32", youtubeId: "zUsv0NIVD0c" },
//         { title: "How to Serve A Pickleball | Beginner's Guide", thumbnail: "https://youtu.be/BmdnJNCEwxI?si=UT6v0VueWpq3l1cu", duration: "09:37", youtubeId: "BmdnJNCEwxI" },
//         { title: "7 tip", thumbnail: "https://youtu.be/izbqLI0QJYA?si=W7Ou4TSDcLzlyeWs", duration: "01:51", youtubeId: "izbqLI0QJYA" },
//     ],
//     advanced: [
//         { title: "Drive Backhand ", thumbnail: "https://youtu.be/jQ1r_JV1Zy4?si=tpM1qEWeFcU6P5Ij", duration: "02:48", youtubeId: "jQ1r_JV1Zy4" },
//         { title: "Serve", thumbnail: "https://youtu.be/cQxpPphr-Zc?si=6E4c-kx0dG7PAZPe", duration: "01:32", youtubeId: "cQxpPphr-Zc" },
//         { title: "Smash", thumbnail: "https://youtu.be/zGRY1t2Jkbs?si=h6W20wWQOLM-kpga", duration: "01:52", youtubeId: "zGRY1t2Jkbs" },
//         { title: "Pickleball’s 3rd and 5th Shots! ", thumbnail: "https://youtu.be/mIzddYkHV6Q?si=mfu1f9HjRgIsCRBM", duration: "02:48", youtubeId: "mIzddYkHV6Q" },
//         { title: "5 Common Mistakes Beginners Make ", thumbnail: "https://youtu.be/b7xmSY94nu4?si=j0JxA9LsoLk_nosN", duration: "13:08", youtubeId: "b7xmSY94nu4" },
//         { title: "How to Hit Topspin Drive in Pickleball", thumbnail: "https://youtu.be/odhhfqkQB-k?si=RhA4MaW5YBOUhCjT", duration: "01:52", youtubeId: "odhhfqkQB-k" },
//         { title: "Improve Your Pickleball Drop Shot  ", thumbnail: "https://youtu.be/JDqs_vGQKsY?si=H4vLhYS-5yMTnxYj", duration: "23:40", youtubeId: "JDqs_vGQKsY" },
//         { title: "The Slice Return in Pickleball", thumbnail: "https://youtu.be/zZMQjd_efD8?si=4_PZBJz1tsccictX", duration: "01:32", youtubeId: "zZMQjd_efD8" },
//         { title: "How to Dink", thumbnail: "https://youtu.be/XW3gyKe20f0?si=PdzV5A6riZRHrGI9", duration: "06:56", youtubeId: "XW3gyKe20f0" },
//     ],
//     tournament: [
//         { title: "Pickleball Championships", thumbnail: "https://youtu.be/oReod5Ip2dY?si=ER77LmEUof5oLHJg", duration: "21:35", youtubeId: "oReod5Ip2dY" },
//         { title: "Quang Duong v Phuc Hyunh", thumbnail: "https://youtu.be/dozPHU9cXU0?si=kMf-bSQMFAVOfIWg", duration: "21:58", youtubeId: "dozPHU9cXU0" },
//         { title: "Bright/Kawamoto vs Brascia/Brascia", thumbnail: "https://youtu.be/Q2jsm-22xew?si=T8bsTy9ihunr4nWe", duration: "01:34:44", youtubeId: "Q2jsm-22xew" }
//     ]
// };

// // Function to render videos
// function renderVideos(videoList, containerId) {
//     const container = document.getElementById(containerId);
//     container.innerHTML = '';
//     videoList.forEach(video => {
//         const videoCard = document.createElement('div');
//         videoCard.className = 'video-card';
//         videoCard.innerHTML = `
//             <div class="video-frame">
//                 <iframe class="w-full h-full" src="https://www.youtube.com/embed/${video.youtubeId}" 
//                         title="${video.title}" frameborder="0" 
//                         allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" 
//                         allowfullscreen></iframe>
//             </div>
//             <div class="video-info">
//                 <h3>${video.title}</h3>
//                 <p>Duration: ${video.duration}</p>
//             </div>
//         `;
//         container.appendChild(videoCard);
//     });
// }

// // Function to search videos
// function searchVideos() {
//     const searchInput = document.getElementById('searchInput').value.toLowerCase();
//     const filteredBasic = videos.basic.filter(video => video.title.toLowerCase().includes(searchInput));
//     const filteredAdvanced = videos.advanced.filter(video => video.title.toLowerCase().includes(searchInput));
//     const filteredTournament = videos.tournament.filter(video => video.title.toLowerCase().includes(searchInput));
//     renderVideos(filteredBasic, 'basicVideoGrid');
//     renderVideos(filteredAdvanced, 'advancedVideoGrid');
//     renderVideos(filteredTournament, 'tournamentVideoGrid');
// }

// // Attach search event listener
// document.getElementById('searchInput').addEventListener('input', searchVideos);


// // Initial video render
// renderVideos(videos.basic, 'basicVideoGrid');
// renderVideos(videos.advanced, 'advancedVideoGrid');
// renderVideos(videos.tournament, 'tournamentVideoGrid');
// videos là một mảng các object, mỗi object có trường category
// [{title: "...", youtubeId: "...", duration: "...", category: "Basic"}, ...]

// Group videos by category
function groupByCategory(videos) {
    return videos.reduce((acc, video) => {
        (acc[video.category] = acc[video.category] || []).push(video);
        return acc;
    }, {});
}

// Render videos by category
function renderVideosByCategory(videos) {
    const gridIds = {
        "Basic": "basicVideoGrid",
        "Advanced": "advancedVideoGrid",
        "Tournament": "tournamentVideoGrid"
    };
    // Clear all grids
    Object.values(gridIds).forEach(id => {
        const grid = document.getElementById(id);
        if (grid) grid.innerHTML = '';
    });
    // Group and render
    const grouped = groupByCategory(videos);
    Object.keys(grouped).forEach(category => {
        const gridId = gridIds[category];
        if (!gridId) return;
        const grid = document.getElementById(gridId);
        grouped[category].forEach(video => {
            const videoCard = document.createElement('div');
            videoCard.className = 'video-card';
            videoCard.innerHTML = `
                <div class="video-frame">
                    <iframe class="w-full h-full" src="https://www.youtube.com/embed/${video.youtubeId}" 
                        title="${video.title}" frameborder="0" allowfullscreen></iframe>
                </div>
                <div class="video-info">
                    <h3>${video.title}</h3>
                    <p>Duration: ${video.duration}</p>
                </div>
            `;
            grid.appendChild(videoCard);
        });
    });
}

// Search videos
function searchVideos() {
    const searchInput = document.getElementById('searchInput').value.toLowerCase();
    const filtered = videos.filter(video => video.title.toLowerCase().includes(searchInput));
    renderVideosByCategory(filtered);
}

document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('searchInput').addEventListener('input', searchVideos);
    renderVideosByCategory(videos);
});