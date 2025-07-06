document.addEventListener("DOMContentLoaded", function() {
    // --- Skill ---
    const skillName = document.querySelector('input[name="skill_name"]');
    const skillLevel = document.querySelector('input[name="skill_level"]');
    const skillDesc = document.querySelector('input[name="skill_description"]');
    const skillAddBtn = document.querySelector('.booking-col .add-btn');
    const skillList = document.querySelectorAll('.booking-list')[0];

    skillAddBtn.onclick = function() {
        if (!skillName.value || !skillLevel.value) return;
        const item = document.createElement('div');
        item.className = 'booking-list-item';
        item.innerHTML = `
            <span>${skillName.value} - ${skillLevel.value} (${skillDesc.value})</span>
            <button type="button" class="delete-btn">Delete</button>
            <button type="button" class="change-btn">Change</button>
        `;
        skillList.appendChild(item);
        skillName.value = '';
        skillLevel.value = '';
        skillDesc.value = '';
        bindSkillItemEvents(item);
    };

    function bindSkillItemEvents(item) {
        item.querySelector('.delete-btn').onclick = function() {
            item.remove();
        };
        item.querySelector('.change-btn').onclick = function() {
            const text = item.querySelector('span').textContent;
            const [nameLevel, desc] = text.split('(');
            const [name, level] = nameLevel.split('-');
            skillName.value = name.trim();
            skillLevel.value = level.trim();
            skillDesc.value = desc ? desc.replace(')', '').trim() : '';
            item.remove();
        };
    }

    // --- Experience ---
    const expName = document.querySelector('input[name="experience_name"]');
    const expPlace = document.querySelector('input[name="work_place"]');
    const expDesc = document.querySelector('input[name="experience_description"]');
    const expAddBtn = document.querySelectorAll('.add-btn')[1];
    const expList = document.querySelectorAll('.booking-list')[1];

    expAddBtn.onclick = function() {
        if (!expName.value || !expPlace.value) return;
        const item = document.createElement('div');
        item.className = 'booking-list-item';
        item.innerHTML = `
            <span>${expName.value} - ${expPlace.value} (${expDesc.value})</span>
            <button type="button" class="delete-btn">Delete</button>
            <button type="button" class="change-btn">Change</button>
        `;
        expList.appendChild(item);
        expName.value = '';
        expPlace.value = '';
        expDesc.value = '';
        bindExpItemEvents(item);
    };

    function bindExpItemEvents(item) {
        item.querySelector('.delete-btn').onclick = function() {
            item.remove();
        };
        item.querySelector('.change-btn').onclick = function() {
            const text = item.querySelector('span').textContent;
            const [namePlace, desc] = text.split('(');
            const [name, place] = namePlace.split('-');
            expName.value = name.trim();
            expPlace.value = place.trim();
            expDesc.value = desc ? desc.replace(')', '').trim() : '';
            item.remove();
        };
    }

    // --- Teaching Locations ---
    const locInput = document.querySelector('input[name="teaching_location"]');
    const locAddBtn = document.querySelectorAll('.add-btn')[2];
    const locList = document.querySelectorAll('.booking-list')[2];

    locAddBtn.onclick = function() {
        if (!locInput.value) return;
        const item = document.createElement('div');
        item.className = 'booking-list-item';
        item.innerHTML = `
            <span>${locInput.value}</span>
            <button type="button" class="delete-btn">Delete</button>
            <button type="button" class="change-btn">Change</button>
        `;
        locList.appendChild(item);
        locInput.value = '';
        bindLocItemEvents(item);
    };

    function bindLocItemEvents(item) {
        item.querySelector('.delete-btn').onclick = function() {
            item.remove();
        };
        item.querySelector('.change-btn').onclick = function() {
            locInput.value = item.querySelector('span').textContent;
            item.remove();
        };
    }

    const weekCheckboxes = document.querySelectorAll('.weekly-selection input[type="checkbox"]');
    const startInput = document.querySelector('input[name="start_time"]');
    const endInput = document.querySelector('input[name="end_time"]');
    const schedAddBtn = document.querySelectorAll('.booking-col .add-btn')[3]; // nút Add cuối cùng
    const schedList = document.querySelectorAll('.booking-list')[3];

    schedAddBtn.onclick = function() {
        // Lấy các ngày đã chọn
        const days = [];
        weekCheckboxes.forEach(cb => {
            if (cb.checked) days.push(cb.value.charAt(0).toUpperCase() + cb.value.slice(1));
        });
        if (days.length === 0 || !startInput.value || !endInput.value) return;

        const item = document.createElement('div');
        item.className = 'booking-list-item';
        item.innerHTML = `
            <span>${days.join(', ')} | ${startInput.value} - ${endInput.value}</span>
            <button type="button" class="delete-btn">Delete</button>
            <button type="button" class="change-btn">Change</button>
        `;
        schedList.appendChild(item);

        // Reset input
        weekCheckboxes.forEach(cb => cb.checked = false);
        startInput.value = '';
        endInput.value = '';
        bindSchedItemEvents(item);
    };

    function bindSchedItemEvents(item) {
        item.querySelector('.delete-btn').onclick = function() {
            item.remove();
        };
        item.querySelector('.change-btn').onclick = function() {
            const text = item.querySelector('span').textContent;
            const [days, times] = text.split('|');
            // Set lại các ngày
            weekCheckboxes.forEach(cb => {
                cb.checked = days.toLowerCase().includes(cb.value);
            });
            // Set lại giờ
            const [start, end] = times.split('-');
            startInput.value = start ? start.trim() : '';
            endInput.value = end ? end.trim() : '';
            item.remove();
        };
    }

    // Avatar preview
    const avatarInput = document.getElementById('avatarInput');
    const avatarPreview = document.getElementById('avatarPreview');
    if (avatarInput && avatarPreview) {
        avatarInput.addEventListener('change', function() {
            if (this.files && this.files[0]) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    avatarPreview.src = e.target.result;
                };
                reader.readAsDataURL(avatarInput.files[0]);
            }
        });
    }
    
    
});