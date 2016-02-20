package ru.jewelline.asana4j.api.models;

import ru.jewelline.asana4j.api.entities.Tag;
import ru.jewelline.asana4j.impl.entity.common.ApiEntityContext;
import ru.jewelline.request.http.HttpMethod;

public class TagImplCreator extends TagBuilderImpl<Tag.TagCreator> implements Tag.TagCreator {

    private final ApiEntityContext context;

    public TagImplCreator(ApiEntityContext context) {
        super(Tag.TagCreator.class);
        this.context = context;
    }

    @Override
    public Tag.TagCreator setWorkspace(long workspaceId) {
        putField(TagImplProcessor.WORKSPACE.getFieldName(), workspaceId);
        return this;
    }

    @Override
    public Tag create() {
        return this.context.apiRequest()
                .path("tags")
                .setEntity(wrapFieldsAsEntity())
                .buildAs(HttpMethod.POST)
                .execute()
                .asApiObject(this.context.getDeserializer(TagImpl.class));
    }
}
