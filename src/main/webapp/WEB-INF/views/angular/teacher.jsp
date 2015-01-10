<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
    <c:forEach var="module" items="${list}">
        <p>
            Navn: <c:out value="${module.user.name}"/>, Modul: <c:out value="${module.module.name}"/>, Startdato: <c:out value="${module.startDate}"/>, Sluttdato: <c:out value="${module.endDate}"/>
        </p>
    </c:forEach>
</body>
</html>