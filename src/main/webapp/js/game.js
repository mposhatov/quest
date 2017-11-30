var gameIsShow = false;
// var warriorByIds = new Map();
var enemyWarriorByPositions = new Map();
var myWarriorByPositions = new Map();

var currentSpellAttackId = null;
var currentSpellHealId = null;

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
        if (activeGame.gameOver) {
            // getClientGameResult(activeGame.closedGameId);
            _printClientGameResult(activeGame.myClientGameResult);
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

function defaultAttack(defendingWarriorId) {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.defaultAttack;
    params.requestType = "POST";
    params.data = {
        defendingWarriorId: defendingWarriorId
    };
    params.successCallbackFunc = function (activeGame) {
        _updateActiveGame(activeGame);
        if (activeGame.gameOver) {
            _printClientGameResult(activeGame.myClientGameResult);
        }
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
    };
    doAjaxRequest(params);
}

function surrendered() {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.surrendered;
    params.requestType = "POST";
    params.successCallbackFunc = function (clientGameResult) {
        _printClientGameResult(clientGameResult);
    };
    doAjaxRequest(params);
}

function setSpellAttack(spellAttackId) {
    currentSpellAttackId = spellAttackId;
}

function setSpellHeal(spellHealId) {
    currentSpellHealId = spellHealId;
}

function spellAttack(spellAttackId, defendingWarriorId) {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.spellAttack;
    params.data = {
        spellAttackId: spellAttackId,
        defendingWarriorId: defendingWarriorId
    };
    params.requestType = "POST";
    params.successCallbackFunc = function (activeGame) {
        _updateActiveGame(activeGame);
        if (activeGame.gameOver) {
            _printClientGameResult(activeGame.myClientGameResult);
        }
        currentSpellAttackId = null;
    };
    doAjaxRequest(params);
}

function spellHeal(spellHealId, goalWarriorId) {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.spellHeal;
    params.data = {
        spellHealId: spellHealId,
        goalWarriorId: goalWarriorId
    };
    params.requestType = "POST";
    params.successCallbackFunc = function (activeGame) {
        _updateActiveGame(activeGame);
        if (activeGame.gameOver) {
            _printClientGameResult(activeGame.myClientGameResult);
        }
        currentSpellAttackId = null;
        currentSpellHealId = null;
    };
    doAjaxRequest(params);
}

function attack(goalWarriorId) {
    if (currentSpellAttackId != null) {
        spellAttack(currentSpellAttackId, goalWarriorId);
    } else if (currentSpellHealId != null) {
        spellHeal(currentSpellHealId, goalWarriorId);
    } else {
        defaultAttack(goalWarriorId);
    }
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

    out += templates.arenaWarrior.body(context);

    return out;
}

function _updateActiveGame(activeGame) {
    if (!gameIsShow) {
        activeGame.anotherClient.hero.warriors.forEach(function (warriorAnotherClient) {
            enemyWarriorByPositions.set(warriorAnotherClient.position, warriorAnotherClient);
        });

        activeGame.me.hero.warriors.forEach(function (meWarrior) {
            myWarriorByPositions.set(meWarrior.position, meWarrior);
        });

        _printActiveGame(activeGame);

        gameIsShow = true;
    } else {

        var warriorByIds = new Map();

        activeGame.anotherClient.hero.warriors.forEach(function (warrior) {
            warriorByIds.set(warrior.id, warrior);
        });

        updateWarriors(enemyWarriorByPositions, warriorByIds, activeGame);

        warriorByIds = new Map();

        activeGame.me.hero.warriors.forEach(function (warrior) {
            warriorByIds.set(warrior.id, warrior);
        });

        updateWarriors(myWarriorByPositions, warriorByIds, activeGame);

        $("#queue").html(generateQueue(activeGame.warriors, activeGame.me.id));
    }
}

function updateWarriors(currentPlayerWarriorByPosition, newPlayerWarriorByIds, activeGame) {

    currentPlayerWarriorByPosition.forEach(function (currentWarrior, position) {
        var newWarrior = newPlayerWarriorByIds.get(currentWarrior.id);
        if (newWarrior == null || newWarrior == undefined) {
            currentPlayerWarriorByPosition.delete(currentWarrior.position);
            $("#warrior_" + currentWarrior.id).remove();
        } else {
            if (currentWarrior.warriorCharacteristics.health != newWarrior.warriorCharacteristics.health) {
                currentWarrior.warriorCharacteristics.health = newWarrior.warriorCharacteristics.health;
                $("#warrior_" + currentWarrior.id + "> .health").html(currentWarrior.warriorCharacteristics.health);
            }
            if (activeGame.currentWarrior.id == currentWarrior.id) {
                highlightWarrior(currentWarrior.id);
            } else {
                extinguishWarrior(currentWarrior.id);
            }
        }
    });
}