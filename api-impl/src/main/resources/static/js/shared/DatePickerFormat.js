const datePickerOptions = {
    weekStart: 1,
    daysOfWeekHighlighted: "6,0",
    autoclose: true,
    todayHighlight: true,
}

function addDatePickerToDateInput(formId) {
    $(formId).datepicker(datePickerOptions);
    let defaultValue = $(formId).val();
    $(formId).datepicker("setDate", formatDate(defaultValue));
}

function formatDate(date) {
    let unformattedDate = date ? date : new Date();
    let formattedDate = moment(unformattedDate).format("YYYY-MM-DD");
    if (formattedDate === "Invalid date")
        throw new Error("Invalid Date: " + date);
    return formattedDate;
}