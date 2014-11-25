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
                    <c:forEach var="module" items="${course.modules}">
                        <li><c:out value=" ${module.name}"/></li>
                        <ul>
                            <c:forEach var="item" items="${module.items}">
                                <li><c:out value="${item.title}"/></li>
                            </c:forEach>
                        </ul>
                    </c:forEach>
                </ul>
            </c:forEach>
        </ul>
    </div>
</body>
</html>
