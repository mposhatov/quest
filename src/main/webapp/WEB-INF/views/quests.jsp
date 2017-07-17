<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/quests.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/categories.css">
    <title>Квесты</title>
</head>
<body>

<div class="container">

    <div class="header test">Header</div>

    <div class="form test">
        <form name='loginForm' action='login' method="POST">
            <div class="element">
                <input type='text' name='username' value=''>
            </div>
            <div class="element">
                <input type='password' name='password'/>
            </div>
            <div class="element">
                <input type="submit" value="Войти"/>
            </div>
        </form>
    </div>

    <div class="filter">
        <div class="categories">
            <c:forEach var="category" items="${categories}">
                <div id="${category.name}" class="category ${category.name}"
                     onclick="setCategory('${category.name}')">
                        ${category.title}
                </div>
            </c:forEach>
        </div>
        <div class="categories">
            <c:forEach var="difficulty" items="${difficulties}">
                <div id="${difficulty.name}" class="category ${difficulty.name}"
                     onclick="setDifficulty('${difficulty.name}')">
                        ${difficulty.title}
                </div>
            </c:forEach>
        </div>
        <div class="button" onclick="search()">
            Поиск
        </div>
    </div>

    <div id="content" class="content">
        <c:forEach var="quest" items="${quests}">
            <div id="quest${quest.id}" class="quest" onclick="startGame('${quest.id}')">
                <div class="categories">
                    <c:forEach var="category" items="${quest.categories}">
                        <div class="category ${category.name}">
                                ${category.title}
                        </div>
                    </c:forEach>
                </div>
                <hr>
                <div class="categories">
                    <div class="category ${quest.difficulty.name}">
                            ${quest.difficulty.title}
                    </div>
                </div>
                <hr>
                    ${quest.name}
                <hr>
                    ${quest.description}
            </div>
        </c:forEach>
    </div>

    <div class="next button" onclick="nextPage()">
        Следующая страница
    </div>
    <div class="prev button" onclick="prevPage()">
        Предыдущая страница
    </div>

    <div class="footer test">
        Footer
    </div>
</div>
<script type="text/javascript" src="/js/jquery.js"></script>
<script type="text/javascript" src="/js/handlebars.js"></script>
<script type="text/javascript" src="/js/global.js"></script>
<script type="text/javascript" src="/js/quests.js"></script>
</body>
</html>
