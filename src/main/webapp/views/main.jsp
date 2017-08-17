<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/quests_old.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/categories.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mainPage.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/game.css">
    <title>Квесты</title>
</head>
<body>

<div class="grid-container">
    <div class="buttons">
        <div class="button" onclick="getClientForProfile()">
            Профиль
        </div>
        <div class="button" onclick="getGames()">
            Игры
        </div>
        <div class="button" onclick="getRate()">
            Рейтинг
        </div>
        <label for="submit" class="button_form logout">
            <span class="text_logout">Выйти</span>
        </label>
        <div class="form">
            <form name='logoutForm' action='${pageContext.request.contextPath}/logout' method="POST">
                <input id="submit" type="submit" value="Выйти" class="hide"/>
            </form>
        </div>
    </div>
    <div id="content" class="content">
    </div>
</div>

<script>
    var contextUrl = "${pageContext.request.contextPath}";
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/handlebars.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/global.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/mainPage.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/quests.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/game.js"></script>
</body>
</html>
