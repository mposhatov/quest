window.onload = setGlobalVariable();

var url = {
    welcome: contextUrl + "/welcome",
    imagesPath: contextUrl + "/img/",
    createGame: contextUrl + "/scene/createGame",
    updateGame: contextUrl + "/scene/updateGame",
    closeGame: contextUrl + "/scene/closeGame",
    getActiveGame: contextUrl + "/scene/activeGame",
    getQuests: contextUrl + "/quests",
    getProfile: contextUrl + "/profile",
    getFilters: contextUrl + "/filters",
    setPhoto: contextUrl + "/photo",
    getClients: contextUrl + "/clients",
    keepAlive: contextUrl + "/keepAlive"
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
    profileTemplate: {
        url: contextUrl + "/templates/profile.hbs",
        body: null,
        name: "profileTemplate",
        load:false
    },
    clientsTemplate: {
        url: contextUrl + "/templates/clients.hbs",
        body: null,
        name: "clientsTemplate",
        load:false
    },
    clientTemplate: {
        url: contextUrl + "/templates/client.hbs",
        body: null,
        name: "clientTemplate",
        load:false
    }
};

function setGlobalVariable() {
    Handlebars.registerHelper('contextUrl', function() {
        return contextUrl;
    });
}

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

function keepAlive() {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.keepAlive;
    doAjaxRequest(params);
}

setInterval(keepAlive, 10000);

function setBackgroundArray(goal, contentType, content) {
    $(goal).css('background-image', 'url(data:' + contentType + ',' + content + ')');
}

function setBackground(goal, pictureName) {
    $(goal).css("background-image", 'url(' + url.imagesPath + pictureName + ')');
}