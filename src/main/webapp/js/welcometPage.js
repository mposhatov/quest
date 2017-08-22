window.onload = onload();

function onload() {
    showLoaderButtonIsMoreQuests([], []);
    getTemplates();
}

setTimeout(function () {
    $("#anchor").css('display', $(window).scrollTop() > 500 ? 'block' : 'none');
}, 300);

function toTop() {
    $('html, body').animate({scrollTop:0}, 200);
}