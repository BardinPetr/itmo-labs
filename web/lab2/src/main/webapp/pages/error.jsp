<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.bardinpetr.itmo.lab2.context.RequestContextHelper" %>
<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>

<c:set var="basePath" value="${pageContext.request.contextPath}"/>

<c:if test="${pageContext.response.status < 400}">
    <c:redirect url="/login.jsp"/>
</c:if>

<html>
<head>
    <title>Error</title>
    <link rel="stylesheet" href="${basePath}/static/dist/style/ErrorPage.css">
</head>
<body>
Error ${pageContext.response.status}
<br>
${requestScope["jakarta.servlet.error.message"]}
<br>

<% if (RequestContextHelper.getUser(request).isEmpty()) {%>
<a href="${basePath}/login.jsp">Go to login</a>
<% } else { %>
<a href="${basePath}/index.jsp">Go to main page</a>
<% } %>
</body>
</html>