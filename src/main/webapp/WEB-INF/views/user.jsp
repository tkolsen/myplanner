<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
    <h1>User</h1>
    <p>AccessToken: <c:out value="${accessToken}"/> </p>
    <p>User id: <c:out value="${user.id}"/></p>
    <p>User email: <c:out value="${user.email}"/></p>
    <p>User firstName: <c:out value="${user.firstName}"/></p>
    <p>User lastName: <c:out value="${user.lastName}"/></p>
</body>
</html>
