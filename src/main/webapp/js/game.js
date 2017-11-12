function addGameSearchRequest() {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.gameSearchRequest;
    params.requestType = "POST";
    params.successCallbackFunc = function (gameSearchRequest) {
        getActiveGame();
    };
    doAjaxRequest(params);
}

function deleteGameSearchRequest() {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.gameSearchRequest;
    params.requestType = "DELETE";
    params.successCallbackFunc = function () {
    };
    doAjaxRequest(params);
}

function getActiveGame() {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.activeGame;
    params.requestType = "GET";
    params.successCallbackFunc = function (activeGame) {
        _printActiveGame(activeGame);
        if (activeGame.gameComplete) {
            getClientGameResult(activeGame.closedGameId);
        } else {
            getActiveGame();
        }
    };
    doAjaxRequest(params);
}

function getClientGameResult(closedGameId) {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.clientGameResult;
    params.requestType = "GET";
    params.data = {
        closedGameId: closedGameId
    };
    params.successCallbackFunc = function (clientGameResult) {
        _printClientGameResult(clientGameResult);
    };
    doAjaxRequest(params);
}

function defaultAttack(warriorId) {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.defaultAttack;
    params.requestType = "POST";
    params.data = {
        defendingWarriorId: warriorId
    };
    params.successCallbackFunc = function (activeGame) {
        _printActiveGame(activeGame);
    };
    doAjaxRequest(params);
}

function defense() {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.defaultDefense;
    params.requestType = "POST";
    params.successCallbackFunc = function (activeGame) {
        _printActiveGame(activeGame);
    };
    doAjaxRequest(params);
}

function surrendered() {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.surrendered;
    params.requestType = "POST";
    params.successCallbackFunc = function (activeGame) {
        _printActiveGame(activeGame);
    };
    doAjaxRequest(params);
}

function _printActiveGame(activeGame) {
    $("body").html(templates.activeGame.body(activeGame));
}

function _printClientGameResult(clientGameResult) {
    $("body").html(templates.clientGameResult.body(clientGameResult));
}

function generateContentArenaCard(warrior, highlight) {
    var out = '';

    warrior.highlight = true;

    var context = {
        warrior: warrior,
        highlight: highlight
    };

    out += '<div class="card_content">';
    out += templates.arenaWarrior.body(context);
    out += '</div>';

    return out;
}