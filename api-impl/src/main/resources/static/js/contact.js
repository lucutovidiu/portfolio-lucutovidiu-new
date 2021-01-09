$(document).ready(function () {
    // Add smooth scrolling to all links
    $("#contactFormSubmit").on('click', function (event) {
        event.preventDefault();
        if ($("#contactForm")[0].checkValidity()) {
            $("#loading").removeClass("noDisplay");
            sendMsg();
        }
    });
    /*
    $.post("/api/email/contact", {"senderName": "ovi","senderEmail":"lucut_ovidiu@yahoo.com", "senderMessage":"msg"}, function (data, status){
               console.log(`${data} and status ${status}`);
            });
     */
});

function sendMsg() {
    $.ajax({
        url: '/api/email/contact',
        dataType: 'json',
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify({
            "senderName": $('#senderName').val(),
            "senderEmail": $('#senderEmail').val(),
            "senderMessage": $('#senderMessage').val()
        }),
        processData: false,
        success: function (data, status, jQxhr) {
            $("#loading").addClass("noDisplay");
            if (status) {
                $("#successMsg").removeClass("noDisplay");
            } else {
                $("#errorMsg").removeClass("noDisplay");
            }
            clearMessages();
        },
        error: function (jqXhr, textStatus, errorThrown) {
            $("#loading").addClass("noDisplay");
            clearMessages();
            console.log(errorThrown);
        }
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
