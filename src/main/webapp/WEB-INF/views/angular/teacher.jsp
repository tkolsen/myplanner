<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
    <c:forEach var="module" items="${userHasModuleList}">
        <c:out value="${module}"/>
    </c:forEach>
    <c:out value="${oldestDate}"/>
</body>
</html>