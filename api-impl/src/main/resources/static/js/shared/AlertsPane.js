const alertsPane = "alerts-pane";

function displayAlert(msg, classes, timeout) {
    let alert = document.createElement("div");
    alert.innerText = msg;
    alert.setAttribute("class", classes);
    alert.setAttribute("role", "alert");
    document.getElementById(alertsPane).append(alert);
    setTimeout(() => {
        document.getElementById(alertsPane).removeChild(alert);
    }, timeout ? timeout : 1000);
}