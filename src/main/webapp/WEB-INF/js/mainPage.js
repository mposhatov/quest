window.onload = getTemplates();

function profile() {
    $.ajax({
        url: url.profile,
        method: "GET",
        dataType: "json",
        success: function (client) {
            $("#content").html(templates.profile.body(client));
        }
    });
}

function game () {
    getFilters();
    getQuests([], [], false);
}