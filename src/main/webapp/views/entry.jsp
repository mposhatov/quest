<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/entry.css">
    <title>Вход</title>
</head>
<body>

<div class="container">
    <div class="login_box">
        <h1>Вход</h1>
        <form action='${pageContext.request.contextPath}/login' method="POST">
            <div class="form-input">
                <input type="text" name="username" required placeholder="Логин" onfocus="this.placeholder = ''"
                       onblur="this.placeholder = 'Логин'">
            </div>
            <div class="form-input">
                <input type="password" name="password" required placeholder="Пароль" onfocus="this.placeholder = ''"
                       onblur="this.placeholder = 'Пароль'">
            </div>
            <input type="submit" name="submit" value="Войти">
        </form>
        <span class="login_box_text"><a href="#">Забыли пароль?</a></span><span class="login_box_text"><a
            href="${pageContext.request.contextPath}/register">Зарегистрироваться</a></span>
    </div>
</div>

</body>
</html>
