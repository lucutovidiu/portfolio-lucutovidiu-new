const userName = "userName";
const userEmail = "userEmail";
const password = "password";
const gender = "gender";
const roles = "roles";
const deleteUserBtn = "deleteUser";
const newUserSelectValue = "NEW_USER";
const usersSelectField = "users";
const isAccountNonExpired = "isAccountNonExpired";
const isAccountNonLocked = "isAccountNonLocked";
const isCredentialsNonExpired = "isCredentialsNonExpired";
const isEnabled = "isEnabled";
const submitUser = "submitUser";
const editUser = "editUser";
const addUserForm = "addUserForm";
const postUserUrl = "/api/users/post-user";
const getUsersUrl = "/api/users";
const editUserUrl = "/api/users/edit-user";
const deleteUserUrl = "/api/users/userId/";
const defaultBoxTimeout = 2000;
let selectedUserId = "";

$(document).ready(function () {
    initAllUsers();
    postNewUser();
    userEditListener();
    userDeleteListener();
    editNewUser();
});

function userDeleteListener() {
    document.getElementById(deleteUserBtn).addEventListener("click", (event) => {
        let userSelect = document.getElementById(usersSelectField);
        let userText = userSelect.options[userSelect.selectedIndex].text;
        let confirmUser = confirm("Are You sure you want to delete User: " + userText);
        if (confirmUser) {
            let userId = userSelect.value;
            fetch(deleteUserUrl + userId, {method: 'DELETE'})
                .then(data => data.json())
                .then(confirmDeletion => {
                    if (confirmDeletion) {
                        displayAlert("User successfully deleted.", "alert alert-success", defaultBoxTimeout);
                        slowReloadPage();
                    }
                })
        }
    })
}

function userEditListener() {
    document.getElementById(usersSelectField).addEventListener("change", (event) => {
        let userId = event.target.value;
        let isNewUser = newUserSelected(userId);
        if (!isNewUser) {
            setUserToUsersForm(userId);
        }
    })
}

function setUserToUsersForm(userId) {
    fetch(deleteUserUrl + userId)
        .then(data => data.json())
        .then(user => setNewUserToForm(user));
}

function setNewUserToForm(user) {
    selectedUserId = user.id;
    $("#" + userName).val(user.userName);
    $("#" + userEmail).val(user.userEmail);
    $("#" + gender + " option")
        .removeAttr('selected')
        .filter("[value=" + user.gender + "]")
        .attr('selected', true);
    $("#" + password).val("");
    $("#" + isAccountNonExpired).prop("checked", !user.isAccountNonExpired);
    $("#" + isAccountNonLocked).prop("checked", !user.isAccountNonLocked);
    $("#" + isCredentialsNonExpired).prop("checked", !user.isCredentialsNonExpired);
    $("#" + isEnabled).prop("checked", !user.isEnabled);
    $("#" + roles + " option").removeAttr('selected');
    user.roles.forEach(role => {
        $("#" + roles + " option")
            .filter("[value=" + role + "]")
            .attr('selected', true)
    });
}

function newUserSelected(user) {
    if (user !== newUserSelectValue) {
        document.getElementById(deleteUserBtn).removeAttribute("disabled");
        $("#" + editUser).removeClass("noDisplay");
        $("#" + submitUser).addClass("noDisplay");
        return false;
    } else {
        document.getElementById(deleteUserBtn).setAttribute("disabled", "disabled");
        $("#" + editUser).addClass("noDisplay");
        $("#" + submitUser).removeClass("noDisplay");
        return true;
    }
}

function initAllUsers() {
    fetch(getUsersUrl)
        .then(data => data.json())
        .then(usersInfo => {
            usersInfo.forEach(user => $('#' + usersSelectField)
                .append(new Option(formatUser(user.userName, user.userEmail), user.id)))
        });
    resetUserNameAndPassword();
}

function resetUserNameAndPassword(){
    setTimeout(()=>{
        $("#" + userName).val("");
        $("#" + password).val("");
    },500);
}

function formatUser(userName, userEmail) {
    return userName + "(" + userEmail + ")";
}

function editNewUser() {
    $("#" + editUser).on("click", (event) => {
        event.preventDefault();
        fetch(editUserUrl, {
            method: 'PUT',
            body: JSON.stringify(buildJsonFromValidFormToEditUser()),
            headers: {'Content-Type': 'application/json'}
        })
            .then(data => data.json())
            .then(userUpdated => {
                if (userUpdated) {
                    displayAlert("User successfully edited.", "alert alert-success", defaultBoxTimeout);
                    slowReloadPage();
                } else {
                    displayAlert("Error edit user.", "alert alert-danger", defaultBoxTimeout);
                }
            })
    })
}


function postNewUser() {
    $("#" + submitUser).on("click", (event) => {
        if ($("#" + addUserForm)[0].checkValidity()) {
            event.preventDefault();
            let userData = JSON.stringify(buildJsonFromValidFormToAddNewUser());
            fetch(postUserUrl, {
                method: 'POST',
                body: userData,
                headers: {'Content-Type': 'application/json'}
            })
                .then(data => data.json())
                .then(data => {
                    if (!checkForErrorByStatusCode(data, 201)) {
                        console.log(data.id);
                        displayAlert("User successfully created.", "alert alert-success", defaultBoxTimeout);
                        slowReloadPage();
                    } else {
                        displayAlert(data.error, "alert alert-danger", defaultBoxTimeout);
                    }
                })
                .catch(err => {
                    console.log(err);
                    displayAlert(err, "alert alert-danger", defaultBoxTimeout);
                })
        }
    });
}

function slowReloadPage() {
    setTimeout(() => {
        location.reload();
    }, 1000)
}

function checkForErrorByStatusCode(result, correctStatus) {
    if (result.status) {
        if (result.status !== correctStatus) {
            return true;
        }
    }
    return false;
}

function buildJsonFromValidFormToAddNewUser() {
    let formData = {};
    formData[userName] = $("#" + userName).val().trim();
    formData[userEmail] = $("#" + userEmail).val().trim();
    formData[password] = $("#" + password).val().trim();
    formData[gender] = $("#" + gender).val();
    formData[roles] = $("#" + roles).val();
    formData[isAccountNonExpired] = !document.querySelector("#" + isAccountNonExpired).checked;
    formData[isAccountNonLocked] = !document.querySelector("#" + isAccountNonLocked).checked;
    formData[isCredentialsNonExpired] = !document.querySelector("#" + isCredentialsNonExpired).checked;
    formData[isEnabled] = !document.querySelector("#" + isEnabled).checked;
    return formData;
}

function buildJsonFromValidFormToEditUser() {
    let formData = buildJsonFromValidFormToAddNewUser();
    formData["id"] = selectedUserId;
    return formData;
}