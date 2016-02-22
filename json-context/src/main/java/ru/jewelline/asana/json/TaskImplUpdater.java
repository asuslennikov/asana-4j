package ru.jewelline.asana.json;

import ru.jewelline.asana.json.im.entity.common.ApiEntityDeserializer;
import ru.jewelline.asana4j.api.entities.Task;
import ru.jewelline.request.http.HttpMethod;

class TaskImplUpdater extends TaskImplBuilder<Task.TaskUpdater> implements Task.TaskUpdater {

    private final TaskImpl target;

    public TaskImplUpdater(TaskImpl target) {
        super(Task.TaskUpdater.class);
        this.target = target;
    }

    @Override
    public Task abandon() {
        this.target.stopUpdate();
        return this.target;
    }

    @Override
    public Task update() {
        this.target.stopUpdate();
        this.target.getContext().apiRequest()
                .path("tasks/" + this.target.getId())
                .setEntity(wrapFieldsAsEntity())
                .buildAs(HttpMethod.PUT)
                .execute()
                .asApiObject(new ApiEntityDeserializer<>(this.target));
        return this.target;
    }
}