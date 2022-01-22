function addEvent() {
    $(".sendEvent").click(function () {
        $(".eventList").append("<button type=\"button\" class=\"btn btn-default\" data-toggle=\"modal\" data-target=\"#someEvent\">\n" +
            "                        <a href=\"#\" class=\"list-group-item list-group-item-action\">\n" +
            "                            <div class=\"d-flex w-100 justify-content-between\">\n" +
            "                                <h5 class=\"mb-1\">Название события</h5>\n" +
            "                                <small>Тут дата</small>\n" +
            "                            </div>\n" +
            "                            <p class=\"mb-1\">Краткое описание Краткое описание Краткое описание Краткое описание\n" +
            "                                Краткое описание Краткое описание Краткое описание Краткое описание Краткое описание\n" +
            "                                Краткое описание Краткое описание Краткое описание Краткое описание Краткое\n" +
            "                                описание </p>\n" +
            "                            <small>Ну и ещё чёт</small>\n" +
            "                        </a>\n" +
            "                    </button>");
    });
}
