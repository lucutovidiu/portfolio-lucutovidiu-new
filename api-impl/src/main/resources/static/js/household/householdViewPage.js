const deleteGroupUrl = "/api/household";

async function deleteGroup(groupId, groupName) {
    let confirmMsg = "Sigur vrei sa stergi grupul: " + groupName;
    let deleteUrl = deleteGroupUrl + "/group/" + groupId;
    let successMsg = "Grup " + groupName + " sters cu success";
    let errorMsg = "Eroare stergere grup: " + groupName;
    await deleteItemOrGroup(confirmMsg, deleteUrl, successMsg, errorMsg);
}

async function deleteItemOrGroup(confirmMsg, deleteUrl, successMsg, errorMsg) {
    let confirmation = confirm(confirmMsg);
    if (confirmation) {
        try {
            let response = await fetch(deleteUrl, {method: 'DELETE'});
            response = await response.json();
            if (response) {
                displayAlert(successMsg, "alert alert-success");
                setTimeout(() => {
                    location.reload();
                }, 500)
            }
        } catch (exception) {
            console.log(exception);
            displayAlert(errorMsg, "alert alert-danger");
        }
    }
}

async function deleteGroupItem(groupId, groupItemId, itemName, groupName) {
    let confirmMsg = "Sigur vrei sa stergi articolul: " + itemName + " din grupul: " + groupName;
    let deleteUrl = deleteGroupUrl +"/group/" + groupId + "/item/" + groupItemId;
    let successMsg = "Articol " + itemName + " sters cu success din grupul: " + groupName;
    let errorMsg = "Eroare stergere articol: " + groupItemId + " din grupul: " + groupName;
    await deleteItemOrGroup(confirmMsg, deleteUrl, successMsg, errorMsg);
}