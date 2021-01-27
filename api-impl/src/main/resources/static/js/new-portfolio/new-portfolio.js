const thumbImage = "thumbImage";
const moreImages = "moreImages";
const title = "title";
const shortDescription = "shortDescription";
const technologiesUsed = "technologiesUsed";
const fullDescription = "fullDescription";
const projectStartDate = "projectStartDate";
const projectEndDate = "projectEndDate";
const httpAccessLink = "httpAccessLink";
const repoLink = "repoLink";
const uploadPortfolioDataUrl = "/api/portfolio/upload-data";
const editPortfolioDataUrl = "/api/portfolio/edit-portfolio";
const uploadPortfolioThumbnailUrl = "/api/portfolio/upload-file/type/THUMBNAIL/portfolio-id/";
const uploadPortfolioMoreImagesUrl = "/api/portfolio/upload-file/type/MORE_IMAGES/portfolio-id/";
const portfolioId = "portfolioId";
const DONE = "DONE";

$(document).ready(function () {
    addDatePickerToDateInput("#" + projectStartDate);
    addDatePickerToDateInput("#" + projectEndDate);
    listenToAddPortfolioFormAndSendItToBackend();
});

function listenToAddPortfolioFormAndSendItToBackend() {
    $("#p-submit").on('click', async function (event) {
        if ($("#p-add-form")[0].checkValidity()) {
            event.preventDefault();
            $("#p-submit").prop("disabled", true);
            $("#loading").removeClass("noDisplay");
            if (isEditModeEnabled()) {
                await editPortfolio();
            } else {
                await addPortfolio();
            }
        }
    });
}

async function editPortfolio() {
    try {
        let portfolioIdVar = await postFormWithoutPictures(editPortfolioDataUrl);
        await postThumbnailImage(portfolioIdVar);
        await sendSiteImages(portfolioIdVar);
        successBranchEditPortfolio();
    } catch (err) {
        console.log(err);
        errBranch();
    }
}

async function addPortfolio() {
    let portfolioId;
    try {
        portfolioId = await postFormWithoutPictures(uploadPortfolioDataUrl);
        await postThumbnailImage(portfolioId);
        await sendSiteImages(portfolioId);
        successBranchNewPortfolio();
    } catch (err) {
        console.log(err);
        if (portfolioId) {
            await deletePortfolioData(portfolioId);
        }
        errBranch();
    }
}

function isEditModeEnabled() {
    return typeof $('#' + portfolioId).val() !== "undefined";
}

function postFormWithoutPictures(url) {
    return new Promise((complete, err) => {
        fetch(url, {
            method: 'POST',
            body: getFormDataInJsonFormat(),
            headers: {'Content-Type': 'application/json'}
        })
            .then(response => response.json())
            .then(data => {
                if (data.succeed) {
                    return complete(data.portfolioId);
                } else return err(data);
            })
            .catch(err => {
                return err(err);
            });
    });
}

async function postThumbnailImage(portfolioId) {
    const fileInput = document.getElementById(thumbImage);
    return postSingleImage(uploadPortfolioThumbnailUrl + portfolioId, fileInput.files[0]);
}

async function postSingleImage(url, file) {
    return new Promise(async (complete, err) => {
        if (typeof file !== "undefined") {
            const formData = new FormData();
            formData.append('file', file);
            try {
                let response = await fetch(url, {method: 'POST', body: formData});
                response = await response.json();
                if (!response.succeed) {
                    return err(response);
                } else {
                    return complete(DONE);
                }
            } catch (error) {
                return err(error);
            }
        } else return complete(DONE);
    })
}

async function sendSiteImages(portfolioId) {
    return new Promise(async (complete, err) => {
        let moreImagesInput = document.getElementById(moreImages);
        if (moreImagesInput.files.length !== 0) {
            for (let i = 0; i < moreImagesInput.files.length; i++) {
                const formData = new FormData();
                formData.append('file', moreImagesInput.files[i]);
                try {
                    await postSingleImage(uploadPortfolioMoreImagesUrl + portfolioId, moreImagesInput.files[i]);
                } catch (error) {
                    return err(error);
                }
            }
        }
        return complete(DONE);
    })
}

function errBranch() {
    $("#loading").addClass("noDisplay");
    $("#errorMsg").removeClass("noDisplay");
    resetInputInput();
}

function successBranchNewPortfolio() {
    $("#loading").addClass("noDisplay");
    $("#successMsg").removeClass("noDisplay");
    resetMessagesBox();
    resetInputInput();
}

function successBranchEditPortfolio() {
    $("#loading").addClass("noDisplay");
    $("#successMsg").removeClass("noDisplay");
    resetMessagesBox();
    resetPictureFields();
}

function resetPictureFields() {
    $('#' + thumbImage).val("");
    $('#' + moreImages).val("");
}

function resetMessagesBox() {
    setTimeout(function () {
        $("#successMsg").addClass("noDisplay");
        $("#errorMsg").addClass("noDisplay");
        $("#p-submit").prop("disabled", false);
    }, 4000);
}

function resetInputInput() {
    setTimeout(function () {
        $('#' + title).val("");
        $('#' + shortDescription).val("");
        $('#' + technologiesUsed).val("");
        $('#' + fullDescription).val("");
        $('#' + httpAccessLink).val("");
        $('#' + repoLink).val("");
        resetPictureFields()
    }, 4000);
}

function getFormDataInJsonFormat() {
    let formData = new FormData();
    formData.append(title, $('#' + title).val());
    formData.append(shortDescription, $('#' + shortDescription).val());
    formData.append(technologiesUsed, $('#' + technologiesUsed).val());
    formData.append(fullDescription, $('#' + fullDescription).val());
    formData.append(projectStartDate, formatDate($('#' + projectStartDate).val()));
    formData.append(projectEndDate, formatDate($('#' + projectEndDate).val()));
    formData.append(httpAccessLink, $('#' + httpAccessLink).val());
    formData.append(repoLink, $('#' + repoLink).val());
    let portfolioIdVar = document.getElementById(portfolioId);
    if (portfolioIdVar) {
        formData.append("id", $('#' + portfolioId).val());
    }
    let result = {};
    for (let entry of formData.entries()) {
        result[entry[0]] = entry[1];
    }
    return JSON.stringify(result);
}