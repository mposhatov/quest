window.onload = onload();

function onload() {
    getTemplateWith(templates.questTemplate.name, function() {
        getQuests([], [], false);
    });
    getTemplates();
}