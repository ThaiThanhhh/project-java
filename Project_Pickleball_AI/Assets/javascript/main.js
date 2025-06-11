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

document.addEventListener('DOMContentLoaded', () => {
    const aopcButton = document.getElementById('btn-aopc');
    const ppaButton = document.getElementById('btn-ppa');
    const aopcInfo = document.getElementById('aopc-info');
    const ppaInfo = document.getElementById('ppa-info');

    aopcButton.addEventListener('click', () => {
        aopcInfo.style.display = 'block';
        ppaInfo.style.display = 'none';
        aopcButton.classList.add('active');
        ppaButton.classList.remove('active');
    });

    ppaButton.addEventListener('click', () => {
        aopcInfo.style.display = 'none';
        ppaInfo.style.display = 'block';
        ppaButton.classList.add('active');
        aopcButton.classList.remove('active');
    });
});

document.addEventListener('DOMContentLoaded', () => {
    const aopcButton = document.getElementById('btn-aopc');
    const ppaButton = document.getElementById('btn-ppa');
    const aopcInfo = document.getElementById('aopc-info');
    const ppaInfo = document.getElementById('ppa-info');
    const showMoreAopc = document.getElementById('show-more-aopc');
    const showMorePpa = document.getElementById('show-more-ppa');

    // Tournament toggle
    aopcButton.addEventListener('click', () => {
        aopcInfo.style.display = 'block';
        ppaInfo.style.display = 'none';
        aopcButton.classList.add('active');
        ppaButton.classList.remove('active');
    });

    ppaButton.addEventListener('click', () => {
        aopcInfo.style.display = 'none';
        ppaInfo.style.display = 'block';
        ppaButton.classList.add('active');
        aopcButton.classList.remove('active');
    });

    // Show More for AOPC
    showMoreAopc.addEventListener('click', () => {
        const hiddenMatches = aopcInfo.querySelectorAll('.hidden-match');
        const isHidden = hiddenMatches[0].style.display === 'none' || !hiddenMatches[0].style.display;
        hiddenMatches.forEach(match => {
            match.style.display = isHidden ? 'flex' : 'none';
        });
        showMoreAopc.textContent = isHidden ? 'SHOW LESS' : 'SHOW MORE';
    });

    // Show More for PPA
    showMorePpa.addEventListener('click', () => {
        const hiddenMatches = ppaInfo.querySelectorAll('.hidden-match');
        const isHidden = hiddenMatches[0].style.display === 'none' || !hiddenMatches[0].style.display;
        hiddenMatches.forEach(match => {
            match.style.display = isHidden ? 'flex' : 'none';
        });
        showMorePpa.textContent = isHidden ? 'SHOW LESS' : 'SHOW MORE';
    });
});