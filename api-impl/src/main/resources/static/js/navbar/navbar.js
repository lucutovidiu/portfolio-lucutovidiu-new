// $(document).ready(function () {
//     $("#newPortfolio").on('click', function (event) {
//         var xhttp = new XMLHttpRequest();
//         xhttp.onreadystatechange = function () {
//             console.log(this)
//             if (this.readyState == 4 && this.status == 200) {
//                 document.getElementsByTagName("body").innerHTML = this.responseText;
//             }
//         };
//         xhttp.open("GET", "/AddNewPortfolio", true);
//         xhttp.setRequestHeader("Authorization", sessionStorage.getItem("jwtToken"));
//         xhttp.send();
//     })
// })