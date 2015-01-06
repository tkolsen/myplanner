<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Start</title>
</head>
<body>
    <h1>MyPlanner</h1>
    <h2>For å bruke MyPlanner må du logge inn med Canvas under</h2>
    <a href="<c:url value="/oauth/userInfo"/>">Log inn via Canvas</a>
</body>
</html>
