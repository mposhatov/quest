<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <title>Title</title>
</head>
<body>
    <div id="step" + ${step.id} class="step">
        ${step.description}
    </div>
    <c:forEach var="answer" items="${step.answers}">
        <div id="answer${answer.id}" class="answer" onclick="nextStep('${answer.id}')">
            ${answer.description}
        </div>
    </div>
</c:forEach>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/scripts.js"></script>
</body>
</html>
