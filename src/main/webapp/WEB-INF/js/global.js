var url = {
    createGame: "/createGame",
    updateGame: "/updateGame",
    closeGame: "/closeGame",
    getActiveGame: "/activeGame",
    getQuests: "/quests",
    getProfile: "/profile",
    getFilters: "/filters",
    setPhoto: "/photo",
    getClients: "/clients"
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
        body: null,
        name: "answersTemplate",
        load:false
    },
    subjectsTemplate: {
        url: '/templates/subjects.hbs',
        body: null,
        name: "subjectsTemplate",
        load:false
    },
    eventsTemplate: {
        url: '/templates/events.hbs',
        body: null,
        name: "eventsTemplate",
        load:false
    },
    exitTemplate: {
        url: '/templates/exit.hbs',
        body: null,
        name: "exitTemplate",
        load:false
    },
    questTemplate: {
        url: "/templates/quests.hbs",
        body: null,
        name: "questTemplate",
        load:false
    },
    profileTemplate: {
        url: "/templates/profile.hbs",
        body: null,
        name: "profileTemplate",
        load:false
    },
    filtersTemplate: {
        url: "/templates/filters.hbs",
        body: null,
        name: "filtersTemplate",
        load:false
    },
    clientsTemplate: {
        url: "/templates/clients.hbs",
        body: null,
        name: "clientsTemplate",
        load:false
    },
    clientTemplate: {
        url: "/templates/client.hbs",
        body: null,
        name: "clientTemplate",
        load:false
    }
};

function getTemplates() {
    for (var template in templates) {
        if(!templates[template].load) {
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
}

function getTemplateWith(template, func) {
    var params = $.extend({}, defaultAjaxParams);
    params.url = templates[template].url;
    params.requestType = "GET";
    params.dataType = 'text';
    params.successCallbackFunc = function (data) {
        templates[template].body = Handlebars.compile(data);
        templates[template].load = true;
        func();
    };
    doAjaxRequest(params);
}