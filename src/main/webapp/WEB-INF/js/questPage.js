window.onload = onload();

function onload() {
    getTemplateWith(templates.filtersTemplate.name, function() {
        getFilters();
    });
    getTemplateWith(templates.questTemplate.name, function() {
        getQuests([], [], false);
    });
}