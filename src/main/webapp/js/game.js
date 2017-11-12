var gameIsShow = false;
var warriorByIds = new Map();

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
        _updateActiveGame(activeGame);
        // _printActiveGame(activeGame);
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
        _updateActiveGame(activeGame);
        // _printActiveGame(activeGame);
    };
    doAjaxRequest(params);
}

function defense() {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.defaultDefense;
    params.requestType = "POST";
    params.successCallbackFunc = function (activeGame) {
        _updateActiveGame(activeGame);
        // _printActiveGame(activeGame);
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

    warrior.currentWarrior = true;

    var context = {
        warrior: warrior,
        currentWarrior: highlight
    };

    out += '<div class="card_content">';
    out += templates.arenaWarrior.body(context);
    out += '</div>';

    return out;
}

function _updateActiveGame(newActiveGame) {
    if(!gameIsShow) {
        newActiveGame.anotherClient.hero.warriors.forEach(function(warriorAnotherClient) {
            warriorByIds.set(warriorAnotherClient.id, warriorAnotherClient);
        });

        newActiveGame.me.hero.warriors.forEach(function(meWarrior) {
            warriorByIds.set(meWarrior.id, meWarrior);
        });

        _printActiveGame(newActiveGame);

        gameIsShow = true;
    } else {
        newActiveGame.anotherClient.hero.warriors.forEach(function(warriorAnotherClient) {
            var warrior = warriorByIds.get(warriorAnotherClient.id);
            if(warrior.warriorCharacteristics.health != warriorAnotherClient.warriorCharacteristics.health) {
                warrior.warriorCharacteristics.health = warriorAnotherClient.warriorCharacteristics.health;
                $("#warrior_" + warrior.id +  "> .health").html(warrior.warriorCharacteristics.health);
            }
        });

        newActiveGame.me.hero.warriors.forEach(function(meWarrior) {
            var warrior = warriorByIds.get(meWarrior.id);
            if(warrior.warriorCharacteristics.health != meWarrior.warriorCharacteristics.health) {
                warrior.warriorCharacteristics.health = meWarrior.warriorCharacteristics.health;
                $("#warrior_" + warrior.id +  "> .health").html(warrior.warriorCharacteristics.health);
            }
        });
    }
}