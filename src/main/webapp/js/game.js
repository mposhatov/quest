var gameIsShow = false;
var enemyWarriorByCardId = new Map();
var myWarriorByCardId = new Map();

var currentSpellAttackId = null;
var currentSpellHealId = null;
var currentSpellExhortationId = null;
var currentSpellPassiveId = null;

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

function cardOnClick(cardId, isMyPosition) {
    if (isMyPosition) {
        var myWarrior = myWarriorByCardId.get(cardId);
        if (myWarrior != null) {
            if (currentSpellHealId != null) {
                spellHeal(currentSpellHealId, myWarrior.id);
            } else if (currentSpellPassiveId != null) {
                spellPassive(currentSpellPassiveId, myWarrior.id);
            }
        } else {
            if (currentSpellExhortationId != null) {
                spellExhortation(currentSpellExhortationId, cardId, isMyPosition);
            }
        }
    } else {
        var enemyWarrior = enemyWarriorByCardId.get(cardId);
        if (enemyWarrior != null) {
            if (currentSpellAttackId != null) {
                spellAttack(currentSpellAttackId, enemyWarrior.id);
            } else if (currentSpellPassiveId != null) {
                spellPassive(currentSpellPassiveId, enemyWarrior.id);
            } else {
                defaultAttack(enemyWarrior.id);
            }
        }
    }
}

function setSpellAttack(spellAttackId, e) {
    e.stopPropagation();
    resetCurrentSpells();
    currentSpellAttackId = spellAttackId;
}

function setSpellHeal(spellHealId, e) {
    e.stopPropagation();
    resetCurrentSpells();
    currentSpellHealId = spellHealId;
}

function setSpellExhortation(spellExhortationId, e) {
    e.stopPropagation();
    resetCurrentSpells();
    currentSpellExhortationId = spellExhortationId;
}

function setSpellPassive(spellPassiveId, e) {
    e.stopPropagation();
    resetCurrentSpells();
    currentSpellPassiveId = spellPassiveId;
}

function resetCurrentSpells() {
    currentSpellAttackId = null;
    currentSpellHealId = null;
    currentSpellExhortationId = null;
    currentSpellPassiveId = null;
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
        resetCurrentSpells();
    };
    doAjaxRequest(params);
}

function spellHeal(spellHealId, warriorId) {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.spellHeal;
    params.data = {
        spellHealId: spellHealId,
        warriorId: warriorId
    };
    params.requestType = "POST";
    params.successCallbackFunc = function (activeGame) {
        _updateActiveGame(activeGame);
        if (activeGame.gameOver) {
            _printClientGameResult(activeGame.myClientGameResult);
        }
        resetCurrentSpells();
    };
    doAjaxRequest(params);
}

function spellExhortation(spellExhortationId, cardId, isMyPosition) {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.spellExhortation;
    params.data = {
        spellExhortationId: spellExhortationId,
        position: cardId,
        isMyPosition: isMyPosition
    };
    params.requestType = "POST";
    params.successCallbackFunc = function (activeGame) {
        _updateActiveGame(activeGame);
        if (activeGame.gameOver) {
            _printClientGameResult(activeGame.myClientGameResult);
        }
        resetCurrentSpells();
    };
    doAjaxRequest(params);
}

function spellPassive(spellPassiveId, warriorId) {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.spellPassive;
    params.data = {
        spellPassiveId: spellPassiveId,
        warriorId: warriorId
    };
    params.requestType = "POST";
    params.successCallbackFunc = function (activeGame) {
        _updateActiveGame(activeGame);
        if (activeGame.gameOver) {
            _printClientGameResult(activeGame.myClientGameResult);
        }
        resetCurrentSpells();
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

    out += templates.arenaWarrior.body(context);

    return out;
}

function _updateActiveGame(activeGame) {
    if (!gameIsShow) {
        activeGame.anotherClient.hero.warriors.forEach(function (warriorAnotherClient) {
            enemyWarriorByCardId.set(warriorAnotherClient.position, warriorAnotherClient);
        });

        activeGame.me.hero.warriors.forEach(function (meWarrior) {
            myWarriorByCardId.set(meWarrior.position, meWarrior);
        });

        _printActiveGame(activeGame);

        gameIsShow = true;
    } else {

        var warriorByIds = new Map();

        activeGame.anotherClient.hero.warriors.forEach(function (warrior) {
            warriorByIds.set(warrior.id, warrior);
        });

        updateWarriors(enemyWarriorByCardId, warriorByIds, activeGame);

        addWarriors(enemyWarriorByCardId, warriorByIds, activeGame, false);

        warriorByIds = new Map();

        activeGame.me.hero.warriors.forEach(function (warrior) {
            warriorByIds.set(warrior.id, warrior);
        });

        updateWarriors(myWarriorByCardId, warriorByIds, activeGame);

        addWarriors(myWarriorByCardId, warriorByIds, activeGame, true);

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

            if (currentWarrior.warriorCharacteristics.attack != newWarrior.warriorCharacteristics.attack) {
                currentWarrior.warriorCharacteristics.attack = newWarrior.warriorCharacteristics.attack;
                $("#warrior_" + currentWarrior.id + "> .attack_and_defense > .attack").html(currentWarrior.warriorCharacteristics.attack);
            }

            if (currentWarrior.warriorCharacteristics.physicalDefense != newWarrior.warriorCharacteristics.physicalDefense) {
                currentWarrior.warriorCharacteristics.physicalDefense = newWarrior.warriorCharacteristics.physicalDefense;
                $("#warrior_" + currentWarrior.id + "> attack_and_defense > .physical-defense").html(currentWarrior.warriorCharacteristics.physicalDefense);
            }

            if (activeGame.currentWarrior.id == currentWarrior.id) {
                highlightWarrior(currentWarrior.id);
            } else {
                extinguishWarrior(currentWarrior.id);
            }
        }
    });
}

function addWarriors(currentPlayerWarriorByPosition, newPlayerWarriorByIds, activeGame, isMyPosition) {
    newPlayerWarriorByIds.forEach(function (warrior, warriorId) {
        var position = warrior.position;
        var oldWarrior = currentPlayerWarriorByPosition.get(warrior.position);
        if (oldWarrior == null) {
            var prefix;
            if (isMyPosition) {
                prefix = "#me > .cards > ";
            } else {
                prefix = "#enemy_player > .cards > ";
            }
            $(prefix + "#card_" + warrior.position).html(generateContentArenaCard(warrior, activeGame.currentWarrior.id == warrior.id));
            currentPlayerWarriorByPosition.set(position, warrior);
        }
    });
}