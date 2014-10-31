<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.26/angular.min.js"></script>
</head>
<body>
    <div ng-app="" ng-init="names=['Kim', 'Tom', 'Dagfinn']">
        <p>Search:<input ng-model="search"/></p>
        <ul>
            <li ng-repeat="x in names | filter:search">{{x}}</li>
        </ul>

        <a href="/oauth">Start oauth</a>
    </div>
</body>
</html>
