package ru.jewelline.asana4j.core.impl.api.clients;

import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.clients.TaskApiClient;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.api.entity.Task;
import ru.jewelline.asana4j.api.entity.io.EntityDeserializer;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.core.impl.api.entity.TaskImpl;
import ru.jewelline.asana4j.http.HttpClient;
import ru.jewelline.asana4j.http.HttpMethod;

public class TaskApiClientImpl extends ApiClientImpl implements TaskApiClient {

    public TaskApiClientImpl(AuthenticationService authenticationService, HttpClient httpClient) {
        super(authenticationService, httpClient);
    }

    private EntityDeserializer<TaskImpl> getTaskDeserializer() {
        return getEntityContext().getDeserializer(TaskImpl.class);


    }

    @Override
    public Task getTaskById(long taskId, RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .path("tasks/" + taskId)
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiObject(getTaskDeserializer());
    }

    @Override
    public PagedList<Task> getTasksForProject(long projectId, RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .path("projects/" + projectId + "/tasks")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection(getTaskDeserializer());
    }
}
