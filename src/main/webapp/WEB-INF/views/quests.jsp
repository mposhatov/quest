<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/questPage.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/quests.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/categories.css">
    <title>Квесты</title>
</head>
<body>

<div class="grid-container">

    <%--<div class="header test">Header</div>--%>

    <div class="form">
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

    <div id="filter" class="filter">
    </div>

    <div id="content" class="content">
    </div>
    <%--<div class="footer test">Footer</div>--%>
</div>
<script type="text/javascript" src="/js/jquery.js"></script>
<script type="text/javascript" src="/js/handlebars.js"></script>
<script type="text/javascript" src="/js/global.js"></script>
<script type="text/javascript" src="/js/quests.js"></script>
<script type="text/javascript" src="/js/game.js"></script>
</body>
</html>
