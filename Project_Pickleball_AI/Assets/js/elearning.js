document.addEventListener('DOMContentLoaded', function() {
    // 1. HÀM KHỞI TẠO
    function initCourseFilter() {
        // Thiết lập giá trị mặc định
        setupDefaultValues();
        
        // Hiển thị tất cả khóa học ban đầu
        resetFilters(false);
        
        // Thêm sự kiện cho các bộ lọc
        addFilterEventListeners();
        
        // Cập nhật giao diện
        updateUI();
    }

    // 2. THIẾT LẬP GIÁ TRỊ MẶC ĐỊNH
    function setupDefaultValues() {
        // Đặt giá trị mặc định cho rating nếu chưa có
        document.querySelectorAll('input[name="rating"]').forEach((radio, index) => {
            if (!radio.value) {
                const values = [4.5, 4.0, 3.5, 3.0];
                radio.value = values[index] || 3.0;
            }
        });
        
        // Khởi tạo hiển thị giá price slider
        const priceSlider = document.querySelector('.price-slider input[type="range"]');
        if (priceSlider) {
            if (!priceSlider.value) priceSlider.value = priceSlider.max;
            updatePriceDisplay(priceSlider);
        }
    }

    // 3. HÀM LỌC CHÍNH
    function filterCourses() {
        // Lấy các tham số lọc
        const filters = getCurrentFilters();
        
        // Biến theo dõi có khóa học nào bị ẩn không
        let anyHidden = false;
        let visibleCount = 0;
        
        // Lặp qua tất cả khóa học
        document.querySelectorAll('.all-coach-box').forEach(box => {
            const matches = checkCourseMatchesFilters(box, filters);
            
            if (matches) {
                box.classList.remove('hidden', 'no-animation');
                visibleCount++;
            } else {
                box.classList.add('hidden');
                anyHidden = true;
            }
        });
        
        // Cập nhật giao diện
        updateUI(visibleCount, anyHidden);
    }

    // 4. LẤY THAM SỐ LỌC HIỆN TẠI
    function getCurrentFilters() {
        return {
            minRating: getSelectedRating(),
            maxPrice: getSelectedPrice(),
            levels: getSelectedLevels(),
            playstyles: getSelectedPlaystyles(),
            sortBy: getSortByValue()
        };
    }

    // 5. KIỂM TRA KHÓA HỌC PHÙ HỢP
    function checkCourseMatchesFilters(box, filters) {
        const courseData = {
            rating: parseFloat(box.dataset.rating),
            price: parseInt(box.dataset.price),
            level: box.dataset.level.toLowerCase(),
            playstyles: box.dataset.playstyle.toLowerCase().split(',')
        };
        
        // Kiểm tra từng điều kiện
        const ratingMatch = courseData.rating >= filters.minRating;
        const priceMatch = courseData.price <= filters.maxPrice;
        const levelMatch = filters.levels.length === 0 || filters.levels.includes(courseData.level);
        
        const playstyleMatch = filters.playstyles.length === 0 || 
                             filters.playstyles.some(style => courseData.playstyles.includes(style));
        
        return ratingMatch && priceMatch && levelMatch && playstyleMatch;
    }

    // 6. CÁC HÀM HỖ TRỢ
    function getSelectedRating() {
        const selected = document.querySelector('input[name="rating"]:checked');
        return selected ? parseFloat(selected.value) : 0;
    }

    function getSelectedPrice() {
        const slider = document.querySelector('.price-slider input[type="range"]');
        return slider ? parseInt(slider.value) : Infinity;
    }

    function getSelectedLevels() {
        return Array.from(document.querySelectorAll('.filter-section input[type="checkbox"][data-filter-type="level"]:checked'))
                   .map(cb => cb.value.toLowerCase());
    }

    function getSelectedPlaystyles() {
        return Array.from(document.querySelectorAll('.filter-section input[type="checkbox"][data-filter-type="playstyle"]:checked'))
                   .map(cb => cb.value.toLowerCase());
    }

    function getSortByValue() {
        const select = document.getElementById('sort-select');
        return select ? select.value : 'popular';
    }

    // 7. CẬP NHẬT GIAO DIỆN
    function updateUI(visibleCount, anyHidden) {
        updateResultsCount(visibleCount);
        updatePriceDisplay();
        updateActiveFilterIndicators();
        
        // Thêm padding nếu có item bị ẩn
        const mainContent = document.querySelector('.main-content');
        if (anyHidden) {
            mainContent.style.paddingBottom = '50px';
        } else {
            mainContent.style.paddingBottom = '';
        }
    }

    function updateResultsCount(visibleCount) {
        const resultsCount = document.querySelector('.results-count');
        if (!resultsCount) return;
        
        const totalCount = document.querySelectorAll('.all-coach-box').length;
        visibleCount = visibleCount !== undefined ? visibleCount : 
                     document.querySelectorAll('.all-coach-box:not(.hidden)').length;
        
        resultsCount.innerHTML = visibleCount === totalCount ? 
            `<b>Showing all ${totalCount} courses</b>` : 
            `<b>Showing ${visibleCount} of ${totalCount} courses</b>`;
    }

    function updatePriceDisplay() {
        const priceSlider = document.querySelector('.price-slider input[type="range"]');
        if (!priceSlider) return;
        
        const valueDisplay = priceSlider.parentElement.querySelector('.price-value') || 
                           createPriceDisplay(priceSlider.parentElement);
        
        valueDisplay.textContent = parseInt(priceSlider.value).toLocaleString() + '₫';
    }

    function createPriceDisplay(parent) {
        const valueDisplay = document.createElement('div');
        valueDisplay.className = 'price-value';
        parent.appendChild(valueDisplay);
        return valueDisplay;
    }

    function updateActiveFilterIndicators() {
        // Cập nhật trạng thái active cho các nút lọc
        document.querySelectorAll('.filter-btn').forEach(btn => {
            if (btn.classList.contains('reset')) return;
            
            const filterType = btn.dataset.filterType;
            let isActive = false;
            
            if (filterType === 'price') {
                const slider = document.querySelector('.price-slider input[type="range"]');
                isActive = slider && parseInt(slider.value) !== parseInt(slider.max);
            } else {
                const selector = `input[type="${filterType === 'rating' ? 'radio' : 'checkbox'}"][data-filter-type="${filterType}"]:checked`;
                isActive = document.querySelectorAll(selector).length > 0;
            }
            
            btn.classList.toggle('active', isActive);
        });
    }

    // 8. THÊM SỰ KIỆN
    function addFilterEventListeners() {
        // Sự kiện cho rating
        document.querySelectorAll('input[name="rating"]').forEach(radio => {
            radio.addEventListener('change', filterCourses);
        });
        
        // Sự kiện cho checkbox
        document.querySelectorAll('.filter-section input[type="checkbox"]').forEach(checkbox => {
            checkbox.addEventListener('change', filterCourses);
        });
        
        // Sự kiện cho price slider
        const priceSlider = document.querySelector('.price-slider input[type="range"]');
        if (priceSlider) {
            priceSlider.addEventListener('input', function() {
                updatePriceDisplay();
                filterCourses();
            });
        }
        
        // Sự kiện cho nút reset
        const resetButton = document.querySelector('.filter-btn.reset');
        if (resetButton) {
            resetButton.addEventListener('click', () => resetFilters(true));
        }
        
        // Sự kiện sắp xếp
        const sortSelect = document.getElementById('sort-select');
        if (sortSelect) {
            sortSelect.addEventListener('change', sortCourses);
        }
    }

    // 9. RESET BỘ LỌC
    function resetFilters(shouldUpdate = true) {
        // Bỏ chọn tất cả checkbox
        document.querySelectorAll('.filter-section input[type="checkbox"]').forEach(cb => {
            cb.checked = false;
        });
        
        // Đặt rating về mặc định
        const ratingRadios = document.querySelectorAll('input[name="rating"]');
        if (ratingRadios.length > 0) {
            ratingRadios[0].checked = true;
        }
        
        // Đặt price slider về max
        const priceSlider = document.querySelector('.price-slider input[type="range"]');
        if (priceSlider) {
            priceSlider.value = priceSlider.max;
        }
        
        // Hiển thị tất cả khóa học
        document.querySelectorAll('.all-coach-box').forEach(box => {
            box.classList.remove('hidden');
        });
        
        if (shouldUpdate) {
            updateUI();
        }
    }

    // 10. SẮP XẾP KHÓA HỌC
    function sortCourses() {
        const sortBy = getSortByValue();
        const container = document.querySelector('.main-content');
        const courses = Array.from(document.querySelectorAll('.all-coach-box:not(.hidden)'));
        
        courses.sort((a, b) => {
            switch(sortBy) {
                case 'highest-rated':
                    return parseFloat(b.dataset.rating) - parseFloat(a.dataset.rating);
                case 'newest':
                    return new Date(b.dataset.date || 0) - new Date(a.dataset.date || 0);
                case 'price-low':
                    return parseInt(a.dataset.price) - parseInt(b.dataset.price);
                case 'price-high':
                    return parseInt(b.dataset.price) - parseInt(a.dataset.price);
                default: // most-popular
                    return parseInt(b.dataset.popularity || 0) - parseInt(a.dataset.popularity || 0);
            }
        });
        
        // Xóa animation tạm thời để tránh hiệu ứng không mong muốn
        courses.forEach(course => course.classList.add('no-animation'));
        
        // Sắp xếp lại DOM
        courses.forEach(course => container.appendChild(course));
        
        // Khôi phục animation sau 100ms
        setTimeout(() => {
            courses.forEach(course => course.classList.remove('no-animation'));
        }, 100);
    }

    // KHỞI CHẠY
    initCourseFilter();
});