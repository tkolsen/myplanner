<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
    <h1>Du er nå logget ut</h1>
    <a href="<c:url value="/oauth/userInfo"/>">Log inn</a>
</body>
</html>
