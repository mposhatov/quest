function showShop() {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.hierarchyWarriors;
    params.requestType = "GET";
    params.successCallbackFunc = function (warriorShops) {
        $("body").html(templates.shop.body({warriors:warriorShops}));
    };
    doAjaxRequest(params);
}

function buyWarrior(hierarchyWarriorId) {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.buyWarrior;

    params.requestType = "POST";
    params.data = {
        hierarchyWarriorId: hierarchyWarriorId
    };

    params.successCallbackFunc = function (warrior) {
    };

    doAjaxRequest(params);
}

function buyWarriorUpdate(hierarchyWarriorId) {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.addAvailableWarrior;

    params.requestType = "POST";
    params.data = {
        hierarchyWarriorId: hierarchyWarriorId
    };

    params.successCallbackFunc = function (warrior) {
    };

    doAjaxRequest(params);
}