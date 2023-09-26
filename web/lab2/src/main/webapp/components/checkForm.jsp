<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="../WEB-INF/custom.tld" %>

<c:set var="restrictions" value="${applicationScope.areaRestrictions}"/>

<form method="get" action="/app/check">

    <table id="table-input">
        <tr>
            <td><label>Select X </label></td>
            <td>
                <c:forEach var="i" items="${restrictions.XOptions}">
                    <label>
                        <input id="select-x-${i}" type="checkbox" name="x" value="${i}">
                            ${i}
                    </label>
                </c:forEach>
            </td>
        </tr>
        <tr>
            <td></td>
            <td><span id="error-msg-x"></span></td>
        </tr>
        <tr>
            <td><label for="input-y">Enter Y</label></td>
            <td>
                <input
                        id="input-y"
                        name="y"
                        type="text"
                        maxlength="9"
                        placeholder="<x:rangeString range='${restrictions.YRange}' inclusive='${restrictions.YInclusive}'/>"
                >
            </td>
        </tr>
        <tr>
            <td></td>
            <td><span id="error-msg-y"></span></td>
        </tr>
        <tr>
            <td><label for="input-r">Enter R</label></td>
            <td>
                <input
                        id="input-r"
                        name="r"
                        type="text"
                        maxlength="10"
                        placeholder="<x:rangeString range='${restrictions.RRange}' inclusive='${restrictions.RInclusive}'/>"
                >
            </td>
        </tr>
        <tr>
            <td></td>
            <td><span id="error-msg-r"></span></td>
        </tr>
        <tr>
            <td><input type="submit" value="Check" id="send-btn"></td>
            <td><span id="error-msg-all"></span></td>
        </tr>
    </table>

</form>