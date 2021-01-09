$(document).ready(function () {
    // Add smooth scrolling to all links
    $(".getstartedtbn").on('click', function (event) {
        $('html,body').animate({scrollTop: $(window).height()-$(".lp_info_wrapper_intro").height()},'slow');
    });
});
