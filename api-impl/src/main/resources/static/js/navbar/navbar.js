$(document).ready(function () {
    $("#navbar-menu-btn").on('click', function (event) {
        $("#navbar-mobile-items-hidden-menu").toggleClass("navbar-mobile-items-hidden");
        animateHiddenMenu();
    })
    $("#navbar-back-btn").on('click', function (event) {
        $("#navbar-mobile-items-hidden-menu").toggleClass("navbar-mobile-items-hidden");
    })
});

function animateHiddenMenu(){
    let hiddenMenu = $( "#navbar-mobile-items-hidden-menu");
    hiddenMenu.fadeTo( "slow" , 0.5, function() {
        // Animation complete.
    });
    hiddenMenu.fadeTo( "slow" , 1, function() {
        // Animation complete.
    });
}
// $(document).ready(function () {
//     $("#newPortfolio").on('click', function (event) {
//         var xhttp = new XMLHttpRequest();
//         xhttp.onreadystatechange = function () {
//             console.log(this)
//             if (this.readyState == 4 && this.status == 200) {
//                 document.getElementsByTagName("body").innerHTML = this.responseText;
//             }
//         };
//         xhttp.open("GET", "/AddNewPortfolio", true);
//         xhttp.setRequestHeader("Authorization", sessionStorage.getItem("jwtToken"));
//         xhttp.send();
//     })
// })