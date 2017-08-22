window.onload = getTemplates();

function startGame(questId) {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.createGame;
    params.dataType = null;
    params.data = {
        questId: questId
    };
    params.successCallbackFunc = function (activeGame) {
        window.location.href = url.getActiveGame + "?activeGameId=" + activeGame.id;
    };

    doAjaxRequest(params);
}

function nextStep(activeGameId, selectedAnswerId, nextStep, winning) {
    if (nextStep) {
        var params = $.extend({}, defaultAjaxParams);
        params.url = url.updateGame;
        params.data = {
            activeGameId: activeGameId,
            selectedAnswerId: selectedAnswerId
        };
        params.successCallbackFunc = function (activeGame) {
            setBackground('body', activeGame.step.backgroundName);
            $("#step").text(activeGame.step.description);
            $("#answers").html(templates.answersTemplate.body(activeGame));
            $("#subjects").html(templates.subjectsTemplate.body(activeGame));
            $("#events").html(templates.eventsTemplate.body(activeGame));
        };
        doAjaxRequest(params);
    } else {
        closeGame(activeGameId, winning);
    }
}

function closeGame(activeGameId, winning) {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.closeGame;
    params.data = {
        activeGameId: activeGameId,
        winning: winning
    };
    params.dataType = null;
    params.successCallbackFunc = function () {
        window.location.href = url.welcome;
    };
    doAjaxRequest(params);
}
