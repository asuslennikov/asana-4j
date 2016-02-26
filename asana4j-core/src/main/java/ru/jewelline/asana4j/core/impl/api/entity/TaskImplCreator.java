package ru.jewelline.asana4j.core.impl.api.entity;

import ru.jewelline.asana4j.api.entity.Task;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityContext;
import ru.jewelline.request.http.HttpMethod;

class TaskImplCreator extends TaskImplBuilder<Task.TaskCreator> implements Task.TaskCreator {

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
    public Task.TaskCreator setTags(long... taskIds) {
        putField(TaskImplProcessor.TAGS.getFieldName(), taskIds);
        return this;
    }

    @Override
    public Task create() {
        return this.context.newRequest()
                .setUrl("tasks")
                .setEntity(wrapFieldsAsEntity())
                .buildAs(HttpMethod.POST)
                .execute()
                .asApiObject(this.context.getDeserializer(TaskImpl.class));
    }
}
