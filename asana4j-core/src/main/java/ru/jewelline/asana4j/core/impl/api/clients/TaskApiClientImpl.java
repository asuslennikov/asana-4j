package ru.jewelline.asana4j.core.impl.api.clients;

import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.clients.TaskApiClient;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.api.entity.Task;
import ru.jewelline.asana4j.api.entity.io.EntityDeserializer;
import ru.jewelline.asana4j.core.impl.api.RequestFactory;
import ru.jewelline.asana4j.core.impl.api.entity.TaskImpl;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityContext;
import ru.jewelline.asana4j.http.HttpMethod;

public class TaskApiClientImpl extends ApiClientImpl implements TaskApiClient {

    public TaskApiClientImpl(RequestFactory requestFactory, ApiEntityContext entityContext) {
        super(requestFactory, entityContext);
    }

    private EntityDeserializer<TaskImpl> getTaskDeserializer() {
        return getEntityContext().getDeserializer(TaskImpl.class);
    }

    @Override
    public Task getTaskById(long taskId, RequestModifier... requestModifiers) {
        return apiRequest(requestModifiers)
                .path("tasks/" + taskId)
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiObject(getTaskDeserializer());
    }

    @Override
    public PagedList<Task> getTasksForProject(long projectId, RequestModifier... requestModifiers) {
        return apiRequest(requestModifiers)
                .path("projects/" + projectId + "/tasks")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection(getTaskDeserializer());
    }

    @Override
    public void deleteTask(long taskId) {
        apiRequest()
                .path("tasks/" + taskId)
                .buildAs(HttpMethod.GET)
                .execute();
    }
}
