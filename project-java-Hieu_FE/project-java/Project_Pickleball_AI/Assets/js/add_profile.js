function addSkill() {
    const skillsList = document.getElementById('skillsList');
    const entry = document.createElement('div');
    entry.className = 'entry';
    entry.innerHTML = `
        <input type="text" class="skillName" placeholder="Skill Name" disabled>
        <input type="text" class="skillLevel" placeholder="Level" disabled>
        <textarea class="skillDesc" placeholder="Skill Description" disabled></textarea>
        <button class="edit--btn" onclick="editEntry(this)">Edit</button>
        <button class="delete--btn" onclick="deleteEntry(this)">Delete</button>
    `;
    skillsList.appendChild(entry);
}

function addExperience() {
    const expList = document.getElementById('experienceList');
    const entry = document.createElement('div');
    entry.className = 'entry';
    entry.innerHTML = `
        <input type="text" class="expName" placeholder="Experience Name" disabled>
        <input type="text" class="expWorkplace" placeholder="Workplace" disabled>
        <textarea class="expDesc" placeholder="Experience Description" disabled></textarea>
        <button class="edit--btn" onclick="editEntry(this)">Edit</button>
        <button class="delete--btn" onclick="deleteEntry(this)">Delete</button>
    `;
    expList.appendChild(entry);
}

function addCalendar() {
    const calList = document.getElementById('calendarList');
    const entry = document.createElement('div');
    entry.className = 'entry';
    entry.innerHTML = `
        <input type="date" class="calDate" disabled>
        <input type="time" class="calTime" placeholder="Time" disabled>
        <input type="time" class="calEndTime" placeholder="End Time" disabled>
        <button class="edit--btn" onclick="editEntry(this)">Edit</button>
        <button class="delete--btn" onclick="deleteEntry(this)">Delete</button>
    `;
    calList.appendChild(entry);
}

function addPrice() {
    const priceList = document.getElementById('priceList');
    const entry = document.createElement('div');
    entry.className = 'entry';
    entry.innerHTML = `
        <input type="number" class="priceValue" placeholder="Price (VND)" disabled>
        <button class="edit--btn" onclick="editEntry(this)">Edit</button>
        <button class="delete--btn" onclick="deleteEntry(this)">Delete</button>
    `;
    priceList.appendChild(entry);
}

function addPhoto() {
    const photoList = document.getElementById('photoList');
    const entry = document.createElement('div');
    entry.className = 'entry';
    entry.innerHTML = `
        <input type="file" class="photoInput" accept="image/*" disabled>
        <img class="photoPreview" style="max-width: 100px; max-height: 100px; display: none;">
        <button class="edit--btn" onclick="editEntry(this)">Edit</button>
        <button class="delete--btn" onclick="deleteEntry(this)">Delete</button>
    `;
    photoList.appendChild(entry);
}

function addTitle() {
    const titleList = document.getElementById('titleList');
    const entry = document.createElement('div');
    entry.className = 'entry';
    entry.innerHTML = `
        <input type="text" class="titleInput" placeholder="Profile Title" disabled>
        <button class="edit--btn" onclick="editEntry(this)">Edit</button>
        <button class="delete--btn" onclick="deleteEntry(this)">Delete</button>
    `;
    titleList.appendChild(entry);
}

function addDescription() {
    const descList = document.getElementById('describeList');
    const entry = document.createElement('div');
    entry.className = 'entry';
    entry.innerHTML = `
        <textarea class="descInput" placeholder="Profile Description" disabled></textarea>
        <button class="edit--btn" onclick="editEntry(this)">Edit</button>
        <button class="delete--btn" onclick="deleteEntry(this)">Delete</button>
    `;
    descList.appendChild(entry);
}

function addCoachName() {
    const coachList = document.getElementById('coachNameList');
    const entry = document.createElement('div');
    entry.className = 'entry';
    entry.innerHTML = `
        <input type="text" class="coachNameInput" placeholder="Coach Name" disabled>
        <button class="edit--btn" onclick="editEntry(this)">Edit</button>
        <button class="delete--btn" onclick="deleteEntry(this)">Delete</button>
    `;
    coachList.appendChild(entry);
}

