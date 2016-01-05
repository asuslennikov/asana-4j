package ru.jewelline.asana4j.api.clients;

import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.api.entity.Task;

public interface TaskApiClient {

    Task getTaskById(long taskId, RequestModifier... requestModifiers);

    PagedList<Task> getTasksForProject(long projectId, RequestModifier... requestModifiers);

    void deleteTask(long taskId);
}
