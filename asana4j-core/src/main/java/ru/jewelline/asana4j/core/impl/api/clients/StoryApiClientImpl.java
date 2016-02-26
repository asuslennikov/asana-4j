package ru.jewelline.asana4j.core.impl.api.clients;

import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.clients.StoryApiClient;
import ru.jewelline.asana4j.api.entity.Story;
import ru.jewelline.asana4j.api.entity.io.EntityDeserializer;
import ru.jewelline.asana4j.core.impl.api.entity.StoryImpl;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityContext;
import ru.jewelline.asana4j.core.impl.api.entity.io.SimpleFieldsUpdater;
import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.HttpRequestFactory;
import ru.jewelline.request.http.modifiers.RequestModifier;

public class StoryApiClientImpl extends ApiClientImpl implements StoryApiClient {

    public StoryApiClientImpl(HttpRequestFactory httpRequestFactory, ApiEntityContext entityContext) {
        super(HttpRequestFactory, entityContext);
    }

    private EntityDeserializer<StoryImpl> getStoryDeserializer() {
        return getEntityContext().getDeserializer(StoryImpl.class);
    }

    @Override
    public Story getStoryById(long storyId, RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .setUrl("stories/" + String.valueOf(storyId))
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiObject(getStoryDeserializer());
    }

    @Override
    public PagedList<Story> getTaskStories(long taskId, RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .setUrl("tasks/" + taskId + "/stories")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection(getStoryDeserializer());
    }

    @Override
    public Story createTaskComment(long taskId, String text, RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .setUrl("tasks/" + taskId + "/stories")
                .setEntity(new SimpleFieldsUpdater()
                        .setField("text", text)
                        .wrapFieldsAsEntity())
                .buildAs(HttpMethod.POST)
                .execute()
                .asApiObject(getStoryDeserializer());
    }
}
