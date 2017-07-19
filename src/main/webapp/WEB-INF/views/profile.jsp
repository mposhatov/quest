<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <title>Title</title>
</head>
<body>
    <div class="profile ">
        <div class="personal">
            <div class="left backWhile">
            </div>

            <div class="right backWhile">
                <h3>${client.name}</h3>
                <div class="divTable">
                    <div class="divTableBody">
                        <div class="divTableRow">
                            <div class="divTableCell">Уровень:</div>
                            <div class="divTableCell">${client.level}</div>
                        </div>
                        <div class="divTableRow">
                            <div class="divTableCell">Опыт:</div>
                            <div class="divTableCell">${client.experience}</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="quests backWhile">
            <c:forEach var="quest" items="${quests}">
                <div id="quest${quest.id}" class="quest" onclick="startGame('${quest.id}')">
                    <c:forEach var="category" items="${quest.categories}">
                        ${category.title}
                    </c:forEach>
                    <hr>
                        ${quest.difficulty}
                    <hr>
                        ${quest.name}
                    <hr>
                        ${quest.description}
                </div>
            </c:forEach>
        </div>
        <form name='logoutForm' action='logout' method="POST">
            <input name="submit" type="submit" value="submit" />
        </form>
    </div>
    <script type="text/javascript" src="/js/jquery.js"></script>
    <script type="text/javascript" src="/js/handlebars.js"></script>
    <script type="text/javascript" src="/js/global.js"></script>
    <script type="text/javascript" src="/js/css.js"></script>
</body>
</html>
