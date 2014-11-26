<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>MyPlanner Profile</title>
    <link href="/resources/style.css" rel="stylesheet"/>
</head>
<body>
    <div id="wrapper">
        <div id="header">
            <h1>MyPlanner</h1>
            <div id="login-info">
                <h2>Innlogget som: ${loginInfo.user.name}</h2>
            </div>
            <br class="clear-float"/>
        </div>

        <c:forEach var="course" items="${courses}">
            <c:forEach var="module" items="${course.modules}">
                <!-- Module -->
                <div class="module">
                    <h3><c:out value="${module.name}"/></h3>
                    <c:forEach var="item" items="${module.items}">
                        <!-- Module Item -->
                        <div class="module-item">
                            >> <c:out value="${item.title}"/>
                            <c:if test="${item.completionRequirement.completed != null}">
                                <!-- Completed -->
                                <br/>
                                <span class="completed-status">
                                    - Ferdig: <c:out value="${item.completionRequirement.completed}"/>
                                </span>
                            </c:if>
                        </div>
                    </c:forEach>
                </div>
                <br/>
            </c:forEach>

        </c:forEach>
    </div>
</body>
</html>
