<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Вход</title>
</head>
<body>

    <div class="form">
        <form name='loginForm' action='${pageContext.request.contextPath}/login' method="POST">
            <input type='text' name='username' placeholder="Логин">
            <input type='password' name='password' placeholder="Пароль"/>
            <label for="submit" class="button_form login">
                <span class="text_login">Войти</span>
            </label>
            <input id="submit" type="submit" value="Войти" class="hide element_form"/>
        </form>
    </div>

</body>
</html>
