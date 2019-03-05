class Router {
    static initialize(){
        addEventListener('hashchange', Router.handleHash);
        Router.handleHash();
    }
    static handleHash() {

    }
}
class TemplateHandler {
    static Render(templateName, data) {
        const templateElement = document.getElementById(templateName);
        const templateSource = templateElement.innerHTML;
        const renderFn = Handlebars.compile(templateSource);
        return renderFn(data);
    }
}

function DefineUser() {
    document.location.hash = '';
    let role = '${role}';
    const field = document.getElementById('username_field');
    field.innerHTML = TemplateHandler.Render('username_template', { username: role})
}
function Logout() {
    document.location.hash = '';
    let Http = new XMLHttpRequest();
    let url = window.location.href.split('#')[0];
    let param = "logout=true";
    Http.open("GET", url+"?"+param, true);
    Http.send(param);
    alert("You are logged out.");
    document.location.reload(true);
}

(async () => {
    DefineUser();
    Router.initialize();
})();


