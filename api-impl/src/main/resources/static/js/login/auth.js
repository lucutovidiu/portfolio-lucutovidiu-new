// $(document).ready(function () {
//     $("#loginbtn").on('click', function (event) {
//         event.preventDefault();
//         if ($("#loginForm")[0].checkValidity()) {
//             $("#loading").removeClass("noDisplay");
//             postRequest('/api/login', {
//                 "userName": $('#emailField').val(),
//                 "password": $('#passwordField').val(),
//             });
//         }
//     });
// });
//
// function postRequest(url, msg) {
//     postJsonRequest(url, msg).then(successResponse => {
//         $("#loading").addClass("noDisplay");
//         if (successResponse.data.loginSucceed) {
//             $("#successMsg").removeClass("noDisplay");
//             sessionStorage.setItem("jwtToken", successResponse.data.jwtToken);
//         } else {
//             $("#errorMsg").removeClass("noDisplay");
//         }
//         clearMessages();
//     }, errorResponse => {
//         console.log(errorResponse.errorThrown);
//         $("#loading").addClass("noDisplay");
//         $("#errorMsg").removeClass("noDisplay");
//         clearMessages();
//     })
// }
//
// function clearMessages() {
//     setTimeout(function () {
//         $("#loading").addClass("noDisplay");
//         $("#successMsg").addClass("noDisplay");
//         $("#errorMsg").addClass("noDisplay");
//         $("#emailField").val("");
//         $("#passwordField").val("");
//     }, 4000)
// }