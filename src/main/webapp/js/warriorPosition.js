var currentWarriorId = undefined;

var warriorByWarriorIds = new Map();

var positions = [];

function printWarriorPositionPlace() {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.hero;
    params.requestType = "GET";

    params.successCallbackFunc = function (hero) {
        $("body").html(templates.warriorPosition.body(hero));

        hero.warriors.forEach(function (warrior) {
            warriorByWarriorIds.set(warrior.id, warrior);
            // if (warrior.main && warrior.position) {
            //     positionByWarriorIds.set(warrior.position, warrior.id);
            // }
        });
    };

    doAjaxRequest(params);
}

function setCurrentWarrior(nextWarriorId) {

    if (currentWarriorId != undefined) {
        _highlightWarrior(currentWarriorId, false);
    }

    if (currentWarriorId == nextWarriorId) {
        currentWarriorId = undefined;
    } else {
        _highlightWarrior(nextWarriorId, true);
        currentWarriorId = nextWarriorId;
    }

}

function setPositionCurrentWarrior(position) {
    if (currentWarriorId != undefined && positions[position] == undefined) {
        positions[position] = 1;
        warriorByWarriorIds.get(currentWarriorId).position = position;
        $("#position_" + position).css('background-image', 'url(' + (url.imagesPath + warriorByWarriorIds.get(currentWarriorId).pictureName) + ')');
        $("#other_warrior_" + currentWarriorId).remove();
        currentWarriorId = undefined;
    }
}

function _highlightWarrior(warriorId, highlight) {
    if (highlight) {
        $("#other_warrior_" + warriorId).css('box-shadow', '0 0 50px #ffd700');
    } else {
        $("#other_warrior_" + warriorId).css('box-shadow', '0 0 0 0');
    }
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