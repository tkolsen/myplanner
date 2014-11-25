<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Courses</title>
</head>
<body>
    <c:forEach var="course" items="${courses}">
        <p><c:out value="${course.name}"/></p>
    </c:forEach>
</body>
</html>
