window.onload = onload();

function onload() {
    getTemplatesWith(function() {
        getFilters();
        getQuests([], [], false);
    });
}