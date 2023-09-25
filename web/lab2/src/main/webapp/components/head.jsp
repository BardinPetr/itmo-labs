<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.lang.String" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %>

<c:set var="restrictions" value="${applicationScope.areaRestrictions}"/>

<head>
    <title>Lab 2</title>
    <link rel="stylesheet" href="static/dist/MainPage.css">

    <script>
        window.lab = {
            constraints: {
                ${
                    String.join(
                        ", ",
                        ["XRange", "YRange", "RRange", "XInclusive", "YInclusive", "RInclusive"]
                            .stream()
                            .map(i -> "%s: %s".formatted(i, Arrays.toString(restrictions[i])))
                            .toList()
                    )
                }
            },
            history: ${
                pointsDatabase.all
                    .stream()
                    .map(i -> [i.id(), i.point().x(), i.point().y(), i.area().r(), i.isInside(), i.timestamp().getEpochSecond(), i.executionTime().toNanos()/1e6])
                    .toList()
            }
        }
    </script>
    <script src="static/dist/MainPage.js"></script>
</head>