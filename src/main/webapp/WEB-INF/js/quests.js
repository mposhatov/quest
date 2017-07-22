var requestPage = 0;
var currentPage = 0;

var stopFetch = false;

var requestCategories = [];
var requestDifficulties = [];

var prevCategories = [];
var prevDifficulties = [];

function setCategory(category) {
    if (!requestCategories.includes(category)) {
        requestCategories.push(category);
        $("#" + category).css("border", "3px solid red");
    } else {
        var index = requestDifficulties.indexOf(category);
        requestCategories.splice(index, 1);
        $("#" + category).css("border", "1px solid black");
    }
}

function setDifficulty(difficulty) {
    if (!requestDifficulties.includes(difficulty)) {
        requestDifficulties.push(difficulty);
        $("#" + difficulty).css("border", "3px solid red");
    } else {
        var index = requestDifficulties.indexOf(difficulty);
        requestDifficulties.splice(index, 1);
        $("#" + difficulty).css("border", "1px solid black");
    }
}

function nextPage() {
    requestPage = currentPage + 1;
    getQuests(prevCategories, prevDifficulties, true);
}

function search() {
    prevCategories = requestCategories;
    prevDifficulties = requestDifficulties;
    getQuests(requestCategories, requestDifficulties, false);
}

function getFilters() {
    $.ajax({
        url: url.getFilters,
        method: "GET",
        dataType: "json",
        success: function (filter) {
            $("#filter").html(templates.filters.body(filter));
        }
    });
}

function getQuests(categories, difficulties, append) {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.getQuests;
    params.requestType = "POST";
    params.contentType = "application/json; charset=utf-8";
    params.dataType = 'json';
    params.data =
        JSON.stringify({page: requestPage, categories: categories, difficulties: difficulties});
    params.successCallbackFunc = function (quests) {
        if (append == true) {
            $("#quests").append(templates.questTemplate.body({quests: quests}));
            if (quests !== 'undefined' && quests.length > 0) {
            } else {
                stopFetch = true;
            }
        } else if (append == false) {
            $("#quests").html(templates.questTemplate.body({quests: quests}));
            $("#quests").scrollTop(0);
            stopFetch = false;
        }

        requestCategories.forEach(function (category) {
            $("#" + category).css("border", "1px solid black");
        });
        requestDifficulties.forEach(function (difficulty) {
            $("#" + difficulty).css("border", "1px solid black");
        });

        requestCategories = [];
        requestDifficulties = [];

        currentPage = requestPage;
        requestPage = 0;
    };
    doAjaxRequest(params);
}

setInterval(function () {
    if ($("#quests")[0] != undefined &&!stopFetch
        && $("#quests").scrollTop() + 3 / 2 * $("#quests").innerHeight() >= $("#quests")[0].scrollHeight) {
        nextPage();
    }
}, 300);