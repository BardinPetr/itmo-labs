<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form method="POST">
    <label>
        Email:
        <input type="email" name="login">
    </label>
    <label>
        Password:
        <input type="password" name="password">
    </label>
    <input type="submit" value="Login" formaction="/auth/login">
    <input type="submit" value="Register" formaction="/auth/register">
</form>

</body>
</html>
