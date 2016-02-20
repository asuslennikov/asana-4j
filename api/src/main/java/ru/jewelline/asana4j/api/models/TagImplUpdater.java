package ru.jewelline.asana4j.api.models;

import ru.jewelline.asana4j.api.entities.Tag;
import ru.jewelline.asana4j.impl.entity.common.ApiEntityDeserializer;
import ru.jewelline.request.http.HttpMethod;

class TagImplUpdater extends TagBuilderImpl<Tag.TagUpdater> implements Tag.TagUpdater {

    private final TagImpl target;

    public TagImplUpdater(TagImpl target) {
        super(Tag.TagUpdater.class);
        this.target = target;
    }

    @Override
    public Tag abandon() {
        this.target.stopUpdate();
        return this.target;
    }

    @Override
    public Tag update() {
        this.target.stopUpdate();
        this.target.getContext().apiRequest()
                .path("tags/" + this.target.getId())
                .setEntity(wrapFieldsAsEntity())
                .buildAs(HttpMethod.PUT)
                .execute()
                .asApiObject(new ApiEntityDeserializer<>(this.target));
        return this.target;
    }
}
