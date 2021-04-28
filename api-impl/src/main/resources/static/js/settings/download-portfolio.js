window.onload = () => {
    document.getElementById("download-portfolio").addEventListener("click", async () => {
        try {
            $("#loading-panel").removeClass("noDisplay");
            let portfoliosDataDto = await requestPortfolio();
            let requestId = portfoliosDataDto.requestId;
            let zipResponse = await keepWatchingServerUntilZippingHasFinished(requestId);
            $("#download-portfolio").css("display", "none");
            createDownloadLink(requestId, zipResponse.zipName);
            createAlert("Successfully created portfolio zip!!!", "alert alert-success");
        } catch (err) {
            console.log(err);
            createAlert("Error occurred please try again!!!", "alert alert-danger");
        }
    });

    document.getElementById("clear-temp-dir").addEventListener("click", async () => {
        clearTempFiles();
    })
    loadJsonPortfolio();
}

function loadJsonPortfolio() {
    $("#load-json-portfolio").on('click', async function (event) {

    });
}

function clearTempFiles() {
    $("#loading-panel").removeClass("noDisplay");
    let url = "/api/get-portfolio-data/clear-temp-dir";
    fetchResponse(url, {}, {'Content-Type': 'application/json'}, 'DELETE')
        .then(response => {
            if (response === true) {
                resetDownloadLink();
                createAlert("Successfully removed all temp files!!!", "alert alert-success");
            }
        })
        .catch(err => {
            console.log(err);
            createAlert("Error occurred please try again!!!", "alert alert-danger");
        })
}

function createAlert(msg, classes) {
    displayAlert(msg, classes);
    $("#loading-panel").addClass("noDisplay");
}

function createDownloadLink(rootDir, zipName) {
    let downloadBtn = document.createElement("a");
    downloadBtn.innerText = "Click here to download Portfolio";
    downloadBtn.setAttribute("href", "/api/get-portfolio-data/" + rootDir + "/" + zipName);
    downloadBtn.setAttribute("class", "btn btn-success mt-2 hand-pointer");
    document.getElementById("download-pane").append(downloadBtn);
}

function resetDownloadLink() {
    let downloadBtn = document.createElement("span");
    downloadBtn.innerText = "Get As zip file";
    let oldDownloadLink = document.getElementById("download-pane").children[1];
    if (oldDownloadLink) oldDownloadLink.remove();
    $("#download-portfolio").css("display", "block");
}

async function keepWatchingServerUntilZippingHasFinished(requestId) {
    let url = "/api/get-portfolio-data/request-zip";
    let response = await fetchResponse(url, JSON.stringify({"requestType": "REQUEST_STATUS", requestId}),
        {'Content-Type': 'application/json'}, 'POST');
    if (response.requestStatus !== "DONE") {
        setTimeout(() => keepWatchingServerUntilZippingHasFinished(requestId), 1000);
    } else {
        return response;
    }
}

async function requestPortfolio() {
    let url = "/api/get-portfolio-data/request-zip";
    return fetchResponse(url, JSON.stringify({"requestType": "CREATE_PORTFOLIO_DATA_REQUEST"}),
        {'Content-Type': 'application/json'}, 'POST');
}

async function fetchResponse(url, formData, headers, method) {
    return fetch(url, {
        method: method,
        body: formData,
        headers
    })
        .then(response => response.json())
        .then(data => {
            return data;
        })
        .catch(err => {
            throw err;
        });
}
