window.onload = getTemplates();

var requestPage = 0;
var currentPage = 0;

var currentCategories = [];
var currentDifficulties = [];

var prevCategories = [];
var prevDifficulties = [];

getQuests();

function setCategory(category) {
    if (!currentCategories.includes(category)) {
        currentCategories.push(category);
        $("#" + category).css("border", "3px solid red");
    } else {
        var index = currentDifficulties.indexOf(category);
        currentCategories.splice(index, 1);
        $("#" + category).css("border", "1px solid black");
    }

}

function setDifficulty(difficulty) {
    if (!currentDifficulties.includes(difficulty)) {
        currentDifficulties.push(difficulty);
        $("#" + difficulty).css("border", "3px solid red");
    } else {
        var index = currentDifficulties.indexOf(difficulty);
        currentDifficulties.splice(index, 1);
        $("#" + difficulty).css("border", "1px solid black");
    }
}

function nextPage() {
    requestPage = currentPage + 1;
    getQuests(prevCategories, prevDifficulties);
}

function prevPage() {
    requestPage = currentPage - 1;
    getQuests(prevCategories, prevDifficulties);
}

function search() {
    getQuests(currentCategories, currentDifficulties);
}

function getQuests(categories, difficulties) {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.getQuests;
    params.requestType = "POST";
    params.contentType = "application/json; charset=utf-8";
    params.dataType = 'json';
    params.data =
        JSON.stringify({page: requestPage, categories: categories, difficulties: difficulties});
    params.successCallbackFunc = function (quests) {
        $("#content").html(templates.questTemplate.body({quests: quests}));
        currentCategories.forEach(function (category) {
            $("#" + category).css("border", "1px solid black");
        });
        currentDifficulties.forEach(function (difficulty) {
            $("#" + difficulty).css("border", "1px solid black");
        });

        prevCategories = currentCategories;
        prevDifficulties = currentDifficulties;

        //todo dont work
        currentCategories = [];
        currentDifficulties = [];

        currentPage = requestPage;
        requestPage = 0;
    };
    doAjaxRequest(params);
}
