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
        _reprintActiveGame(activeGame);
        if (!activeGame.gameComplete) {
            getActiveGame();
        }
    };
    doAjaxRequest(params);
}

function directAttack(warriorId) {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.directAttack;
    params.requestType = "POST";
    params.data = {
        defendingWarriorId: warriorId
    };
    params.successCallbackFunc = function (activeGame) {
        _reprintActiveGame(activeGame);
    };
    doAjaxRequest(params);
}

function _reprintActiveGame(activeGame) {
    $("body").html(templates.activeGame.body(activeGame));
}