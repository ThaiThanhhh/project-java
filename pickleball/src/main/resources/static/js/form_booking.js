console.log("skills from server:", skills);
console.log("experiences from server:", experiences);
document.addEventListener("DOMContentLoaded", function() {
    // Chuẩn hóa dữ liệu từ server (khi edit)
    if (typeof skills !== "undefined" && Array.isArray(skills)) {
        skills = skills.map(s => ({
            name: s.skillName || "",
            level: s.level || "",
            desc: s.description || ""
        }));
    } else {
        skills = [];
    }
    if (typeof experiences !== "undefined" && Array.isArray(experiences)) {
        experiences = experiences.map(e => ({
            name: e.experienceName || "",
            workPlace: e.workPlace || "",
            desc: e.description || ""
        }));
    } else {
        experiences = [];
    }

    // Thêm skill
    window.addSkill = function() {
        const name = document.getElementById('skill_name').value.trim();
        const level = document.getElementById('skill_level').value;
        const desc = document.getElementById('skill_description').value.trim();
        if (!name || !level) return;
        skills.push({ name, level, desc });
        renderSkills();
        document.getElementById('skill_name').value = '';
        document.getElementById('skill_level').value = '';
        document.getElementById('skill_description').value = '';
        updateSkillsHidden();
    };

    // Hiển thị skill
    function renderSkills() {
        const list = document.getElementById('skillList');
        if (!list) return;
        list.innerHTML = '';
        skills.forEach((s, i) => {
            list.innerHTML += `<div class="booking-list-item">${s.name} - ${s.level} : ${s.desc} <button type="button" onclick="removeSkill(${i})">x</button></div>`;
        });
        updateSkillsHidden();
    }

    // Xóa skill
    window.removeSkill = function(idx) {
        skills.splice(idx, 1);
        renderSkills();
    };

    // Cập nhật input ẩn skill
    function updateSkillsHidden() {
        let container = document.getElementById('skillsHidden');
        if (!container) {
            container = document.createElement('div');
            container.id = 'skillsHidden';
            document.querySelector('form').appendChild(container);
        }
        container.innerHTML = '';
        skills.forEach((s, i) => {
            container.innerHTML += `<input type="hidden" name="skills[${i}].skillName" value="${s.name}">`;
            container.innerHTML += `<input type="hidden" name="skills[${i}].level" value="${s.level}">`;
            container.innerHTML += `<input type="hidden" name="skills[${i}].description" value="${s.desc}">`;
        });
    }

    // Thêm experience
    const addExperienceBtn = document.querySelector('.add-experience-btn');
    if (addExperienceBtn) {
        addExperienceBtn.addEventListener('click', addExperience);
    }

    function addExperience() {
        const name = document.getElementById('experience_name').value.trim();
        const workPlace = document.getElementById('work_place').value.trim();
        const desc = document.getElementById('experience_description').value.trim();
        if (!name || !workPlace) return;
        experiences.push({ name, workPlace, desc });
        renderExperiences();
        document.getElementById('experience_name').value = '';
        document.getElementById('work_place').value = '';
        document.getElementById('experience_description').value = '';
        updateExperiencesHidden();
    }

    // Hiển thị experience
    function renderExperiences() {
        const list = document.getElementById('experienceList');
        if (!list) return;
        list.innerHTML = '';
        experiences.forEach((e, i) => {
            list.innerHTML += `<div class="booking-list-item">${e.name} - ${e.workPlace} : ${e.desc} <button type="button" onclick="removeExperience(${i})">x</button></div>`;
        });
        updateExperiencesHidden();
    }

    // Xóa experience
    window.removeExperience = function(idx) {
        experiences.splice(idx, 1);
        renderExperiences();
    };

    // Cập nhật input ẩn experience
    function updateExperiencesHidden() {
        let container = document.getElementById('experiencesHidden');
        if (!container) {
            container = document.createElement('div');
            container.id = 'experiencesHidden';
            document.querySelector('form').appendChild(container);
        }
        container.innerHTML = '';
        experiences.forEach((e, i) => {
            container.innerHTML += `<input type="hidden" name="experiences[${i}].experienceName" value="${e.name}">`;
            container.innerHTML += `<input type="hidden" name="experiences[${i}].workPlace" value="${e.workPlace}">`;
            container.innerHTML += `<input type="hidden" name="experiences[${i}].description" value="${e.desc}">`;
        });
    }

    // Avatar preview logic
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

    // // Khi load trang edit, render lại dữ liệu nếu có
    renderSkills();
    renderExperiences();
});