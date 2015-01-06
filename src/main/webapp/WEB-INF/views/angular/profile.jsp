<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html ng-app="myPlanner">
<head>
    <title>Home</title>
    <link rel="stylesheet" href="<c:url value="/resources/style.css"/>"/>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.4/angular.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.4/angular-route.js"></script>
    <script src="<c:url value="/resources/app.js"/>"></script>
    <script src="<c:url value="/resources/testController.js"/>"></script>
</head>
<body>
<div id="wrapper" data-ng-controller="CoursesCtrl">
    <header id="header" class="box">
        <h1 id="logo"><span>My</span>Planner</h1>
        <h2>Logget inn som: {{username}}</h2>
        <p><a href="<c:url value="/oauth/logout"/>">Log ut</a></p>
        <a href="<c:url value="/generateSchedule"/>">Generer Timeplan for valgt fag</a>
    </header>

    <!-- Main content -->
    <section id="main-content" class="box">

        <!-- Navigation side bar -->
        <aside id="navigation">
            <label>
                Velg kurs:
                <select data-ng-model="selectedCourse" data-ng-options="course.name for course in courses"></select>
            </label>
            <a href="#">Skjul ferdige moduler</a>
        </aside>

        <!-- Wrapper for modules -->
        <div id="module-wrapper">
            <h3 data-ng-if="selectedCourse">Moduler i {{selectedCourse.name}}:</h3>

            <!-- Module. Repeats for each module in selected course -->
            <div data-ng-repeat="course in courses | filter:selectedCourse.name" data-ng-if="selectedCourse">
                <div class="module" data-ng-click="moduleClicked()" data-ng-repeat="module in course.modules">
                    <div class="padding">
                        <h4 class="module-name">{{module.name}}</h4>
                        <span class="time">(15 timer)</span>

                        <div class="clear-float"></div>
                        <span>
                            <label>
                                Start: <input type="date"/>
                            </label>
                            <label>
                                Slutt: <input type="date"/>
                            </label>
                            <span>Tid til frist: 5dager</span>
                        </span>
                    </div>
                    <span class="progressBackground">
                        <span class="progressbar" ng-style="{'width' : module.width}"
                              ng-init="test(course, $index)"></span>
                    </span>
                </div>
            </div>
        </div>

    </section>

    <!-- Main footer -->
    <section id="extra" class="box">
        <h3>Made by Tom K. Olsen, Dagfinn Reitan and Kim André Kjelsberg <br/> For Høgskolen i Sør-Trøndelag</h3>
    </section>
</div>
</body>
</html>
