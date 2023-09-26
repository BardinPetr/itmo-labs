<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="basePath" value="${pageContext.request.contextPath}"/>

<c:if test="${requestScope.user != null}">
    <c:redirect url="/index.jsp"/>
</c:if>

<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="${basePath}/static/dist/style/ErrorPage.css">
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
    <input type="submit" value="Login" formaction="/app/auth/login">
    <input type="submit" value="Register" formaction="/app/auth/register">
</form>

</body>
</html>
