<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <form>
        <label>
            Email:
            <input type="email" name="login">
        </label>
        <label>
            Password:
            <input type="password" name="password">
        </label>
        <input type="submit" value="Login" formaction="/login">
        <input type="submit" value="Register" formaction="/register">
    </form>

</body>
</html>
