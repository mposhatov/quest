<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    ${client.name}<br>
    <c:forEach var="quest" items="${quests}">
        <li>id:${quest.id}</li>
        <li>name:${quest.name}</li>
        <li>description:${quest.description}</li>
    </c:forEach>
    <form name='logoutForm' action='logout' method="POST">
        <input name="submit" type="submit" value="submit" />
    </form>
</body>
</html>
