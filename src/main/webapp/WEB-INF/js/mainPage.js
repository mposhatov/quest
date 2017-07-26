window.onload = onload();

function onload() {
    getTemplatesWith(function () {
        getProfile();
    });
}

function setPhoto() {
    var formData = new FormData();
    var file = $('#file')[0].files[0];
    if (checkFileSize(file)) {
        formData.set('photo', file);

        var params = $.extend({}, defaultAjaxParams);

        params.url = url.setPhoto;
        params.data = formData;
        params.contentType = false;
        params.processData = false;

        params.successCallbackFunc = function (photo) {
            showPhoto(photo);
        };

        doAjaxRequest(params);
    }
}


function checkFileSize() {
    if ($('#file')[0].files[0].size > 5000000) {
        $('#file').val('');
        showHelp();
        return 0;
    }
    else {
        return 1;
    }
}

function showHelp() {
    $('#help').css('display', 'flex');
}

function hideHelp() {
    $('#help').css('display', 'none');
}

function getProfile() {
    //todo переписать ajax
    $.ajax({
        url: url.getProfile,
        method: "GET",
        dataType: "json",
        success: function (client) {
            $("#content").html(templates.profile.body(client));
            if (client.photo != null && client.photo != undefined) {
                showPhoto(client.photo);
            }
            file.onchange = function () {
                setPhoto();
            };
        }
    });
}

function showPhoto(photo) {
    $('#photo').css('background-image', 'url(data:' + photo.contentType + ',' + photo.content + ')');
}

function getGames() {
    //todo можно вынести
    $("#content").html("<div id='filter' class='filter'></div>" +
        "<div id='quests' class='quests'> </div>");
    getFilters();
    getQuests([], [], false);
}

function getRate() {
    //todo можно вынести
    $("#content").html("<div id='client' class='client'></div>" +
        "<hr>" +
        "<div id='clients' class='clients'></div>");

    //todo переписать ajax
    $.ajax({
        url: url.getClients,
        method: "GET",
        dataType: "json",
        success: function (clients) {
            $("#clients").html(templates.clients.body({clients:clients}));
        }
    });
}