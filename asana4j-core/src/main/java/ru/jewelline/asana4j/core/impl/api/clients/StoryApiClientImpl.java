package ru.jewelline.asana4j.core.impl.api.clients;

import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.clients.StoryApiClient;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.api.entity.Story;
import ru.jewelline.asana4j.api.entity.io.EntityDeserializer;
import ru.jewelline.asana4j.core.impl.api.RequestFactory;
import ru.jewelline.asana4j.core.impl.api.entity.StoryImpl;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityContext;
import ru.jewelline.asana4j.core.impl.api.entity.io.SimpleFieldsUpdater;
import ru.jewelline.asana4j.http.HttpMethod;

public class StoryApiClientImpl extends ApiClientImpl implements StoryApiClient {

    public StoryApiClientImpl(RequestFactory requestFactory, ApiEntityContext entityContext) {
        super(requestFactory, entityContext);
    }

    private EntityDeserializer<StoryImpl> getStoryDeserializer() {
        return getEntityContext().getDeserializer(StoryImpl.class);
    }

    @Override
    public Story getStoryById(long storyId, RequestModifier... requestModifiers) {
        return apiRequest(requestModifiers)
                .path("stories/" + String.valueOf(storyId))
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiObject(getStoryDeserializer());
    }

    @Override
    public PagedList<Story> getTaskStories(long taskId, RequestModifier... requestModifiers) {
        return apiRequest(requestModifiers)
                .path("tasks/" + taskId + "/stories")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection(getStoryDeserializer());
    }

    @Override
    public Story createTaskComment(long taskId, String text, RequestModifier... requestModifiers) {
        return apiRequest(requestModifiers)
                .path("tasks/" + taskId + "/stories")
                .setEntity(new SimpleFieldsUpdater()
                        .setField("text", text)
                        .wrapFieldsAsEntity())
                .buildAs(HttpMethod.POST)
                .execute()
                .asApiObject(getStoryDeserializer());
    }
}
