var currentWarriorId = null;

var warriorByWarriorIds = new Map();
var warriorByPositions = new Map();

function printWarriorPositionPlace() {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.hero;
    params.requestType = "GET";

    params.successCallbackFunc = function (hero) {
        $("body").html(templates.warriorPosition.body(hero));

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
            extinguishWarrior(currentWarriorId);
            highlightWarrior(warriorByPosition.id);
            currentWarriorId = warriorByPosition.id;
        } else {
            var warrior = warriorByWarriorIds.get(currentWarriorId);
            if (warrior.position != null && warrior.position != undefined) {
                warriorByPositions.delete(warrior.position);
            }
            warrior.position = position;
            warriorByPositions.set(position, warrior);
            $("#warrior_" + currentWarriorId).remove();
            $("#position_" + position).prepend('' +
                '<div id="warrior_' + currentWarriorId + '">' +
                '<img src="' + url.imagesPath + warrior.pictureName + '" alt="Archer">' +
                '<button onclick="deleteMainWarrior(' + warrior.id + ')">Удалить</button>' +
                '</div>');
            currentWarriorId = null;
        }
    } else {
        if (warriorByPosition != null) {
            currentWarriorId = warriorByPosition.id;
            highlightWarrior(warriorByPosition.id);
        }
    }
}

function deleteMainWarrior(warriorId) {

    var warrior = warriorByWarriorIds.get(warriorId);

    warriorByPositions.delete(warrior.position);

    warrior.position = null;

    $("#warrior_" + warriorId).remove();

    $("#queue").append('' +
        '<div id="warrior_' + warriorId + '" class="card_B" onclick="setCurrentWarrior(' + warriorId + ')">' +
        '<img src="' + url.imagesPath + warrior.pictureName + '" alt="Archer">' +
        '</div>');

    if (currentWarriorId == warriorId) {
        extinguishWarrior(currentWarriorId);
        currentWarriorId = null;
    }
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