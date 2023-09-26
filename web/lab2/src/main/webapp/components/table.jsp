<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.Instant" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.function.Function" %>

<%
    DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.getDefault());

    pageContext.setAttribute("formatDate", (Function<Instant, String>) (i) -> formatter.format(new Date(i.toEpochMilli())));
    pageContext.setAttribute("formatDouble", (Function<Double, String>) "%.3f"::formatted);
%>

<form class="inline-form" method="post" action="/app/db/clear">
    <input type="submit" value="Clear" id="clear-btn">
</form>
<table id="table-result" class="data-table">
    <tr>
        <c:forEach var="cname" items="${['ID', 'Timestamp', 'Inside', 'R', 'X', 'Y', 'Execution Time, ms']}">
            <td>${cname}</td>
        </c:forEach>
    </tr>
    <c:forEach var="i" items="${resultsModel.list}">
        <tr>
            <td>${i.id()}</td>
            <td>${formatDate.apply(i.timestamp())}</td>
            <td>${i.inside ? "&#128309" : "&#128308"}</td>
            <td>${formatDouble.apply(i.area().r())}</td>
            <td>${formatDouble.apply(i.point().x())}</td>
            <td>${formatDouble.apply(i.point().y())}</td>
            <td>${formatDouble.apply(i.executionTime().toNanos()/1e6)}</td>
        </tr>
    </c:forEach>
</table>
