//todo привевти именование в порядок
var url = {
    createGame: "/createGame",
    updateGame: "/updateGame",
    closeGame: "/closeGame",
    getQuests: "/quests",
    profile: "/profile",
    filters: "/filters"
};

var defaultAjaxParams = {
    url: null,
    dataType: "json",
    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
    requestType: "POST",
    data: {},
    errorCallbackFunc: null,
    successCallbackFunc: null,
    cache: false,
    processData: true,
    context: false,
    async: false
};

function doAjaxRequest(ajaxParams) {
    $.ajax({
        url: ajaxParams.url,
        dataType: ajaxParams.dataType,
        contentType: ajaxParams.contentType,
        type: ajaxParams.requestType,
        data: ajaxParams.data,
        cache: ajaxParams.cache,
        processData: ajaxParams.processData,
        context: ajaxParams.context,
        success: function (data, textStatus, jqXHR) {
            // if (typeof ajaxParams.successCallbackFunc === "function") {
            ajaxParams.successCallbackFunc(data, textStatus, jqXHR);
            // }
        }
    });
}

var templates = {
    answersTemplate: {
        url: '/templates/answers.hbs',
        body: null
    },
    subjectsTemplate: {
        url: '/templates/subjects.hbs',
        body: null
    },
    eventsTemplate: {
        url: '/templates/events.hbs',
        body: null
    },
    exitTemplate: {
        url: '/templates/exit.hbs',
        body: null
    },
    questTemplate: {
        url: "/templates/quests.hbs",
        body: null
    },
    profile: {
        url: "/templates/profile.hbs",
        body: null
    },
    filters: {
        url: "/templates/filters.hbs",
        body: null
    }
};

function getTemplates() {
    for (var template in templates) {
        var params = $.extend({}, defaultAjaxParams);
        params.url = templates[template].url;
        params.requestType = "GET";
        params.dataType = 'text';
        params.context = template;
        params.successCallbackFunc = function (data) {
            templates[this.context].body = Handlebars.compile(data);
        };
        doAjaxRequest(params);
    }
}