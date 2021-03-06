package ru.jewelline.asana4j.core.impl.api.clients;

import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.clients.TagFilter;
import ru.jewelline.asana4j.api.clients.TagsClient;
import ru.jewelline.asana4j.api.entity.Tag;
import ru.jewelline.asana4j.api.entity.Task;
import ru.jewelline.asana4j.api.entity.io.EntityDeserializer;
import ru.jewelline.asana4j.core.impl.api.entity.TagImpl;
import ru.jewelline.asana4j.core.impl.api.entity.TagImplCreator;
import ru.jewelline.asana4j.core.impl.api.entity.TaskImpl;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityContext;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityResponseReceiver;
import ru.jewelline.asana4j.core.impl.api.entity.io.SimpleFieldsUpdater;
import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.HttpRequestFactory;
import ru.jewelline.request.http.entity.SerializableEntity;
import ru.jewelline.request.http.modifiers.RequestModifier;

public class TagsClientImpl extends ApiClientImpl implements TagsClient {

    public TagsClientImpl(HttpRequestFactory httpRequestFactory, ApiEntityContext entityContext) {
        super(httpRequestFactory, entityContext);
    }

    private EntityDeserializer<TagImpl> getTagDeserializer() {
        return getEntityContext().getDeserializer(TagImpl.class);
    }

    @Override
    public Tag getTagById(long tagId, RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .setUrl("tags/" + tagId)
                .buildAs(HttpMethod.GET)
                .execute(new ApiEntityResponseReceiver())
                .asApiObject(getTagDeserializer());
    }

    @Override
    public Tag.TagCreator createTag(long workspaceId) {
        return new TagImplCreator(getEntityContext()).setWorkspace(workspaceId);
    }

    @Override
    public PagedList<Tag> getTags(TagFilter filter, RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .setUrl("tags")
                .setEntity(convertFromTagFilter(filter))
                .buildAs(HttpMethod.GET)
                .execute(new ApiEntityResponseReceiver())
                .asApiCollection(getTagDeserializer());
    }

    private SerializableEntity convertFromTagFilter(TagFilter filter) {
        SimpleFieldsUpdater updater = new SimpleFieldsUpdater();
        if (filter != null) {
            if (filter.getWorkspaceId() > 0) {
                updater.setField("workspace", filter.getWorkspaceId());
            }
            if (filter.getTeamId() > 0) {
                updater.setField("team", filter.getTeamId());
            }
            updater.setField("archived", filter.isArchived());
        }
        return updater.wrapFieldsAsEntity();
    }

    @Override
    public PagedList<Task> getTasksForTag(long tagId, RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .setUrl("tags/" + tagId + "/tasks")
                .buildAs(HttpMethod.GET)
                .execute(new ApiEntityResponseReceiver())
                .asApiCollection(getEntityContext().getDeserializer(TaskImpl.class));
    }
}
