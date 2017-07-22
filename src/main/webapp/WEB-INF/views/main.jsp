<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/quests.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/categories.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mainPage.css">
    <title>Квесты</title>
</head>
<body>

<div class="grid-container">
    <div class="buttons">
        <div class="button" onclick="getProfile()">
            Профиль
        </div>
        <div class="button" onclick="getGames()">
            Игры
        </div>
        <div class="button">
            Рейтинг
        </div>
        <div class="button">
            Настройки
        </div>
        <div class="form">
            <form name='logoutForm' action='logout' method="POST">
                <input name="submit" type="submit" value="Выйти"/>
            </form>
        </div>
    </div>
    <div id="content" class="content">
    </div>
</div>


<script type="text/javascript" src="/js/jquery.js"></script>
<script type="text/javascript" src="/js/handlebars.js"></script>
<script type="text/javascript" src="/js/global.js"></script>
<script type="text/javascript" src="/js/mainPage.js"></script>
<script type="text/javascript" src="/js/quests.js"></script>
<script type="text/javascript" src="/js/game.js"></script>
</body>
</html>
