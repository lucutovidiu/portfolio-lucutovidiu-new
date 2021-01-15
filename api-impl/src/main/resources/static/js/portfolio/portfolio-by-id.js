window.addEventListener("load", async event => {
    var image = document.querySelector('.carousel-image');
    var isLoaded = image.complete && image.naturalHeight !== 0;
    if(isLoaded) {
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