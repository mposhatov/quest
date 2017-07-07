<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <title>Title</title>
</head>
<body>
    <div class="container">
        <div class="left">
            <div id="step" + ${step.id} class="step">
                ${step.description}
            </div>
            <c:forEach var="answer" items="${step.answers}">
                <div id="answer${answer.id}" class="answer" onclick="nextStep('${answer.id}', '${answer.nextStep}')">
                        ${answer.description}
                </div>
            </c:forEach>

            <div id="closeGame" class="closeGame" onclick="closeGame('false')">
                Завершить игру
            </div>
        </div>

        <div class="right">
            <div class="inventory">
                <h4>Полученные предметы:</h4>
                <c:forEach var="subject" items="${activeGame.subjects}">
                    <div class="subject">
                        ${subject.name}
                    </div>
                </c:forEach>
            </div>

            <div class="inventory">
                <h4>Выполненные действия:</h4>
                <c:forEach var="event" items="${activeGame.events}">
                    <div class="subject">
                        ${event.name}<br>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/scripts.js"></script>
</body>
</html>
