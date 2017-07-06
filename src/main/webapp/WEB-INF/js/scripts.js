function startGame(questId) {
    $.ajax({
        method: "POST",
        data: {
            questId: questId
        },
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: "/createGame",
        success: function() {
            window.location.href = 'quest';
        }
    });
}

function nextStep(answerId, nextStep) {
    if(nextStep == "true") {
        $.ajax({
            method: "POST",
            data: {
                answerId: answerId
            },
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            url: "/updateGame",
            success: function() {
                window.location.href = 'quest';
            }
        });
    } else {
        closeGame(true);
    }

}

function closeGame(gameCompleted) {
    $.ajax({
        method: "POST",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: "/closeGame",
        data: {
            gameCompleted: gameCompleted
        },
        success: function() {
            window.location.href = 'profile';
        }
    });
}