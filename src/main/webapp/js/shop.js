function showShop() {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.warriorShops;
    params.requestType = "GET";
    params.successCallbackFunc = function (warriorShops) {
        $("body").html(templates.shop.body({warriors:warriorShops}));
    };
    doAjaxRequest(params);
}

function buyWarrior(warriorShopId) {
    var params = $.extend({}, defaultAjaxParams);
    params.url = url.buyWarrior;

    params.requestType = "POST";
    params.data = {
        warriorShopId: warriorShopId
    };

    params.successCallbackFunc = function (warrior) {
    };

    doAjaxRequest(params);
}