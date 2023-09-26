<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="pointsDatabase" class="ru.bardinpetr.itmo.lab2.storage.impl.PointResultDatabase" scope="application"/>
<jsp:useBean id="resultsModel" class="ru.bardinpetr.itmo.lab2.models.ResultsTableModel" scope="request"/>
<jsp:setProperty name="resultsModel" property="dao" value="${pointsDatabase}"/>
<jsp:setProperty name="resultsModel" property="ownerUsername" value="${requestScope.user.login()}"/>

<html>
<%@ include file="../components/head.jsp" %>
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
