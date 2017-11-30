<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/register.css">
    <title>Регистрация</title>
</head>
<body>
<div class="register-container">
    <div class="register-login_box">
        <h1>Регистрация</h1>
        <form action="${pageContext.request.contextPath}/register">
            <div class="form-input">
                <input type="text" name="login" required placeholder="Логин" onfocus="this.placeholder = ''"
                       onblur="this.placeholder = 'Логин'" autocomplete="off">
            </div>
            <div class="form-input">
                <input type="text" name="email" required placeholder="E-mail" onfocus="this.placeholder = ''"
                       onblur="this.placeholder = 'E-mail'" autocomplete="off">
            </div>
            <div class="form-input">
                <input type="password" name="password" required placeholder="Пароль" onfocus="this.placeholder = ''"
                       onblur="this.placeholder = 'Пароль'" autocomplete="off">
            </div>
            <div class="form-input">
                <input type="password" name="repeatPassword" required placeholder="Повторите пароль"
                       onfocus="this.placeholder = ''" onblur="this.placeholder = 'Повторите пароль'"
                       autocomplete="off">
            </div>
            <input type="submit" name="submit" value="Зарегистрироваться">
        </form>
        <a class="login_box_text" href="${pageContext.request.contextPath}/entryPage">Войти</a>
    </div>
</div>

</body>
</html>
