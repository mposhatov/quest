window.onload = onload();

function onload() {
    //todo getTemplatesWithout
    getTemplates();
    getFiltersTemplate();
    getQuestTemplate();
}

function getFiltersTemplate() {
    var params = $.extend({}, defaultAjaxParams);
    params.url = templates.filters.url;
    params.requestType = "GET";
    params.dataType = 'text';
    params.successCallbackFunc = function (data) {
        templates.filters.body = Handlebars.compile(data);
        getFilters();
    };
    doAjaxRequest(params);
}

function getQuestTemplate() {
    var params = $.extend({}, defaultAjaxParams);
    params.url = templates.questTemplate.url;
    params.requestType = "GET";
    params.dataType = 'text';
    params.successCallbackFunc = function (data) {
        templates.questTemplate.body = Handlebars.compile(data);
        getQuests([], [], false);
    };
    doAjaxRequest(params);
}