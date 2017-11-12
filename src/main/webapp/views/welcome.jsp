<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/welcomePage.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/game.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/warriorPosition.css">
    <script>
        var contextUrl = "${pageContext.request.contextPath}";
    </script>
    <title>Мир Квестов</title>
</head>

<body>

<div id="flex_container" class="flex_container">

    <div id="head" class="head">
        <div class="head_content">
            <a id="button_quest" class="head_button" description="Квесты"
               href="${pageContext.request.contextPath}/welcome">
                <i class="fa fa-book fa-2x" aria-hidden="true"></i>
            </a>
            <div id="button_news" class="head_button" description="Новости">
                <i class="fa fa-newspaper-o fa-2x" aria-hidden="true" data-description="Новости"></i>
            </div>
            <a id="entry" class="head_button entry" description="Войти"
               href="${pageContext.request.contextPath}/entry">
                <i class="fa fa-sign-in fa-2x" aria-hidden="true"></i>
            </a>
            <a id="registration" class="head_button" description="Регистрация"
               href="${pageContext.request.contextPath}/register">
                <i class="fa fa-user-plus fa-2x" aria-hidden="true"></i>
            </a>
        </div>
    </div>

    <div id="content" class="content">

        <div id='anchor' onclick='toTop()'><i class="fa fa-hand-pointer-o fa-3x" aria-hidden="true"></i></div>

        <div id="content_description" class="content_description">
            <button onclick="addGameSearchRequest()">Встать в очередь</button>
            <button onclick="deleteGameSearchRequest()">Выйти из очередь</button>
            <br><br>
            <button onclick="printWarriorPositionPlace()">Расстановка войск</button>
            <h1 class="content_description_title">Текстовые квесты</h1>
            <p class="content_description_text">
                Текстовые квесты - интерактивная художественная литература,
                разновидность компьютерных игр, в которых общение с игроком осуществляется посредством текстовой
                информации.
                Текст нужно подшлифовать
            </p>
            <p class="content_description_text">Для того чтобы записывалсь статистика и сохранялись игры -
                зарегестрируйтесь.</p>
        </div>

        <div id="filter" class="filter">

            <span class="filter_text">Категория квеста:</span>

            <div class="categories">
                <c:forEach items="${categories}" var="category">
                    <div id="${category.name}" class="category" onclick="setCategory('${category.name}')">
                        <span class="filter_category_text">${category.title}</span>
                    </div>
                </c:forEach>
            </div>

            <span class="filter_text">Уровень сложности:</span>

            <div class="categories">
                <c:forEach items="${difficulties}" var="difficulty">
                    <div id="${difficulty.name}" class="category"
                         onclick="setDifficulty('${difficulty.name}')">
                        <span class="filter_category_text">${difficulty.title}</span>
                    </div>
                </c:forEach>
            </div>

            <div class="filter_button" onclick="search()">
                <span class="filter_button_text"><i class="fa fa-search" aria-hidden="true"></i> Искать</span>
            </div>
        </div>

        <div id="quests" class="quests">
            <c:forEach items="${quests}" var="quest">
                <div id="quest_${quest.id}" class="quest">
                    <div id="quest_picture_${quest.id}" class="quest_picture">
                        <div class="rate">
                            <i class="fa fa-star"></i>
                            <div class="rate_number">${quest.rate}</div>
                        </div>
                        <span class="quest_description_text">${quest.description}</span>
                    </div>
                    <script>
                        document.getElementById("quest_picture_${quest.id}").style.backgroundImage =
                            'url(' + contextUrl + '/img/' + '${quest.pictureName}' + ')';
                        document.getElementById("quest_picture_${quest.id}").style.backgroundSize = 'cover';
                    </script>
                    <span class="quest_name">${quest.name}</span>
                    <div class="quest_play" onclick="startGame(${quest.id})">Играть</div>
                </div>
            </c:forEach>
        </div>

        <div id="loader" class="loader">
            <div id="loader_button" class="loader_button" onclick="nextPage()">
                <i class="fa fa-refresh" aria-hidden="true"></i>
                <span class="loader_text">Показать еще</span>
            </div>
        </div>

    </div>

    <div id="tail" class="tail">
        <div class="tail_content">
            <div class="tail_addres">
                <span class="tail_addres_text">Контакты:</span>
                <div class="tail_email">
                    <i class="fa fa-envelope" aria-hidden="true"></i>
                    <%--todo вынести--%>
                    <span class="tail_email_text">quest@mail.ru</span>
                </div>
                <div class="tail_phone">
                    <i class="fa fa-phone" aria-hidden="true"></i>
                    <%--todo вынести--%>
                    <span class="tail_phone_text">+375(29)123−12−26</span>
                </div>
            </div>
        </div>
    </div>

</div>

<script defer type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script defer type="text/javascript" src="${pageContext.request.contextPath}/js/handlebars.js"></script>
<script defer type="text/javascript" src="${pageContext.request.contextPath}/js/global.js"></script>
<script defer type="text/javascript" src="${pageContext.request.contextPath}/js/welcomePage.js"></script>
<script defer type="text/javascript" src="${pageContext.request.contextPath}/js/game.js"></script>
<script defer type="text/javascript" src="${pageContext.request.contextPath}/js/warriorPosition.js"></script>
</body>

</html>
