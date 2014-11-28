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
    <header id="header" class="box" onload="getResources()">
        <h1 id="logo">My<span>Planner</span></h1>

        <h2>Logget inn som:</h2>

        <h3 id="username">{{username}}</h3>

        <p><a href="/oauth/logout">Log ut</a></p>
    </header>

    <!-- Main content -->
    <section id="main-content" class="box">
        <aside id="navigation">
            <h3>Velg kurs</h3>
            <select ng-model="selectedCourse" ng-options="course.name for course in courses">
                <option value="">Velg kurs</option>
            </select>
        </aside>
        <div id="module-wrapper">
            <h3>Moduler i {{selectedCourse.name}}</h3>

            <div ng-repeat="course in courses | filter:selectedCourse.name" ng-if="selectedCourse">
                <div class="module" ng-click="doSomething()" ng-repeat="module in course.modules">
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
                </div>
            </div>
        </div>
        <div class="clear-float"/>
    </section>
    <!-- Main footer -->
    <section id="extra" class="box">
        <p ng-click="test()">Ekstra boks for annen info</p>
    </section>
</div>
</body>
</html>
