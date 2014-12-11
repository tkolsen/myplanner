<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <h1>Not authorized!</h1>
    <a href="<c:url value="/oauth/userInfo"/>">Log inn</a>
</body>
</html>
