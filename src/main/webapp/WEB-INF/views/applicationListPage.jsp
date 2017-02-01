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
<body ng-app="myApp">
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
<script src="<c:url value='/static/js/app.js' />"></script>
<script src="<c:url value='/static/js/service/app_service.js' />"></script>
<script src="<c:url value='/static/js/controller/app_controller.js' />"></script>

<div class="generic-container" ng-controller="AppController as ctrl">

<%--<table>--%>
    <%--<th>Name</th>--%>
    <%--<th>Url</th>--%>
    <%--<th>MaxVersion</th>--%>
<%--<c:forEach var="application" items="${applicationList}">--%>
    <%--<tr>--%>
        <%--<td>${application.name}</td>--%>
        <%--<td>${application.url}</td>--%>
        <%--<td>${application.maxVersion}</td>--%>
    <%--</tr>--%>
<%--</c:forEach>--%>
    <%--</table>--%>

<div class="tablecontainer">
    <table class="table table-hover">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Url</th>
            <th>MaxVersion</th>
            <th width="20%"></th>
        </tr>
        </thead>
        <tbody>
        <tr ng-repeat="app in ctrl.apps">
            <td><span ng-bind="app.id"></span></td>
            <td><span ng-bind="app.name"></span></td>
            <td><span ng-bind="app.url"></span></td>
            <td><span ng-bind="app.maxVersion"></span></td>
            <td>
                <button type="button" ng-click="ctrl.edit(app.id)" class="btn btn-success custom-width">Edit</button>  <button type="button" ng-click="ctrl.remove(appapp.id)" class="btn btn-danger custom-width">Remove</button>
            </td>
        </tr>
        </tbody>
    </table>
</div>

</body>
</html>