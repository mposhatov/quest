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

function nextStep(answerId, nextStep, winning) {
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
        closeGame(winning);
    }
}

function closeGame(winning) {
    $.ajax({
        method: "POST",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: "/closeGame",
        data: {
            winning: winning
        },
        success: function() {
            window.location.href = 'profile';
        }
    });
}