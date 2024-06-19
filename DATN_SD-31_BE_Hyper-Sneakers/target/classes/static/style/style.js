document.getElementById("mainButton").addEventListener("click", function() {
    // Lấy các button ẩn
    var hiddenButtons = document.querySelectorAll(".hidden");

    // Hiển thị các button ẩn
    hiddenButtons.forEach(function(button) {
        button.style.display = "inline-block";
    });

    // Hiển thị nút "Remove" và ẩn nút chính
    document.getElementById("removeButton").style.display = "inline-block";
    document.getElementById("mainButton").style.display = "none";
});

// Đặt sự kiện nhấn vào nút "Remove"
document.getElementById("removeButton").addEventListener("click", function() {
    // Ẩn các button ẩn và nút "Remove"
    var hiddenButtons = document.querySelectorAll(".hidden");
    hiddenButtons.forEach(function(button) {
        button.style.display = "none";
    });
    document.getElementById("removeButton").style.display = "none";

    // Hiển thị lại nút chính
    document.getElementById("mainButton").style.display = "inline-block";
});