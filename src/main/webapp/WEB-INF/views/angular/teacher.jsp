<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
    <c:forEach var="module" items="${list}">
        <c:out value="${module}"/>
    </c:forEach>
</body>
</html>