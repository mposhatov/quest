var url = {
    welcome: contextUrl + "/welcome",

    gameSearchRequest: contextUrl + "/game-search-request",

    activeGame: contextUrl + "/active-game",
    defaultAttack: contextUrl + "/active-game.action/attack/default",
    defaultDefense: contextUrl + "/active-game.action/defense/default",
    surrendered: contextUrl + "/active-game.action/surrendered",

    hero: contextUrl + "/hero",
    updateMainWarriors: contextUrl + "/hero.action/update-main-warriors",

    clients: contextUrl + "/clients",
    clientGameResult: contextUrl + "/client-game-result",

    imagesPath: contextUrl + "/img/",

    setPhoto: contextUrl + "/photo",

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
        load: false
    },
    clientsTemplate: {
        url: contextUrl + "/templates/clients.hbs",
        body: null,
        name: "clientsTemplate",
        load: false
    },
    clientTemplate: {
        url: contextUrl + "/templates/client.hbs",
        body: null,
        name: "clientTemplate",
        load: false
    },
    activeGame: {
        url: contextUrl + "/templates/activeGame.hbs",
        body: null,
        name: "activeGame",
        load: false
    },
    clientGameResult: {
        url: contextUrl + "/templates/clientGameResult.hbs",
        body: null,
        name: "clientGameResult",
        load: false
    },
    warriorPositionPlace: {
        url: contextUrl + "/templates/warriorPositionPlace.hbs",
        body: null,
        name: "warriorPositionPlace",
        load: false
    },
    positionWarrior: {
        url: contextUrl + "/templates/positionWarrior.hbs",
        body: null,
        name: "positionWarrior",
        load: false
    },
    arenaWarrior: {
        url: contextUrl + "/templates/arenaWarrior.hbs",
        body: null,
        name: "arenaWarrior",
        load: false
    }
};

function getTemplates() {
    for (var template in templates) {
        if (!templates[template].load) {
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

function setBackgroundArray(goal, contentType, content) {
    $(goal).css('background-image', 'url(data:' + contentType + ',' + content + ')');
}

function setBackground(goal, pictureName) {
    $(goal).css("background-image", 'url(' + url.imagesPath + pictureName + ')');
}

Handlebars.registerHelper('if_eq', function (a, b, options) {
    if (a == b) // Or === depending on your needs
        return options.fn(this);
    else
        return options.inverse(this);
});

Handlebars.registerHelper('position_main_cards', function (start_card, finish_card, warriors, options) {

    var out = '';

    for (var i = start_card; i <= finish_card; ++i) {

        out += '<div id="card_' + i + '" class="card" onclick="setPositionCurrentWarrior(' + i + ')">';

        warriors.forEach(function (warrior) {
            if (warrior.position != undefined && warrior.position != null && warrior.position == i) {
                out += generateContentPositionMainCards(i, warrior);
            }
        });

        out += '</div>';

    }

    return out;
});

Handlebars.registerHelper('position_no_main_cards', function (warriors, options) {

    var out = '';

    warriors.forEach(function (warrior) {
        if (warrior.main != undefined && warrior.main != null && warrior.main == false) {
            out += generateContentPositionNoMainCards(warrior);
        }
    });

    return out + '</div>';
});

Handlebars.registerHelper('arena_cards', function (start_card, finish_card, warriors, currentWarriorId, options) {

    var out = '';

    var i;

    if (start_card < finish_card) {
        for (i = start_card; i <= finish_card; ++i) {
            out += generateContentArenaCards(i, warriors, currentWarriorId);
        }
    } else if (start_card > finish_card) {
        for (i = start_card; i >= finish_card; --i) {
            out += generateContentArenaCards(i, warriors, currentWarriorId);
        }
    }

    return out;
});

function generateContentArenaCards(cardId, warriors, currentWarriorId) {
    var out = '<div class="card">';

    warriors.forEach(function (warrior) {
        if (warrior.position != undefined && warrior.position != null && warrior.position == cardId) {
            out += generateContentArenaCard(warrior, warrior.id == currentWarriorId);
        }
    });

    return out + '</div>';
}

Handlebars.registerHelper('queue', function (warriors, meClientId, options) {

    var out = '';

    if (warriors.length > 0) {
        if (warriors[0].hero.client.id == meClientId) {
            out += '<div class="now myWarrior">';
        } else {
            out += '<div class="now">';
        }
        out += '<img src = "' + (url.imagesPath + warriors[0].pictureName) + '">';
    }

    out += '</div>';

    warriors.forEach(function (warrior) {
        if(warrior.hero.client.id == meClientId) {
            out += '<div class="next myWarrior">';
        } else {
            out += '<div class="next">';
        }
        out += '<img src = "' + (url.imagesPath + warrior.pictureName) + '">';
        out += '</div>';
    });

    return out;
});