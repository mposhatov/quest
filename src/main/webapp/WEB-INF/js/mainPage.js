window.onload = onload();

function onload() {
    getTemplates();
    getProfile();
}

// file.onchange = function() {
//     loadPhoto(file.files[0]);
// };

function loadPhoto() {
    var formData = new FormData();
    formData.append("photo", file.files[0]);
    $.ajax({
        url: url.getPhoto,
        method: "GET",
        contentType: "multipart/form-data",
        dataType: "json",
        processData: false,
        data: file.files[0],
        success: function (photo) {
            setPhoto(photo);
        }
    });
}

function getProfile() {
    $.ajax({
        url: url.getProfile,
        method: "GET",
        dataType: "json",
        success: function (client) {
            $("#content").html(templates.profile.body(client));
            setPhoto(client.photo);
        }
    });
}

function setPhoto(photo) {
    $('#photo').css('background-image', 'url(data:' + photo.contentType + ',' + photo.content + ')');
}

function getGames() {
    //todo можно вынести
    $("#content").html("<div id='filter' class='filter'></div>" +
        "<div id='quests' class='quests'> </div>");
    getFilters();
    getQuests([], [], false);
}