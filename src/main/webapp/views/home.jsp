<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/game.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/warriorPosition.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/clientGameResult.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/shop.css">
    <script>
        var contextUrl = "${pageContext.request.contextPath}";
    </script>
    <title>Мир Квестов</title>
</head>

<body>

    <a id="entry" class="head_button entry" description="Войти"
       href="${pageContext.request.contextPath}/entryPage">
        <i class="fa fa-sign-in fa-2x" aria-hidden="true"></i>
    </a>
    <a id="registration" class="head_button" description="Регистрация"
       href="${pageContext.request.contextPath}/registerPage">
        <i class="fa fa-user-plus fa-2x" aria-hidden="true"></i>
    </a>

    <br><br>
    <button onclick="addGameSearchRequest()">Встать в очередь</button>
    <button onclick="deleteGameSearchRequest()">Выйти из очередь</button>
    <br><br>
    <button onclick="printWarriorPositionPlace()">Расстановка войск</button>
    <button onclick="showShop()">Магазин</button>

    <button onclick="">Выйти</button>

</body>

<script defer type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script defer type="text/javascript" src="${pageContext.request.contextPath}/js/handlebars.js"></script>
<script defer type="text/javascript" src="${pageContext.request.contextPath}/js/global.js"></script>
<script defer type="text/javascript" src="${pageContext.request.contextPath}/js/home.js"></script>
<script defer type="text/javascript" src="${pageContext.request.contextPath}/js/game.js"></script>
<script defer type="text/javascript" src="${pageContext.request.contextPath}/js/warriorPosition.js"></script>
<script defer type="text/javascript" src="${pageContext.request.contextPath}/js/shop.js"></script>
</body>

</html>
