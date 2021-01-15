$(document).ready(function () {
    let carouselImages = document.getElementsByClassName("carousel-image");
    let maxHeight = 600;
    for(let i = 0; i<carouselImages.length; i++){
        if(carouselImages[i].naturalHeight < maxHeight){
            maxHeight = carouselImages[i].naturalHeight;
        }
    }
    $('.carousel-image').css('max-height', maxHeight);
})