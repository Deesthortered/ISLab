function CheckInvalidCredentialsAttribute() {
    let val = "${invalid_credentials}";
    if (val === "true") {
        const templateElement = document.getElementById('invalid_credentials_template');
        const templateSource = templateElement.innerHTML;
        const renderFn = Handlebars.compile(templateSource);

        const invalid_credentials = document.getElementById('invalid_credentials');
        invalid_credentials.innerHTML = renderFn({});
    }
}

(async () => {
    CheckInvalidCredentialsAttribute();
})();
