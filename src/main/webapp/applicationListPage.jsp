<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Applications</title>
    <style>
        table, th, td {
            border: 1px solid black;
        }
    </style>
</head>
<body>

<table>
    <th>Name</th>
    <th>Url</th>
    <th>MaxVersion</th>
<c:forEach var="application" items="${applicationList}">
    <tr>
        <td>${application.name}</td>
        <td>${application.url}</td>
        <td>${application.maxVersion}</td>
    </tr>
</c:forEach>
    </table>

</body>
</html>