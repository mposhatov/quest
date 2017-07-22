window.onload = onload();

function onload() {
    getTemplates();
    getProfile();
}

function getProfile() {
    $.ajax({
        url: url.getProfile,
        method: "GET",
        dataType: "json",
        success: function (client) {
            $("#content").html(templates.profile.body(client));
        }
    });
}

function getGames() {
    //todo можно вынести
    $("#content").html("<div id='filter' class='filter'></div>" +
        "<div id='quests' class='quests'> </div>");
    getFilters();
    getQuests([], [], false);
}