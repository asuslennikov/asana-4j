package ru.jewelline.asana4j.core.impl.api.entity;

import ru.jewelline.asana4j.api.entity.Project;
import ru.jewelline.asana4j.api.entity.Task;
import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.asana4j.http.HttpMethod;

public class TaskImplCreator extends TaskImplBuilder<Task.TaskCreator> implements Task.TaskCreator {

    private final ApiEntityContext context;

    public TaskImplCreator(ApiEntityContext context) {
        super(Task.TaskCreator.class);
        this.context = context;
    }

    @Override
    public Task.TaskCreator setWorkspace(Workspace workspace) {
        putField(TaskImplProcessor.WORKSPACE.getFieldName(), workspace);
        return this;
    }

    @Override
    public Task.TaskCreator setProjects(Project... projects) {
        putField(TaskImplProcessor.PROJECTS.getFieldName(), projects);
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
