package ru.jewelline.asana4j.core.impl.api.clients;

import ru.jewelline.asana4j.api.clients.TagApiClient;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.api.entity.Tag;
import ru.jewelline.asana4j.api.entity.io.EntityDeserializer;
import ru.jewelline.asana4j.core.impl.api.RequestFactory;
import ru.jewelline.asana4j.core.impl.api.entity.TagImpl;
import ru.jewelline.asana4j.core.impl.api.entity.TagImplCreator;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityContext;
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
        return apiRequest()
                .path("tags/" + tagId)
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiObject(getTagDeserializer());
    }

    @Override
    public Tag.TagCreator createTag(long workspaceId) {
        return new TagImplCreator(getEntityContext()).setWorkspace(workspaceId);
    }
}
