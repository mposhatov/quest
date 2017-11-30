<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/entry.css">
    <title>Вход</title>
</head>
<body>
<div class="entry-container">
    <div class="entry-login_box">
        <h1>Вход</h1>
        <%--<c:if error="${true}" test="wer">--%>
            <%--<span class="error-msg">Введен неверный логин или пароль</span>--%>
        <%--</c:if>--%>
        <%--<span class="error-msg">Введен неверный логин или пароль</span>--%>
        <form action='${pageContext.request.contextPath}/login' method="POST">
            <div class="form-input">
                <input type="text" name="username" required placeholder="Логин" onfocus="this.placeholder = ''"
                       onblur="this.placeholder = 'Логин'" autocomplete="off">
            </div>
            <div class="form-input">
                <input type="password" name="password" required placeholder="Пароль" onfocus="this.placeholder = ''"
                       onblur="this.placeholder = 'Пароль'" autocomplete="off">
            </div>
            <input type="submit" name="submit" value="Войти">
        </form>
        <span class="login_box_text"><a href="#">Забыли пароль?</a></span><span class="login_box_text"><a
            href="${pageContext.request.contextPath}/registerPage">Зарегистрироваться</a></span>
    </div>
</div>

</body>
</html>

