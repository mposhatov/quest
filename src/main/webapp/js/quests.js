var requestPage = 0;
var currentPage = 0;

var stopFetch = false;

var requestCategories = [];
var requestDifficulties = [];

var prevCategories = [];
var prevDifficulties = [];

var filter_category_modifier = {
    attribute:'border',
    not_visited_category:'2px solid #1980B6',
    visited_category:'2px solid coral'
};

function setCategory(category) {
    if (!requestCategories.includes(category)) {
        requestCategories.push(category);
        $("#" + category).css(filter_category_modifier.attribute, filter_category_modifier.visited_category);
    } else {
        var index = requestDifficulties.indexOf(category);
        requestCategories.splice(index, 1);
        $("#" + category).css(filter_category_modifier.attribute, filter_category_modifier.not_visited_category);
    }
}

function setDifficulty(difficulty) {
    clearDifficulties();
    requestDifficulties = [];
    requestDifficulties.push(difficulty);
    $("#" + difficulty).css(filter_category_modifier.attribute, filter_category_modifier.visited_category);
}

function clearCategories() {
    requestCategories.forEach(function (category) {
        $("#" + category).css(filter_category_modifier.attribute, filter_category_modifier.not_visited_category);
    });
}

function clearDifficulties() {
    requestDifficulties.forEach(function (difficulty) {
        $("#" + difficulty).css(filter_category_modifier.attribute, filter_category_modifier.not_visited_category);
    });
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

function getQuests(categories, difficulties, append) {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.getQuests;
    params.requestType = "POST";
    params.contentType = "application/json; charset=utf-8";
    params.data =
        JSON.stringify({page: requestPage, categories: categories, difficulties: difficulties});
    params.successCallbackFunc = function (quests) {
        if (append == true) {
            quests.forEach(function(quest) {
                $("#quests").append(templates.questTemplate.body(quest));
                setBackground("#quest_" + quest.id + "> #quest_picture", quest.pictureName);
            });
        } else if (append == false) {
            $("#quests").html('');
            quests.forEach(function(quest) {
                $("#quests").append(templates.questTemplate.body(quest));
                setBackground("#quest_" + quest.id + "> #quest_picture", quest.pictureName);
            });
        }

        clearCategories();
        requestCategories = [];

        clearDifficulties();
        requestDifficulties = [];

        currentPage = requestPage;
        requestPage = 0;

        showLoaderButtonIsMoreQuests(categories, difficulties);
    };
    doAjaxRequest(params);
}

function showLoaderButtonIsMoreQuests(categories, difficulties) {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.getQuests;
    params.requestType = "POST";
    params.contentType = "application/json; charset=utf-8";
    params.data =
        JSON.stringify({page: currentPage + 1, categories: categories, difficulties: difficulties});
    params.successCallbackFunc = function (quests) {
        if(quests != undefined && quests.length > 0) {
            $("#loader_button").css("display", "block");
        } else {
            $("#loader_button").css("display", "none");
        }
    };
    doAjaxRequest(params);
}