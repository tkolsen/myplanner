<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html ng-app="myPlanner">
<head>
    <title>Home</title>
    <link rel="stylesheet" href="/resources/myplanner.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.4/angular.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.4/angular-route.js"></script>
    <script src="<c:url value="/resources/app.js"/>"></script>
    <script src="<c:url value="/resources/testController.js"/>"></script>
</head>
<body>
    <div id="wrapper" ng-controller="CoursesCtrl">
        <!-- Main Header TODO: Get this to work-->
        <header id="header" class="box-spacing" onload="getResources()">
            <h1 id="logo">MyPlanner</h1>
            <h2 id="username">{{username}}</h2>
            <p><a href="/oauth/logout">Log ut</a></p>
        </header>

        <!-- Main content -->
        <section id="main-content" class="box-spacing">
            <h3>Main Content</h3>
            <div ng-repeat="course in courses">
                <ul>
                    <li ng-repeat="module in course.modules">
                        {{module.name}}
                        <ul>
                            <li ng-repeat="item in module.items  | limitTo:quantity">
                                {{item.title}}
                                <ul ng-if="item.completion_requirement.completed != null">
                                    <li>
                                        Ferdig: {{item.completion_requirement.completed}}
                                    </li>
                                </ul>
                            </li>
                        </ul>
                        <span class="show-more" ng-click="showMore()">{{text}}</span>
                    </li>
                </ul>
            </div>
            <div id="test-span"></div>
        </section>

        <!-- Main footer -->
        <section id="extra" class="box-spacing">
            <p>Ekstra boks for annen info</p>
        </section>
    </div>
</body>
</html>
