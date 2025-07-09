const studyHistory = [
    { id: "KH001", name: "Basic Pickleball", startDate: "2025-06-01", status: "in_progress" },
    { id: "KH002", name: "Advanced Pickleball Techniques", startDate: "2025-05-15", status: "completed" },
    { id: "KH003", name: "Pickleball Competition Strategies", startDate: "2025-06-10", status: "in_progress" },
    { id: "KH004", name: "In-Depth Pickleball", startDate: "2025-04-20", status: "paused" },
];

// Function to render study history
function renderStudyHistory(historyToShow) {
    const tableBody = document.getElementById('studyTable');
    tableBody.innerHTML = '';
    historyToShow.forEach(item => {
        const statusClass = {
            in_progress: 'status-in-progress',
            completed: 'status-completed',
            paused: 'status-paused'
        }[item.status];

        tableBody.innerHTML += `
            <tr>
                <td>${item.id}</td>
                <td>${item.name}</td>
                <td>${item.startDate}</td>
                <td>
                    <span class="status ${statusClass}">
                        ${item.status === 'in_progress' ? 'In Progress' : 
                          item.status === 'completed' ? 'Completed' : 'Paused'}
                    </span>
                </td>
            </tr>
        `;
    });
}
// ...existing code...

// Function to view progress details (placeholder)
function viewProgress(courseId) {
    alert(`View details for course ${courseId}`);
    // Implement actual progress detail view logic here
}

// Search and filter functionality
document.getElementById('searchInput').addEventListener('input', filterStudyHistory);
document.getElementById('statusFilter').addEventListener('change', filterStudyHistory);

function filterStudyHistory() {
    const searchTerm = document.getElementById('searchInput').value.toLowerCase();
    const statusFilter = document.getElementById('statusFilter').value;

    const filteredHistory = studyHistory.filter(item => 
        (item.id.toLowerCase().includes(searchTerm) || 
         item.name.toLowerCase().includes(searchTerm)) &&
        (statusFilter === 'all' || item.status === statusFilter)
    );

    renderStudyHistory(filteredHistory);
}

// Initial render
renderStudyHistory(studyHistory);


// ...existing code...
document.addEventListener('DOMContentLoaded', function() {
    const allCourseBtn = document.getElementById('course--btn');
    const historyBtn = document.getElementById('history--btn');
    const allCourseSection = document.getElementById('all-course-section');
    const historySection = document.getElementById('history-section');

    function setActive(btn) {
        allCourseBtn.classList.remove('active');
        historyBtn.classList.remove('active');
        btn.classList.add('active');
    }

    allCourseBtn.addEventListener('click', function() {
        allCourseSection.style.display = 'block';
        historySection.style.display = 'none';
        setActive(allCourseBtn);
    });

    historyBtn.addEventListener('click', function() {
        allCourseSection.style.display = 'none';
        historySection.style.display = 'block';
        setActive(historyBtn);
    });

    // Mặc định nút All course được active
    setActive(allCourseBtn);
});
// ...existing code...