function addLectureCount() {
    const lectureList = document.getElementById('lectureList');
    const entry = document.createElement('div');
    entry.className = 'entry';
    entry.innerHTML = `
        <input type="number" class="lectureCountInput" placeholder="Number of Lectures" disabled>
        <button class="edit--btn" onclick="editEntry(this)">Edit</button>
        <button class="delete--btn" onclick="deleteEntry(this)">Delete</button>
    `;
    lectureList.appendChild(entry);
}

function addTeachingMethod() {
    const methodList = document.getElementById('methodList');
    const entry = document.createElement('div');
    entry.className = 'entry';
    entry.innerHTML = `
        <input type="text" class="methodInput" placeholder="Teaching Method" disabled>
        <button class="edit--btn" onclick="editEntry(this)">Edit</button>
        <button class="delete--btn" onclick="deleteEntry(this)">Delete</button>
    `;
    methodList.appendChild(entry);
}

function editEntry(button) {
    const entry = button.parentElement;
    const inputs = entry.getElementsByTagName('input');
    const textarea = entry.getElementsByTagName('textarea')[0];
    
    if (button.textContent === 'Edit') {
        for (let input of inputs) {
            input.removeAttribute('disabled');
        }
        if (textarea) textarea.removeAttribute('disabled');
        button.textContent = 'Save';
        
        // Handle photo preview
        const photoInput = entry.querySelector('.photoInput');
        if (photoInput) {
            photoInput.addEventListener('change', function() {
                const file = this.files[0];
                if (file) {
                    const reader = new FileReader();
                    reader.onload = function(e) {
                        const preview = entry.querySelector('.photoPreview');
                        preview.src = e.target.result;
                        preview.style.display = 'block';
                    };
                    reader.readAsDataURL(file);
                }
            });
        }
    } else {
        for (let input of inputs) {
            input.setAttribute('disabled', 'disabled');
        }
        if (textarea) textarea.setAttribute('disabled', 'disabled');
        button.textContent = 'Edit';
    }
}

function deleteEntry(button) {
    button.parentElement.remove();
}

function saveChanges() {
    const profile = {
        photo: Array.from(document.getElementById('photoList').getElementsByClassName('entry')).map(entry => ({
            url: entry.querySelector('.photoPreview')?.src || ''
        })),
        title: Array.from(document.getElementById('titleList').getElementsByClassName('entry')).map(entry => ({
            value: entry.getElementsByClassName('titleInput')[0].value
        })),
        description: Array.from(document.getElementById('describeList').getElementsByClassName('entry')).map(entry => ({
            value: entry.getElementsByClassName('descInput')[0].value
        })),
        coachName: Array.from(document.getElementById('coachNameList').getElementsByClassName('entry')).map(entry => ({
            value: entry.getElementsByClassName('coachNameInput')[0].value
        })),
        lectureCount: Array.from(document.getElementById('lectureList').getElementsByClassName('entry')).map(entry => ({
            value: entry.getElementsByClassName('lectureCountInput')[0].value
        })),
        teachingMethods: Array.from(document.getElementById('methodList').getElementsByClassName('entry')).map(entry => ({
            value: entry.getElementsByClassName('methodInput')[0].value
        })),
        skills: Array.from(document.getElementById('skillsList').getElementsByClassName('entry')).map(entry => ({
            name: entry.getElementsByClassName('skillName')[0].value,
            level: entry.getElementsByClassName('skillLevel')[0].value,
            desc: entry.getElementsByClassName('skillDesc')[0].value
        })),
        experience: Array.from(document.getElementById('experienceList').getElementsByClassName('entry')).map(entry => ({
            name: entry.getElementsByClassName('expName')[0].value,
            workplace: entry.getElementsByClassName('expWorkplace')[0].value,
            desc: entry.getElementsByClassName('expDesc')[0].value
        })),
        calendar: Array.from(document.getElementById('calendarList').getElementsByClassName('entry')).map(entry => ({
            date: entry.getElementsByClassName('calDate')[0].value,
            time: entry.getElementsByClassName('calTime')[0].value,
            endTime: entry.getElementsByClassName('calEndTime')[0].value
        })),
        price: Array.from(document.getElementById('priceList').getElementsByClassName('entry')).map(entry => ({
            value: entry.getElementsByClassName('priceValue')[0].value
        }))
    };
    console.log('Profile saved:', profile);
    alert('Changes saved!');
}