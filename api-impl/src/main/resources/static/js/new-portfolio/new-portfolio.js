$(document).ready(function () {
    addDatePicker("#p-date-from");
    addDatePicker("#p-date-to");
    listenToAddPortfolioForm ();

});

function listenToAddPortfolioForm () {
    $("#p-submit").on('click', function (event) {
        event.preventDefault();
        if ($("#p-add-form")[0].checkValidity()) {
            postRequest('/api/email/contact', {
                "senderName": $('#senderName').val(),
                "senderEmail": $('#senderEmail').val(),
                "senderMessage": $('#senderMessage').val()
            });
        }
    });
}

function addDatePicker(formId) {
    $(formId).datepicker({
        weekStart: 1,
        daysOfWeekHighlighted: "6,0",
        autoclose: true,
        todayHighlight: true,
    });
    $(formId).datepicker("setDate", new Date());
}