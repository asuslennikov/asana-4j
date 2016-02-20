package ru.jewelline.asana4j.impl.clients;

import ru.jewelline.asana4j.api.clients.TagApiClient;
import ru.jewelline.asana4j.api.clients.TagFilter;
import ru.jewelline.asana4j.api.entity.Tag;
import ru.jewelline.asana4j.api.entity.Task;
import ru.jewelline.asana4j.core.impl.api.RequestFactory;
import ru.jewelline.asana4j.impl.entity.TagImpl;
import ru.jewelline.asana4j.impl.entity.TagImplCreator;
import ru.jewelline.asana4j.impl.entity.TaskImpl;
import ru.jewelline.asana4j.impl.entity.common.ApiEntityContext;
import ru.jewelline.asana4j.impl.entity.io.SimpleFieldsUpdater;
import ru.jewelline.request.api.PagedList;
import ru.jewelline.request.api.entity.EntityDeserializer;
import ru.jewelline.request.api.entity.SerializableEntity;
import ru.jewelline.request.api.modifiers.RequestModifier;
import ru.jewelline.request.http.HttpMethod;

public class TagApiClientImpl extends ApiClientImpl implements TagApiClient {

    public TagApiClientImpl(RequestFactory requestFactory, ApiEntityContext entityContext) {
        super(requestFactory, entityContext);
    }

    private EntityDeserializer<TagImpl> getTagDeserializer() {
        return getEntityContext().getDeserializer(TagImpl.class);
    }

    @Override
    public Tag getTagById(long tagId, RequestModifier... requestModifiers) {
        return apiRequest(requestModifiers)
                .path("tags/" + tagId)
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiObject(getTagDeserializer());
    }

    @Override
    public Tag.TagCreator createTag(long workspaceId) {
        return new TagImplCreator(getEntityContext()).setWorkspace(workspaceId);
    }

    @Override
    public PagedList<Tag> getTags(TagFilter filter, RequestModifier... requestModifiers) {
        return apiRequest(requestModifiers)
                .path("tags")
                .setEntity(convertFromTagFilter(filter))
                .buildAs(HttpMethod.GET)
                .execute()
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
        return apiRequest(requestModifiers)
                .path("tags/" + tagId + "/tasks")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection(getEntityContext().getDeserializer(TaskImpl.class));
    }
}
