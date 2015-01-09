<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <h1>Du er ikke logget inn.</h1>
    <a href="<c:url value="/oauth/userInfo"/>">Logg inn</a>
</body>
</html>
