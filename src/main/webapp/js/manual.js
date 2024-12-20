document.addEventListener("DOMContentLoaded", function() {
    // Select elements
    const manualHeader = document.getElementById("manualHeader");
    const manualContent = document.getElementById("manualContent");
    const toggleArrow = document.getElementById("toggleArrow");

    // Check if elements exist
    if (manualHeader && manualContent && toggleArrow) {
        manualHeader.addEventListener("click", function() {
            if (manualContent.style.display === "none" || manualContent.style.display === "") {
                manualContent.style.display = "block";
                toggleArrow.style.transform = "rotate(180deg)"; // Rotate the arrow when content is shown
            } else {
                manualContent.style.display = "none";
                toggleArrow.style.transform = "rotate(0deg)"; // Rotate back when content is hidden
            }
        });
    }
});
