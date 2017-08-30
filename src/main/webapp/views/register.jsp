<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/register.css">
    <title>Регистрация</title>
</head>
<body>
<div class="container">
    <div class="login_box">
        <h1>Регистрация</h1>
        <form>
            <div class="form-input">
                <input type="text" name="Username" required placeholder="Логин" onfocus="this.placeholder = ''"
                       onblur="this.placeholder = 'Логин'">
            </div>
            <div class="form-input">
                <input type="text" name="E-mail" required placeholder="E-mail" onfocus="this.placeholder = ''"
                       onblur="this.placeholder = 'E-mail'">
            </div>
            <div class="form-input">
                <input type="Password" name="Password" required placeholder="Пароль" onfocus="this.placeholder = ''"
                       onblur="this.placeholder = 'Пароль'">
            </div>
            <div class="form-input">
                <input type="RepeatPassword" name="RepeatPassword" required placeholder="Повторите пароль"
                       onfocus="this.placeholder = ''" onblur="this.placeholder = 'Повторите пароль'">
            </div>
            <input type="submit" name="submit" value="Зарегистрироваться">
        </form>
        <a class="login_box_text" href="${pageContext.request.contextPath}/entry">Войти</a>
    </div>
</div>
</body>
</html>
