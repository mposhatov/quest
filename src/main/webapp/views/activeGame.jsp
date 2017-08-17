<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/game.css">
    <title>Квесты</title>
</head>
<body>
<div class="container">
    <div class="left">
        <div id="step" class="step">
            <span class="step_text">${activeGame.step.description}</span>
        </div>
        <div id="answers" class="answers">
            <c:forEach items="${activeGame.step.answers}" var="answer">
                <div id = "answer_${answer.id}" class="answer"
                     onclick="nextStep(${activeGame.id}, ${answer.id}, ${answer.nextStep}, ${answer.winning})">
                    <span class="answer_text">${answer.description}</span>
                </div>
            </c:forEach>
        </div>

        <div id="exit" class="exit" onclick="closeGame(${activeGame.id}, false)">
            Завершить игру
        </div>
    </div>

    <div class="right">
        <div id="subjects" class="subjects">
            <h4>Полученные предметы:</h4>
            <c:forEach var="subject" items="${activeGame.subjects}">
                <div class="subject">
                        ${subject.name}
                </div>
            </c:forEach>
        </div>

        <div id="events" class="events">
            <h4>Выполненные действия:</h4>
            <c:forEach var="event" items="${activeGame.events}">
                <div class="subject">
                        ${event.name}<br>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

<script>
    var contextUrl = "${pageContext.request.contextPath}";
    document.body.style.backgroundImage = 'url(' + contextUrl + '/img/' + '${activeGame.step.backgroundName}' + ')';
</script>
<script defer type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script defer type="text/javascript" src="${pageContext.request.contextPath}/js/handlebars.js"></script>
<script defer type="text/javascript" src="${pageContext.request.contextPath}/js/global.js"></script>
<script defer type="text/javascript" src="${pageContext.request.contextPath}/js/game.js"></script>
</body>
</html>
