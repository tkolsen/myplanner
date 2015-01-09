<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    </header>

    <!-- Main content -->
    <section id="main-content" class="box">

        <!-- Navigation side bar -->
        <aside id="navigation">
            <label>
                Velg kurs:
                <select data-ng-model="selectedCourse" data-ng-options="course.name for course in courses"></select>
            </label> | <a href="<c:url value="/user/profile/refresh"/>">Hent data på nytt</a>
            <br/>
        </aside>

        <div id="schedule-generator">
            <a ng-click="showGenerator()">
                <span ng-hide="!show">&nabla;</span>
                <span ng-hide="show">&Delta;</span>
                Handlingsmeny
                <span ng-hide="!show">&nabla;</span>
                <span ng-hide="show">&Delta;</span>
            </a>
            <form data-ng-submit="generateSchedule(scheduleDetails)" ng-hide="show" action="<c:url value="/user/profile/refresh"/>">
                <h4>Generer Fremdriftsplan:</h4>
                <label for="hours-pr-day-input">
                    Timer du jobber per dag:
                </label>
                <input name="hours-pr-day" id="hours-pr-day-input" type="number"
                       ng-model="scheduleDetails.workHoursDaily" min="0" max="24" <%-- TODO: Should be possible to set this to a decimal --%>
                       title="Omtrentlig antall arbeidstimer du regner med å i gjennomsnitt ville jobbe med faget hver dag."/>
                <br class="clear-float"/>

                <label for="start-date-input">
                    Datoen du skal starte:
                </label>
                <input title="Datoen du vil begynne å jobbe med faget." name="start-date" id="start-date-input" type="date" ng-model="scheduleDetails.startDate"/>
                <br class="clear-float"/>

                <input id="schedule-submit-button" type="submit" value="Generer Timeplan"/>
                <br class="clear-float"/>
            </form>
            <form data-ng-submit="checkDeadlines(onlyOldestDates)" ng-hide="show"> <%-- add action on submit, load page with users--%>
                <h4>Hent elever bak sin fremdriftsplan:</h4>
                <label for="oldest-dates">
                    Hent kun eldste uoppnådde frist:
                </label>
                <input type="checkbox" data-ng-model="onlyOldestDates" id="oldest-dates" checked>
                <br class="clear-float"/>

                <input type="submit" value="Sjekk Deadlines">
                <br class="clear-float"/>
            </form>
        </div>

        <!-- Wrapper for modules -->
        <div id="module-wrapper">
            <h3 data-ng-if="selectedCourse">Moduler i {{selectedCourse.name}}:</h3>
            <h1 id="loading-text" ng-show="!courses">
                Venligst vent. Henter data fra din Canvas konto.<br/>
                <img id="loading-gif" src="../resources/ajax-loader-alt.gif"/>
            </h1>
            <!-- Module. Repeats for each module in selected course -->
            <div data-ng-repeat="course in courses | filter:selectedCourse.name" data-ng-if="selectedCourse">
                <div class="module" data-ng-click="moduleClicked()" data-ng-repeat="module in course.modules">
                    <div class="padding">
                        <h4 class="module-name">{{module.name}}</h4>
                        <span class="time">Estimert arbeidsmengde i timer: {{module.moduleTimeEstimation}}</span>

                        <div class="clear-float"></div>
                        <form data-ng-submit="submit(module)">
                            <label>
                                Start: <input title="Anbefalt dato for når du bør begynne på modulen." ng-model="module.newStartDate" type="date"/>
                            </label>
                            <label>
                                Slutt: <input title="Anbefalt dato for når du bør være ferdig med modulen." ng-model="module.newEndDate" type="date"/>
                            </label>
                            <label>
                                <input type="submit" value="Lagre"/>
                            </label>
                            <span>
                                <span ng-show="module.newEndDate">{{testDateCalc(module.newEndDate)}}</span>
                                <span ng-show="!module.newEndDate">Dato ikke satt</span>
                            </span>
                        </form>
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
    {{testVar}}
</div>
</body>
</html>
<!-- test -->