<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>MyPlanner Home</title>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.1/angular.min.js"></script>
    <link rel="stylesheet" href="/resources/style.css"/>
</head>
<body ng-app>
    <div id="profile">
        <h3>Profile:</h3>
        <p>ID: ${profile.id}</p>
        <p>Name: ${profile.name}</p>
        <p>Login id: ${profile.loginId}</p>
    </div>

    <div id="courses" ng-init="${courses}">
        <ul>
            <li ng-repeat="c in courses | filter:search">
                Name: {{c.name}}
                <ul>
                    <li>Id: {{c.id}}</li>
                    <li>Course code: {{c.courseCode}}</li>
                </ul>
            </li>
        </ul>
    </div>
</body>
</html>
