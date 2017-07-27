window.onload = onload();

function onload() {
    getTemplateWith(templates.profileTemplate.name, function () {
        getClientForProfile();
    });
    getTemplates();
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
            $('#photo_profile').css('background-image', 'url(data:' + photo.contentType + ',' + photo.content + ')');
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

function getClientForProfile() {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.getProfile;
    params.requestType = "GET";
    params.dataType = 'json';
    params.successCallbackFunc = function (client) {
        $("#content").html(templates.profileTemplate.body(client));
        if (client.photo != null && client.photo != undefined) {
            $('#photo_profile').css('background-image', 'url(data:' + client.photo.contentType + ',' + client.photo.content + ')');
        } else {
            $('#photo_profile').css('background-image', 'url(../img/imageOfAnonymousClient.png)');
        }
        file.onchange = function () {
            setPhoto();
        };
    };
    doAjaxRequest(params);
}

function getClientForRate() {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.getProfile;
    params.requestType = "GET";
    params.dataType = 'json';
    params.successCallbackFunc = function (client) {
        $("#client_information").html(templates.clientTemplate.body(client));
        if(client.photo != null && client.photo != undefined) {
            $('#client>#photo_rate').css('background-image', 'url(data:' + client.photo.contentType + ',' + client.photo.content + ')');
        } else {
            $('#client>#photo_rate').css('background-image', 'url(../img/imageOfAnonymousClient.png)');
        }
    };
    doAjaxRequest(params);
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
    $("#content").html("<div id='client_information' class='client_information'></div>" +
        "<hr>" +
        "<div id='clients' class='clients'></div>");

    getClientForRate();

    //todo переписать ajax
    $.ajax({
        url: url.getClients,
        method: "GET",
        dataType: "json",
        success: function (clients) {
            $("#clients").html(templates.clientsTemplate.body({clients:clients}));
            clients.forEach(function(client) {
                if(client.photo != null && client.photo != undefined) {
                    $('#client' + client.id + '>#photo_rate').css('background-image', 'url(data:' + client.photo.contentType + ',' + client.photo.content + ')');
                } else {
                    $('#client' + client.id + '>#photo_rate').css('background-image', 'url(../img/imageOfAnonymousClient.png)');
                }
            });
        }
    });
}