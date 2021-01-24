$(document).ready(function () {
    formatSiteDates();
})

function formatSiteDates() {
    let projectStartDate = $("#projectStartDate");
    let projectEndDate = $("#projectEndDate");
    projectStartDate.text(formatDate(projectStartDate.text()));
    projectEndDate.text(formatDate(projectEndDate.text()));
}


function formatDate(date) {
    let unformattedDate = date ? date : new Date();
    let formattedDate = moment(unformattedDate).format("Do MMM YYYY");
    if (formattedDate === "Invalid date")
        throw new Error("Invalid Date: " + date);
    return formattedDate;
}

window.addEventListener("load", async event => {
    let image = document.querySelector('.carousel-image');
    let isLoaded = image.complete && image.naturalHeight !== 0;
    if (isLoaded) {
        let minHeight = await getCarouselImageHeight();
        $('.carousel-image').css('max-height', minHeight);
    }
});

async function getCarouselImageHeight() {
    let maxHeight = 600;
    let carouselImages = document.getElementsByClassName("carousel-image");
    for (let i = 0; i < carouselImages.length; i++) {
        if (carouselImages[i].naturalHeight < maxHeight) {
            maxHeight = carouselImages[i].naturalHeight;
        }
    }
    return maxHeight;
}