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
        <div class="button">
            Профиль
        </div>
        <div class="button">
            Игра
        </div>
        <div class="button">
            Рейтинг
        </div>
        <div class="button">
            Настройки
        </div>
        <div class="form">
            <form name='logoutForm' action='logout' method="POST">
                <input name="submit" type="submit" value="Выйти" />
            </form>
        </div>
    </div>
    <div class="content">
    </div>
</div>

</body>
</html>
