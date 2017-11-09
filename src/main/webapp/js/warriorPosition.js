var currentWarriorId = undefined;

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

    if (currentWarriorId != undefined) {
        $("#warrior_" + currentWarriorId).css('box-shadow', '0 0 0 0');
    }

    if (currentWarriorId == nextWarriorId) {
        currentWarriorId = undefined;
    } else {
        $("#warrior_" + nextWarriorId).css('box-shadow', '0 0 50px #ffd700');
        currentWarriorId = nextWarriorId;
    }

}

function setPositionCurrentWarrior(position) {

    //разобраться с этой проблемой

    var warriorByPosition = warriorByPositions.get(position);

    if (currentWarriorId != undefined) {
        if (warriorByPosition != null) {
            $("#warrior_" + currentWarriorId).css('box-shadow', '0 0 0 0');
            $("#warrior_" + warriorByPosition.id).css('box-shadow', '0 0 50px #ffd700');
            currentWarriorId = warriorByPosition.id;
        } else {
            var warrior = warriorByWarriorIds.get(currentWarriorId);
            warrior.main = true;
            warrior.position = position;
            warriorByPositions.set(position, warrior);
            $("#warrior_" + currentWarriorId).remove();
            $("#position_" + position).prepend('' +
                '<div id="warrior_' + currentWarriorId + '">' +
                '<img src="' + url.imagesPath + warrior.pictureName + '" alt="Archer">' +
                '<button onclick="deleteMainWarrior(' + warrior.id + ')">Удалить</button>' +
                '</div>');
            currentWarriorId = undefined;
        }
    } else {
        if (warriorByPosition != null) {
            currentWarriorId = warriorByPosition.id;
            $("#warrior_" + warriorByPosition.id).css('box-shadow', '0 0 50px #ffd700');
        }
    }
}

function deleteMainWarrior(warriorId) {
    //Добавить в панель нерасставленных войнов
    var warrior = warriorByWarriorIds.get(warriorId);
    warrior.main = false;
    warrior.position = undefined;
    $("#warrior_" + warriorId).remove();
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