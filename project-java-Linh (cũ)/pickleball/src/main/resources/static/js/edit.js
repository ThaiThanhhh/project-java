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

function editEntry(button) {
    const entry = button.parentElement;
    const inputs = entry.getElementsByTagName('input');
    const textarea = entry.getElementsByTagName('textarea')[0];
    
    // Check if the button is in "Edit" or "Save" state
    if (button.textContent === 'Edit') {
        // Enable inputs and textarea
        for (let input of inputs) {
            input.removeAttribute('disabled');
        }
        if (textarea) textarea.removeAttribute('disabled');
        button.textContent = 'Save';
    } else {
        // Disable inputs and textarea
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