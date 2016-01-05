package ru.jewelline.asana4j.core.impl.api.entity;

import ru.jewelline.asana4j.api.entity.Task;
import ru.jewelline.asana4j.http.HttpMethod;

public class TaskImplCreator extends TaskImplBuilder<Task.TaskCreator> implements Task.TaskCreator {

    private final ApiEntityContext context;

    public TaskImplCreator(ApiEntityContext context) {
        super(Task.TaskCreator.class);
        this.context = context;
    }

    @Override
    public Task.TaskCreator setWorkspace(long workspaceId) {
        putField(TaskImplProcessor.WORKSPACE.getFieldName(), workspaceId);
        return this;
    }

    @Override
    public Task.TaskCreator setProjects(long... projectIds) {
        putField(TaskImplProcessor.PROJECTS.getFieldName(), projectIds);
        return this;
    }

    @Override
    public Task.TaskCreator setParent(long parentTaskId) {
        putField(TaskImplProcessor.PARENT.getFieldName(), parentTaskId);
        return this;
    }

    @Override
    public Task create() {
        return this.context.newRequest()
                .path("tasks")
                .setEntity(wrapFieldsAsEntity())
                .buildAs(HttpMethod.POST)
                .execute()
                .asApiObject(this.context.getDeserializer(TaskImpl.class));
    }
}
