package ru.jewelline.asana4j.core.impl.api.clients;

import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.clients.TagApiClient;
import ru.jewelline.asana4j.api.clients.TagFilter;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.api.entity.Tag;
import ru.jewelline.asana4j.api.entity.io.EntityDeserializer;
import ru.jewelline.asana4j.api.entity.io.SerializableEntity;
import ru.jewelline.asana4j.core.impl.api.RequestFactory;
import ru.jewelline.asana4j.core.impl.api.entity.TagImpl;
import ru.jewelline.asana4j.core.impl.api.entity.TagImplCreator;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityContext;
import ru.jewelline.asana4j.core.impl.api.entity.io.SimpleFieldsUpdater;
import ru.jewelline.asana4j.http.HttpMethod;

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

    private SerializableEntity convertFromTagFilter(TagFilter filter){
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
}
