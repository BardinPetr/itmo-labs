<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set value="JSTL Core Tags Example" var="pageTitle"/>

<html>
<head>
    <title><c:out value="${pageTitle}"/></title>
    <link rel="stylesheet" href="static/dist/MainPage.css">
    <%--    <script src="static/dist/MainPage.js"></script>--%>

</head>
<body>

<%--<c:import url="../components/header.jsp"/>--%>
<%--<%@ include file="../components/header.jsp"%>--%>

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
