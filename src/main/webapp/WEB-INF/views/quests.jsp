<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/questPage.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/quests.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/categories.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/game.css">
    <title>Квесты</title>
</head>
<body>

<div class="grid-container">

    <div class="form">
        <form name='loginForm' action='login' method="POST">
            <input type='text' name='username' placeholder="Логин" class="element_form">
            <input type='password' name='password' placeholder="Пароль" class="element_form"/>
            <label for="submit" class="button_form login">
                <span class="text_login">Войти</span>
            </label>
            <input id="submit" type="submit" value="Войти" class="hide element_form"/>
        </form>
    </div>

    <div id="content" class="content">
        <div id="filter" class="filter">
        </div>
        <div id="quests" class="quests">
        </div>
    </div>

</div>

<script defer type="text/javascript" src="/js/jquery.js"></script>
<script defer type="text/javascript" src="/js/handlebars.js"></script>
<script defer type="text/javascript" src="/js/global.js"></script>
<script defer type="text/javascript" src="/js/quests.js"></script>
<script defer type="text/javascript" src="/js/questPage.js"></script>
<script defer type="text/javascript" src="/js/game.js"></script>
</body>
</html>
