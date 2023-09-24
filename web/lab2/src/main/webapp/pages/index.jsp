<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Lab 2</title>
    <link rel="stylesheet" href="static/dist/MainPage.css">
    <%--    <script src="static/dist/MainPage.js"></script>--%>

</head>
<body>

<%@ include file="../components/header.jsp" %>

<table class="container" id="page-base">
    <tr>
        <td>
            <%@ include file="../components/canvas.jsp" %>
            <%@ include file="../components/checkForm.jsp" %>
        </td>
        <td>
            <%@ include file="../components/table.jsp" %>
        </td>
    </tr>
</table>


</body>
</html>
