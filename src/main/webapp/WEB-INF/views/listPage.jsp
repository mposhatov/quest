<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script>
      var basicUrl = "${pageContext.request.contextPath}";
    </script>
    <title>Chat Room</title>
</head>
<body>

  <div id="workSpace">
    <div id="number"></div>
    <div class="chat scroll" id="messageList"></div>
    <button id="addNews" onclick="showFormAddNews(true)">Добавить Новость</button>

    <div id="formAddNews" class="formAddNews">
      <div class="closeForm">

        <div class="formTop">
          <div class="formHeader">Создание Новости</div>
        </div>

        <div class="formCenter">
          <textarea data-limit-rows="true" maxlength="120" id="title" name="title" type="text" placeholder="Тема" rows="2"></textarea>
          <textarea data-limit-rows="true" maxlength="256" id="resume" name="resume" type="text" placeholder="Резюме" rows="5"></textarea>
          <input id="closeButton" name="submit" type="button" value="Готово" onclick="addNews()">
          <input id="closeBack" type="reset" value="Отмена" onclick="showFormAddNews( false );">
        </div>
      </div>
    </div>
  </div>

  <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/scripts.js"></script>
</body>
</html>
