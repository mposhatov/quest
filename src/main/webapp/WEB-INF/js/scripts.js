const MESSAGE_LIMIT = 5;

var currentPage = 1;
var numberOfPage = 1;
var currentNumberMessageOfPage = 0;

showButtonAddNews(false);
getNumberOfPage();

function showMessage(data) {
    var html = $("#messageList").html();
    data.forEach(function(item){
        var dateMessage = new Date(item.date);
        html += '<div class="myText">';
        html += '<div>' + dateMessage.getHours() + ":" + dateMessage.getMinutes() + '</div><br>';
        html += '<div>' + "Заголовок: " + item.title + '</div><br>';
        html += '<div>'  + "Сообщение: " + item.resume + '</div>';
        html += '<div id ="comments' + item.id + '"></div>';
        $("#comments" + item.id).hide();
        if (item.comments.length > 0 )
            item.comments.forEach(function (comment){
                addCommentByIdAndTextToComments(item.id, comment);
            });
        html += '<div><textarea id = "textAreaComments' + item.id + '"></textarea><br><button ' +
            'onclick="addComments(' + item.id + ')">Добавить комментарий' + '</button></div>';
        html += '</div><br>';
    });
    $("#messageList").html(html);
}

function addComments(id){
    var textComments = $("#textAreaComments" + id).val();
    addCommentByIdAndTextToComments(id, textComments);
    $("#textAreaComments" + id).val('');
}

function addCommentByIdAndTextToComments(id, text){
    $.ajax({
        method: "POST",
        dataType: "json",
        data: {
            newsId: id,
            textComments: text
        },
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: basicUrl + "/addComment",
        success: function() {
            $("#comments" + id).show();
            var commentsHtml = $("#comments" + id).html();
            commentsHtml += '<div class="myText">' + textComments + '</div>';
            $("#comments" + id).show();
            $("#comments" + id).html(commentsHtml);
        }
    })
}

function showButtonAddNews(currentStateButton) {
    if(currentStateButton)
        $("#addNews").show();
    else
        $("#addNews").hide();
}

function showNumberOfPage(currentPage, numberOfPage){
    var html = '';
    for (var i = 1 ; i <= numberOfPage; ++i ){
        if (i == currentPage)
            html += '<u onclick="setCurrentPage(' + i + ')">' + "[" + i + "]" + " " + '</u>';
        else
            html += '<u onclick="setCurrentPage(' + i + ')">' + i + " " + '</u>';
    }
    html += '<br>';
    $("#number").html(html);
}

function setCurrentPage(numberCurrentOfPage) {
    currentPage = numberCurrentOfPage;
    showButtonAddNews(currentPage === numberOfPage ? true : false);
    $("#messageList").html('');
    $.ajax({
        method: "POST",
        dataType: "json",
        data: {
            numberOfPage: currentPage
        },
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: basicUrl + "/setCurrentPage",
        success: function (data, textStatus, jqXHR) {
            showMessage(data);
            showNumberOfPage(currentPage, numberOfPage);
            currentNumberMessageOfPage = data.length;
        }
    })
}

function getNumberOfPage() {
    $.ajax({
        method: "POST",
        dataType: "json",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: basicUrl + "/getNumberOfPage",
        success: function(data, textStatus, jqXHR) {
            numberOfPage = data;
            currentPage = numberOfPage;
            showNumberOfPage(currentPage, numberOfPage);
            setCurrentPage(currentPage);
        }
    })
}

function showFormAddNews(stateFormAddMessage){
    if(stateFormAddMessage)
        formAddNews.style.display = 'block';
    else
        formAddNews.style.display = '';
}

function addNews() {
    showFormAddNews(false);
    if ($("#title").val() == '' || $("#resume").val() == '') {
        alert("Поля заголовка и сообщения должны быть заполнены обязательно");
    }
    else {
        if(currentNumberMessageOfPage == MESSAGE_LIMIT){
            currentNumberMessageOfPage = 0;
            currentPage++;
            numberOfPage++;
            showNumberOfPage(currentPage, numberOfPage);
            $("#messageList").html('');
        }
        currentNumberMessageOfPage++;
        $.ajax({
            method: "POST",
            dataType: "json",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            data: {
                title: $("#title").val(),
                resume: $("#resume").val(),
                numberOfPage: currentPage
            },
            url: basicUrl + "/addNews",
            success: function (data, textStatus, jqXHR) {
                setCurrentPage(currentPage , numberOfPage);
                //showMessage(data);
            }
        });
    }
    $("#title").val('');
    $("#resume").val('');
}