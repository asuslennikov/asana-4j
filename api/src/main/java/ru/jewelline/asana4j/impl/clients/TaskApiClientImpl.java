package ru.jewelline.asana4j.impl.clients;

import ru.jewelline.asana4j.api.clients.TaskApiClient;
import ru.jewelline.asana4j.api.entities.Tag;
import ru.jewelline.asana4j.api.entities.Task;
import ru.jewelline.asana4j.api.models.TagImpl;
import ru.jewelline.asana4j.api.models.TaskImpl;
import ru.jewelline.asana4j.core.impl.api.RequestFactory;
import ru.jewelline.asana4j.impl.entity.common.ApiEntityContext;
import ru.jewelline.asana4j.impl.entity.io.SimpleFieldsUpdater;
import ru.jewelline.request.api.PagedList;
import ru.jewelline.request.api.entity.EntityDeserializer;
import ru.jewelline.request.api.modifiers.RequestModifier;
import ru.jewelline.request.http.HttpMethod;

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

    @Override
    public PagedList<Tag> getTaskTags(long taskId, RequestModifier... requestModifiers) {
        return apiRequest()
                .path("tasks/" + taskId + "/tags")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection(getEntityContext().getDeserializer(TagImpl.class));
    }

    @Override
    public void addTag(long taskId, long tagId) {
        getEntityContext().apiRequest()
                .path("tasks/" + taskId + "/addTag")
                .setEntity(new SimpleFieldsUpdater()
                        .setField("tag", tagId)
                        .wrapFieldsAsEntity())
                .buildAs(HttpMethod.POST)
                .execute();
    }

    @Override
    public void removeTag(long taskId, long tagId) {
        getEntityContext().apiRequest()
                .path("tasks/" + taskId + "/removeTag")
                .setEntity(new SimpleFieldsUpdater()
                        .setField("tag", tagId)
                        .wrapFieldsAsEntity())
                .buildAs(HttpMethod.POST)
                .execute();
    }
}
