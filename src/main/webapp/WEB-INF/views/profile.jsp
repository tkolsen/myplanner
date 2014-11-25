<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>MyPlanner Profile</title>
</head>
<body>
    <div id="login-info">
        <p>Innlogget som: ${loginInfo.user.name}</p>
    </div>

    <div id="course-list">
        <ul>
            <c:forEach var="course" items="${courses}">
                <li><c:out value="${course.name}"/></li>
                <ul>
                    <li>Her kommer moduler. (Ikke implementert)</li>
                </ul>
            </c:forEach>
        </ul>
    </div>
</body>
</html>
