$(document).ready(function () {
    addDatePicker("#projectStartDate");
    addDatePicker("#projectEndDate");
    listenToAddPortfolioForm();
});

function listenToAddPortfolioForm() {
    $("#p-submit").on('click', async function (event) {
        if ($("#p-add-form")[0].checkValidity()) {
            event.preventDefault();
            $("#p-submit").prop("disabled", true);
            $("#loading").removeClass("noDisplay");
            let response;
            try {
                response = await sendPostRequest("/api/portfolio/upload-data", getFormDataInJsonFormat(), {'Content-Type': 'application/json'}, 'POST');
                await sendThumbnailImage(response.portfolioId);
                await sendSiteImages(response.portfolioId);
                successBranch();
            } catch (err) {
                console.log("-----------------------");
                console.log(response);
                console.log("-----------------------");
                if (response) {
                    deletePortfolioData(response.portfolioId);
                }
                errBranch();
            }
        }
    });
}

async function sendThumbnailImage(portfolioId) {
    return new Promise(async (complete, err) => {
        let url = "/api/portfolio/upload-file/type/THUMBNAIL/portfolio-id/" + portfolioId;
        const fileInput = document.getElementById("thumbImage");
        const formData = new FormData();
        if (!fileInput.files[0]) err(formData);
        formData.append('file', fileInput.files[0]);
        try {
            let response = await sendPostRequest(url, formData, {}, 'POST')
            complete(response);
        } catch (error) {
            err(error);
        }
    })
}

async function sendSiteImages(portfolioId) {
    return new Promise(async (complete, err) => {
        let url = "/api/portfolio/upload-file/type/MORE_IMAGES/portfolio-id/" + portfolioId;
        const fileInput = document.getElementById("moreImages");
        for (let i = 0; i < fileInput.files.length; i++) {
            const formData = new FormData();
            formData.append('file', fileInput.files[i]);
            try {
                let response = await sendPostRequest(url, formData, {}, 'POST')
                complete(response);
            } catch (error) {
                err(error);
            }
        }
    })
}

function errBranch() {
    $("#loading").addClass("noDisplay");
    $("#errorMsg").removeClass("noDisplay");
    enableInput();
}

function successBranch() {
    $("#loading").addClass("noDisplay");
    $("#successMsg").removeClass("noDisplay");
    enableInput();
}

function enableInput() {
    setTimeout(function () {
        $("#loading").addClass("noDisplay");
        $("#successMsg").addClass("noDisplay");
        $("#errorMsg").addClass("noDisplay");
        $("#p-submit").prop("disabled", false);
        $('#title').val("");
        $('#shortDescription').val("");
        $('#technologiesUsed').val("");
        $('#fullDescription').val("");
        $('#httpAccessLink').val("");
        $('#repoLink').val("");
        $('#thumbImage').val("");
        $('#moreImages').val("");
    }, 4000);
}

function getFormDataInJsonFormat() {
    let formData = new FormData();
    formData.append("title", $('#title').val());
    formData.append("shortDescription", $('#shortDescription').val());
    formData.append("technologiesUsed", $('#technologiesUsed').val());
    formData.append("fullDescription", $('#fullDescription').val());
    formData.append("projectStartDate", $('#projectStartDate').val());
    formData.append("projectEndDate", $('#projectEndDate').val());
    formData.append("httpAccessLink", $('#httpAccessLink').val());
    formData.append("repoLink", $('#repoLink').val());
    let result = {};
    for (var entry of formData.entries()) {
        result[entry[0]] = entry[1];
    }
    return JSON.stringify(result);
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