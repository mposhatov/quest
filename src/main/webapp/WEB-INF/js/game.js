window.onload = getTemplates();

function startGame(questId) {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.createGame;
    params.data = {
        questId: questId
    };
    params.successCallbackFunc = function (activeGame) {
        $('body').load("views/stepTemplate.html", function () {
            setBackground(activeGame.step.background.contentType, activeGame.step.background.content);
            $("#step").text(activeGame.step.description);
            $("#answers").html(templates.answersTemplate.body(activeGame));
            $("#subjects").html(templates.subjectsTemplate.body(activeGame));
            $("#events").html(templates.eventsTemplate.body(activeGame));
            $("#exit").html(templates.exitTemplate.body(activeGame));
        });
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
            $('body').load("views/stepTemplate.html", function () {
                setBackground(activeGame.step.background.contentType, activeGame.step.background.content);
                $("#step").text(activeGame.step.description);
                $("#answers").html(templates.answersTemplate.body(activeGame));
                $("#subjects").html(templates.subjectsTemplate.body(activeGame));
                $("#events").html(templates.eventsTemplate.body(activeGame));
                $("#exit").html(templates.exitTemplate.body(activeGame));
            });
        };
        doAjaxRequest(params);
    } else {
        closeGame(activeGameId, winning);
    }
}

function closeGame(activeGameId, winning) {
    // var params = $.extend({}, defaultAjaxParams);
    // params.url = url.closeGame;
    // params.data = {
    //     activeGameId: activeGameId,
    //     winning: winning
    // };
    // params.successCallbackFunc = function () {
    //     window.location.href = 'profile';
    // };
    // doAjaxRequest(params);


    $.ajax({
        method: "POST",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: "/closeGame",
        data: {
            activeGameId: activeGameId,
            winning: winning
        },
        success: function () {
            window.location.href = 'profile';
        }
    });
}

function setBackground(contentType, content) {
    $('body').css('backgroundImage', 'url(data:' + contentType + ',' + content + ')');
}
