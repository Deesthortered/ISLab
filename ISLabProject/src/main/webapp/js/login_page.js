function Logout() {
    var Http = new XMLHttpRequest();
    var url = window.location.href;
    var param = "logout=true";
    Http.open("POST", url);
    Http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    Http.setRequestHeader("Content-length", String(param.length));
    Http.send(param);
    document.location.reload(true);
}
