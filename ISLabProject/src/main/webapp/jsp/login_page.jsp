<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">

    <title>*! Little Storage !* Login Page</title>

    <style><%@include file="../css/login_page.css"%></style>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.0.5/handlebars.min.js"></script>

</head>
<body>
    <h1>Welcome to the System!</h1>
    <p>Please, enter login data for getting access.</p>
    <form method="post">
        <p>Enter login (only latin and digits):</p>
        <p><input type="text" name="login_input" required="required" pattern="[a-zA-Z0-9-]{1,30}"></p>
        <p>Enter password (only latin and digits):</p>
        <p><input type="password" name="pass_input" required="required" pattern="[a-zA-Z0-9-]{1,30}"></p>
        <p><input type="checkbox" name="save_session"> Stay in system</p>
        <div id="invalid_credentials"></div>
        <p><input type="submit"></p>
    </form>

    <script id="invalid_credentials_template" type="text/template" >
        <p class="invalid">Invalid login or/and password.</p>
    </script>

    <script><%@include file="../js/login_page.js"%></script>
</body>
</html>