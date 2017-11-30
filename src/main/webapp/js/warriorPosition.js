var currentWarriorId = null;

var warriorByWarriorIds = new Map();
var warriorByPositions = new Map();

function printWarriorPositionPlace() {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.hero;
    params.requestType = "GET";

    params.successCallbackFunc = function (hero) {
        $("body").html(templates.warriorPositionPlace.body(hero));

        hero.warriors.forEach(function (warrior) {
            warriorByWarriorIds.set(warrior.id, warrior);
            if (warrior.main && warrior.position) {
                warriorByPositions.set(warrior.position, warrior);
            }
        });
    };

    doAjaxRequest(params);
}

function setCurrentWarrior(nextWarriorId) {

    if (currentWarriorId != null) {
        extinguishWarrior(currentWarriorId);
    }

    if (currentWarriorId == nextWarriorId) {
        currentWarriorId = null;
    } else {
        highlightWarrior(nextWarriorId);
        currentWarriorId = nextWarriorId;
    }

}

function setPositionCurrentWarrior(position) {

    var warriorByPosition = warriorByPositions.get(position);

    if (currentWarriorId != null) {
        if (warriorByPosition != null) {
            setCurrentWarrior(warriorByPosition.id);
        } else {
            var warrior = warriorByWarriorIds.get(currentWarriorId);

            $("#card_" + position).prepend(generateContentPositionMainCards(position, warrior));

            $("#card_content_" + warrior.position).remove();
            $("#reserve_card_" + warrior.id).remove();

            warriorByPositions.delete(warrior.position);

            warrior.position = position;
            warriorByPositions.set(position, warrior);

            currentWarriorId = null;
        }
    } else {
        if (warriorByPosition != null) {
            setCurrentWarrior(warriorByPosition.id);
        }
    }
}

function deleteMainWarrior(warriorId) {

    var warrior = warriorByWarriorIds.get(warriorId);

    $("#card_content_" + warrior.position).remove();

    warriorByPositions.delete(warrior.position);

    warrior.position = null;

    $("#queue").append(generateContentPositionNoMainCards(warrior));

    if (currentWarriorId == warriorId) {
        extinguishWarrior(currentWarriorId);
        currentWarriorId = null;
    }
}

function generateContentPositionMainCards(cardId, warrior) {
    var out = '';
    out += '<div id="card_content_' + cardId + '" class="card_content">';
    out += templates.positionWarrior.body(warrior);
    out += '<button onclick="deleteMainWarrior(' + warrior.id + ')">Удалить</button>';
    out += '</div>';
    return out;
}

function generateContentPositionNoMainCards(warrior) {
    var out = '';
    out += '<div id="reserve_card_' + warrior.id + '" class="card" onclick="setCurrentWarrior(' + warrior.id + ')">';
    out += '<div class="card_content">';
    out += templates.positionWarrior.body(warrior);
    out += '</div>';
    out += '</div>';
    return out;
}

function generateQueue(warriors, meClientId) {
    var out = '';

    if (warriors.length > 0) {
        if (warriors[0].hero.client.id == meClientId) {
            out += '<div class="now myWarrior">';
        } else {
            out += '<div class="now">';
        }
        out += '<img src = "' + (url.imagesPath + warriors[0].pictureName) + '">';
    }

    out += '</div>';

    warriors.forEach(function (warrior) {
        if(warrior.hero.client.id == meClientId) {
            out += '<div class="next myWarrior">';
        } else {
            out += '<div class="next">';
        }
        out += '<img src = "' + (url.imagesPath + warrior.pictureName) + '">';
        out += '</div>';
    });

    return out;
}

function highlightWarrior(warriorId) {
    $("#warrior_" + warriorId).css('box-shadow', '0 0 50px #ffd700');
}

function extinguishWarrior(warriorId) {
    $("#warrior_" + warriorId).css('box-shadow', '0 0 0 0');
}

function acceptWarriorPosition() {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.updateMainWarriors;

    var warriors = [];

    warriorByWarriorIds.forEach(function (value, key) {
        warriors.push(value);
    });

    params.requestType = "POST";

    params.contentType = "application/json";

    // params.data = JSON.stringify({warriors: warriors});

    params.data = JSON.stringify(warriors);

    params.successCallbackFunc = function (warriors) {
    };

    doAjaxRequest(params);
}