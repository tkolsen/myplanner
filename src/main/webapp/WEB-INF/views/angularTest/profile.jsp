<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html ng-app="myPlanner">
<head>
    <title>Home</title>
    <!--<link rel="stylesheet" href="<c:url value="/resources/myplanner.css"/>"/>-->
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.4/angular.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.4/angular-route.js"></script>
    <script src="<c:url value="/resources/app.js"/>"></script>
    <script src="<c:url value="/resources/testController.js"/>"></script>
</head>
<body>
<div id="wrapper" data-ng-controller="CoursesCtrl">
    <!-- Main Header TODO: Get this to work-->
    <header id="header" class="box">
        <h1 id="logo">My<span>Planner</span></h1>

        <h2>Logget inn som:</h2>

        <h3 id="username">{{username}}</h3>

        <p><a href="<c:url value="/oauth/logout"/>">Log ut</a></p>
    </header>

    <!-- Main content -->
    <section id="main-content" class="box">

        <!-- Navigation side bar -->
        <aside id="navigation">
            <label>
                Velg kurs:<br/>
                <select data-ng-model="selectedCourse" data-ng-options="course.name for course in courses">
                </select>
            </label>
            <br/>
            <a class="show-more" data-ng-click="showMore()">Vis alle elementer</a>
        </aside>

        <!-- Wrapper for modules -->
        <div id="module-wrapper">
            <h3 data-ng-if="selectedCourse">Moduler i {{selectedCourse.name}}</h3>

            <!-- Module. Repeats for each module in selected course -->
            <div data-ng-repeat="course in courses | filter:selectedCourse.name" data-ng-if="selectedCourse">
                <div class="module" data-ng-click="moduleClicked()" data-ng-repeat="module in course.modules">
                    <h4>Modul {{module.position}}: {{module.name}}</h4>
                    <ul>
                        <li data-ng-repeat="item in module.items  | limitTo:quantity">
                            {{item.title}}
                            <ul data-ng-if="item.completion_requirement.completed != null">
                                <li>
                                    Ferdig: {{item.completion_requirement.completed}}
                                </li>
                            </ul>
                        </li>
                    </ul>
                    <!--<span class="show-more" data-ng-click="showMore()">{{text}}</span>-->
                    <p class="top-border" data-ng-if="quantity==5">Viser 5 av {{module.items_count}} elementer</p>
                    <p class="top-border" data-ng-if="quantity>5">Viser alle elementer</p>
                </div>
            </div>
        </div>
        <div class="clear-float"></div>
    </section>

    <!-- Main footer -->
    <section id="extra" class="box">
        <p>Ekstra boks for annen info</p>
    </section>
</div>
</body>
</html>
