function startGame(questId) {
    $.ajax({
        method: "POST",
        data: {
            questId: questId
        },
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: "/createGame",
        success: function(activeGame) {
            $('body').load("views/stepTemplate.html", function () {
                reprintStep(activeGame.step.description);
                setBackground(activeGame.step.background.contentType, activeGame.step.background.content);
                reprintAnswers(activeGame);
                reprintSubjects(activeGame.subjects);
                reprintEvents(activeGame.events);
                printExit(activeGame.id);
            });

        }
    });
}

function nextStep(activeGameId, selectedAnswerId, nextStep, winning) {
    if(nextStep) {
        $.ajax({
            method: "POST",
            dataType: "json",
            data: {
                activeGameId: activeGameId,
                selectedAnswerId: selectedAnswerId
            },
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            url: "/updateGame",
            success: function(activeGame) {
                setBackground(activeGame.step.background.contentType, activeGame.step.background.content);
                reprintStep(activeGame.step.description);
                reprintAnswers(activeGame);
                reprintSubjects(activeGame.subjects);
                reprintEvents(activeGame.events);
                printExit(activeGame.id);
            }
        });
    } else {
        closeGame(activeGameId, winning);
    }
}

function closeGame(activeGameId, winning) {
    $.ajax({
        method: "POST",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: "/closeGame",
        data: {
            activeGameId: activeGameId,
            winning: winning
        },
        success: function() {
            window.location.href = 'profile';
        }
    });
}

function setBackground(contentType, content) {
    $('body').css('backgroundImage', 'url(data:' + contentType + ',' + content + ')');
}

function reprintStep(description) {
    $("#step").text(description);
}

function reprintAnswers(activeGame) {
    answersHtml = '';
    var activeGameId = activeGame.id;
    var answers = activeGame.step.answers;
    answers.forEach(function(answer) {
        answersHtml += '<div class="answer" onclick="nextStep(' + activeGameId + ',' + answer.id + ',' +
            answer.nextStep + ',' + answer.winning + ')">';
        answersHtml += answer.description;
        answersHtml += '</div>';
    });
    $("#answers").html(answersHtml);
}

function reprintSubjects(subjects) {
    subjectHtml = '<h4>Полученные предметы:</h4>';
    subjects.forEach(function(subject) {
        subjectHtml += '<div class="subject">' + subject.name + "</div>";
    });
    $("#subjects").html(subjectHtml);
}

function reprintEvents(events) {
    eventHtml = '<h4>Выполненные действия:</h4>';
    events.forEach(function(event) {
        eventHtml += '<div class="event">' + event.name + "</div>";
    });
    $("#events").html(eventHtml);
}

function printExit(activeGameId) {
    var exitHtml = '<div id="closeGame" class="closeGame" onclick="closeGame(' + activeGameId + ',' + false +')">';
    exitHtml += 'Завершить игру';
    exitHtml + 'div';
    $("#exit").html(exitHtml);
}