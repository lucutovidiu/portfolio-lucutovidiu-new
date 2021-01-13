$(document).ready(function () {
    // Add smooth scrolling to all links
    $("#contactFormSubmit").on('click', function (event) {
        event.preventDefault();
        if ($("#contact-form")[0].checkValidity()) {
            $("#loading").removeClass("noDisplay");
            postRequest('/api/email/contact', {
                "senderName": $('#senderName').val(),
                "senderEmail": $('#senderEmail').val(),
                "senderMessage": $('#senderMessage').val()
            });
        }
    });
});

function postRequest(url, msg) {
    postJsonRequest(url, msg).then(successResponse => {
        $("#loading").addClass("noDisplay");
        if (successResponse.data) {
            $("#successMsg").removeClass("noDisplay");
        } else {
            $("#errorMsg").removeClass("noDisplay");
        }
        clearMessages();
    }, errorResponse => {
        console.log(errorResponse.errorThrown);
        $("#loading").addClass("noDisplay");
        $("#errorMsg").removeClass("noDisplay");
        clearMessages();
    });
}

function clearMessages() {
    setTimeout(function () {
        $("#loading").addClass("noDisplay");
        $("#successMsg").addClass("noDisplay");
        $("#errorMsg").addClass("noDisplay");
        $("#senderName").val("");
        $("#senderEmail").val("");
        $("#senderMessage").val("");
    }, 4000)
}
