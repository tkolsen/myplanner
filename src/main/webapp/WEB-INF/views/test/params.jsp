<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
    <h1>Params</h1>
    <ul>
        <c:forEach var="param" items="${params}">
            <li><c:out value="${param}"/></li>
        </c:forEach>
    </ul>
</body>
</html>
