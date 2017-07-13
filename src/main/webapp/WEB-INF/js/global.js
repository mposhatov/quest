var url = {
    createGame: "/createGame",
    updateGame: "/updateGame",
    closeGame: "/closeGame",
    getQuests: "/quests"
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
        error: function (jqXHR, textStatus, errorThrown) {
            if (typeof ajaxParams.errorCallbackFunc === "function") {
                ajaxParams.errorCallbackFunc(errorThrown);
            }
        },
        success: function (data, textStatus, jqXHR) {
            // if (typeof ajaxParams.successCallbackFunc === "function") {
            ajaxParams.successCallbackFunc(data, textStatus, jqXHR);
            // }
        },
        complete: function () {
            if (typeof ajaxParams.completeCallbackFunc === "function") {
                ajaxParams.completeCallbackFunc();
            }
        },
        beforeSend: function (jqXHR, settings) {
            if (typeof ajaxParams.beforeSend === "function") {
                ajaxParams.beforeSend(jqXHR, settings);
            }
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