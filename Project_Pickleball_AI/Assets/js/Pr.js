document.getElementById("upload-avatar").addEventListener("change", function (event) {
  const file = event.target.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = function (e) {
      document.getElementById("avatarImage").src = e.target.result;
      document.getElementById("avatarText").style.display = "none";
    };
    reader.readAsDataURL(file);
  }
});

function logout() {
  alert("Bạn đã đăng xuất!");
}
