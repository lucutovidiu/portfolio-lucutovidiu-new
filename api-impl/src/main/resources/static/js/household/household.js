const itemStartFromDate = "itemStartFromDate";
const itemExpirationDate = "itemExpirationDate";
const groupNameSubmit = "groupNameSubmit";
const itemNameSubmitForm = "itemNameSubmitForm";
const householdGroupId = "householdGroupId";
const groupPostUrl = "/api/household/group";
const groupsUrl = "/api/household/groups";
const postItemUrl = "/api/household/item";
const groupName = "groupName";
const itemName = "itemName";
const groupNameSubmitForm = "groupNameSubmitForm";
const householdSubmit = "householdSubmit";
const moreInfo = "moreInfo";

$(document).ready(function () {
    addDatePickerToDateInput("#" + itemStartFromDate);
    addDatePickerToDateInput("#" + itemExpirationDate);
    waitForAddGroupEvent();
    waitForAddItemEvent();
    initializeSelectGroup();
});

function initializeSelectGroup() {
    fetch(groupsUrl)
        .then(data => data.json())
        .then(groups => {
            groups.forEach(group => updateSelectGroup(group.groupName, group.id))
        });
}

function waitForAddItemEvent() {
    document.getElementById(householdSubmit).addEventListener("click", async (event) => {
        if ($("#" + itemNameSubmitForm)[0].checkValidity()) {
            event.preventDefault();
            fetch(postItemUrl, {method: 'POST', body: buildItemToGroupFormGroup()})
                .then(data => data.json())
                .then(item => {
                    if (item === true) {
                        displayAlert("Articol adaugat cu success!!!", "alert alert-success");
                        resetAddItemToGroupForm();
                    } else {
                        displayAlert("Group introdus negasit!!!", "alert alert-warning");
                    }
                })
        }
    })
}

function buildItemToGroupFormGroup() {
    let itemNameInput = document.getElementById(itemName).value.trim();
    let householdGroupIdInput = document.getElementById(householdGroupId).value.trim();
    let itemStartFromDateInput = document.getElementById(itemStartFromDate).value;
    let itemExpirationDateInput = document.getElementById(itemExpirationDate).value;
    let moreInfoInput = document.getElementById(moreInfo).value;
    const formData = new FormData();
    formData.append('householdGroupId', householdGroupIdInput);
    formData.append('itemName', itemNameInput);
    formData.append('itemStartFromDate', itemStartFromDateInput);
    formData.append('itemExpirationDate', itemExpirationDateInput);
    formData.append('moreInfo', moreInfoInput);
    return formData;
}

function resetAddItemToGroupForm() {
    document.getElementById(itemName).value = "";
    document.getElementById(moreInfo).value = "";
}

function waitForAddGroupEvent() {
    document.getElementById(groupNameSubmit).addEventListener("click", async (event) => {
        if ($("#" + groupNameSubmitForm)[0].checkValidity()) {
            event.preventDefault();
            let groupNameInput = document.getElementById(groupName).value.trim();
            if (groupNameInput.length > 0) {
                const formData = new FormData();
                formData.append('groupName', groupNameInput);
                try {
                    let postedGroup = await fetch(groupPostUrl, {method: 'POST', body: formData});
                    postedGroup = await postedGroup.json();
                    if (postedGroup.succeeded) {
                        displayAlert("Group adaugat cu success!!!", "alert alert-success");
                        updateSelectGroup(postedGroup.groupName, postedGroup.id);
                    } else {
                        displayAlert("Grup deja existent!!!", "alert alert-danger");
                        console.log(postedGroup)
                    }
                } catch (exception) {
                    console.log(exception);
                } finally {
                    document.getElementById(groupName).value = "";
                }
            }
        }
    })
}

function updateSelectGroup(groupName, groupValue) {
    $('#' + householdGroupId).append(new Option(groupName, groupValue));
}
