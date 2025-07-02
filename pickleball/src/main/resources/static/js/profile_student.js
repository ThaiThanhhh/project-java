const editBtn = document.getElementById('editBtn');
const saveBtn = document.getElementById('saveBtn');
const cancelBtn = document.getElementById('cancelBtn');
const form = document.getElementById('profileForm');
const inputs = form.querySelectorAll('input:not([type="file"])');
const levelSelect = document.getElementById('levelSelect');

function setEditMode(edit) {
    inputs.forEach(i => {
        if (i.name === "email" || i.hasAttribute("data-always-readonly")) {
            i.readOnly = true;
        } else {
            i.readOnly = !edit;
        }
    });
    if (levelSelect) levelSelect.disabled = !edit;
    if(edit) {
        saveBtn.style.display = 'inline-block';
        cancelBtn.style.display = 'inline-block';
        editBtn.style.display = 'none';
    } else {
        saveBtn.style.display = 'none';
        cancelBtn.style.display = 'none';
        editBtn.style.display = 'inline-block';
    }
}

// Mặc định: chỉ xem, không cho sửa
setEditMode(false);

editBtn.onclick = () => setEditMode(true);
cancelBtn.onclick = () => {
    setEditMode(false);
    form.reset();
};