package ru.jewelline.asana4j.core.impl.api.entity;

import ru.jewelline.asana4j.api.entity.Task;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityDeserializer;
import ru.jewelline.asana4j.http.HttpMethod;

public class TaskImplUpdater extends TaskImplBuilder<Task.TaskUpdater> implements Task.TaskUpdater {

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
        this.target.getContext().newRequest()
                .path("tasks/" + this.target.getId())
                .setEntity(wrapFieldsAsEntity())
                .buildAs(HttpMethod.PUT)
                .execute()
                .asApiObject(new ApiEntityDeserializer<>(this.target));
        return this.target;
    }
}
