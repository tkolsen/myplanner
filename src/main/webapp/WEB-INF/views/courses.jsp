<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Courses</title>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.1/angular.min.js"></script>
</head>
<body>
    <div ng-app="" ng-init="${courses}">
        <p>Filter by name: <input type="text" ng-model="search.name"/></p>
        <h3>Courses</h3>
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
