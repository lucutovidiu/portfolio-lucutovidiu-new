const groupSyncUrl = "/api/household/sync-holiday";
document.getElementById("sync-holidays").addEventListener("click", async () => {
    try {
        $("#loading-panel").removeClass("noDisplay");
        let response = await fetchResponseStatus(groupSyncUrl, {},
            {'Content-Type': 'application/json'}, 'POST');
        if (response) {
            createNewsAlert("Successfully Synchronized holiday group!!!", "alert alert-success");
        } else {
            createNewsAlert("Error occurred please try again!!!", "alert alert-danger");
        }
    } catch (err) {
        console.log(err);
        createNewsAlert("Error occurred please try again!!!", "alert alert-danger");
    }
})

async function fetchResponseStatus(url, formData, headers, method) {
    return fetch(url, {
        method: method,
        body: formData,
        headers
    })
        .then(response => response.status === 201)
        .catch(err => {
            throw err;
        });
}

function createNewsAlert(msg, classes) {
    displayAlert(msg, classes, 2000);
    $("#loading-panel").addClass("noDisplay");
}
