package ru.jewelline.asana4j.core.impl.api.entity;

import ru.jewelline.asana4j.api.entity.Tag;
import ru.jewelline.asana4j.api.entity.Tag.TagUpdater;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityDeserializer;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityResponseReceiver;
import ru.jewelline.request.http.HttpMethod;

class TagImplUpdater extends TagBuilderImpl<TagUpdater> implements TagUpdater {

    private final TagImpl target;

    public TagImplUpdater(TagImpl target) {
        super(TagUpdater.class);
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
        this.target.getContext().newRequest()
                .setUrl("tags/" + this.target.getId())
                .setEntity(wrapFieldsAsEntity())
                .buildAs(HttpMethod.PUT)
                .execute(new ApiEntityResponseReceiver())
                .asApiObject(new ApiEntityDeserializer<>(this.target));
        return this.target;
    }
}
