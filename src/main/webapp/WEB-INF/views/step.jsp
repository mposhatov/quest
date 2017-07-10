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

            <div id="step" + ${activeGame.step.id} class="step">
                ${activeGame.step.description}<br>
            </div>

            <div class="answers" id="answers">
                <c:forEach var="answer" items="${activeGame.step.answers}">
                    <div id="answer${answer.id}" class="answer" onclick="nextStep(
                    ${activeGame.id}, ${answer.id},${answer.nextStep},${answer.winning})">
                            ${answer.description}
                    </div>
                </c:forEach>
            </div>

            <div id="closeGame" class="closeGame" onclick="closeGame(${activeGame.id}, false)">
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
                        ${event.name}
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/global.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/game.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/handlebars.js"></script>
    <script>
        setBackground('${activeGame.step.background.contentType}', '${activeGame.step.background.content}');
    </script>
</body>
</html>
