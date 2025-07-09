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
    const showMoreAopc = document.getElementById('show-more-aopc');
    const showMorePpa = document.getElementById('show-more-ppa');

    // Tournament toggle
    aopcButton.addEventListener('click', () => {
        aopcInfo.style.display = 'block';
        ppaInfo.style.display = 'none';
        aopcButton.classList.add('active');
        ppaButton.classList.remove('active');
        // Hiện show more AOPC, ẩn show more PPA
        document.getElementById('show-more-aopc-container').style.display = 'block';
        document.getElementById('show-more-ppa-container').style.display = 'none';
    });

    ppaButton.addEventListener('click', () => {
        aopcInfo.style.display = 'none';
        ppaInfo.style.display = 'block';
        ppaButton.classList.add('active');
        aopcButton.classList.remove('active');
        // Hiện show more PPA, ẩn show more AOPC
        document.getElementById('show-more-ppa-container').style.display = 'block';
        document.getElementById('show-more-aopc-container').style.display = 'none';
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

    // Khởi tạo: chỉ hiện show more của giải đang mở
    document.getElementById('show-more-aopc-container').style.display = 'block';
    document.getElementById('show-more-ppa-container').style.display = 'none';
});


const cards = document.querySelectorAll('.background__card--item');
const leftArrow = document.querySelector('.arrow--left');
const rightArrow = document.querySelector('.arrow--right');

if (!leftArrow || !rightArrow) {
    console.error('Arrow elements not found!');
}

let isFirstSet = true; // Trạng thái: true cho 0,1,2; false cho 2,3,4

function updateCards() {
    cards.forEach((card) => {
        card.style.display = 'none'; // Ẩn tất cả thẻ trước
    });
    if (isFirstSet) {
        cards[0].style.display = 'block'; // Thẻ 0
        cards[1].style.display = 'block'; // Thẻ 1
        cards[2].style.display = 'block'; // Thẻ 2
    } else {
        cards[2].style.display = 'block'; // Thẻ 2 (giữ thẻ cũ)
        cards[3].style.display = 'block'; // Thẻ 3
        cards[4].style.display = 'block'; // Thẻ 4
    }
}
// chuyen tiep the hlv
rightArrow.addEventListener('click', () => {
    console.log('Right arrow clicked');
    if (isFirstSet) {
        isFirstSet = false; // Chuyển sang 2,3,4
    } else {
        isFirstSet = true; // Quay lại 0,1,2
    }
    updateCards();
});

leftArrow.addEventListener('click', () => {
    console.log('Left arrow clicked');
    if (!isFirstSet) {
        isFirstSet = true; // Từ 2,3,4 về 0,1,2
    } else {
        isFirstSet = false; // Từ 0,1,2 về 2,3,4
    }
    updateCards();
});

// Thiết lập ban đầu
updateCards();


document.addEventListener('DOMContentLoaded', function() {
    const rows = document.querySelectorAll('.ai-feature-row');
    const observer = new IntersectionObserver((entries, obs) => {
        entries.forEach((entry, idx) => {
            if (entry.isIntersecting) {
                setTimeout(() => {
                    entry.target.classList.add('visible');
                }, idx * 200); // delay từng dòng
                obs.unobserve(entry.target);
            }
        });
    }, { threshold: 0.2 });

    rows.forEach(row => observer.observe(row));
});

document.addEventListener('DOMContentLoaded', () => {
    const locationCards = document.querySelectorAll('.container__location__card--item');
    const leftArrowLocation = document.querySelector('.arrow--left--location');
    const rightArrowLocation = document.querySelector('.arrow--right--location');
    
    let isFirstLocationSet = true; // true: 0,1,2 | false: 2,3,4

    function updateLocationCards() {
        locationCards.forEach((card, index) => {
            card.style.display = 'none';
            
            if (isFirstLocationSet && (index === 0 || index === 1 || index === 2)) {
                card.style.display = 'block';
            } else if (!isFirstLocationSet && (index === 2 || index === 3 || index === 4)) {
                card.style.display = 'block';
            }
        });
    }

    rightArrowLocation.addEventListener('click', () => {
        if (isFirstLocationSet) {
            isFirstLocationSet = false; // Chuyển sang 2,3,4
        } else {
            isFirstLocationSet = true; // Quay lại 0,1,2 (nếu muốn vòng lặp)
        }
        updateLocationCards();
    });

    leftArrowLocation.addEventListener('click', () => {
        if (!isFirstLocationSet) {
            isFirstLocationSet = true; // Từ 2,3,4 về 0,1,2
        } else {
            isFirstLocationSet = false; // Từ 0,1,2 về 2,3,4 (nếu muốn vòng lặp)
        }
        updateLocationCards();
    });

    // Thiết lập ban đầu
    updateLocationCards();
});