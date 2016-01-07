package ru.jewelline.asana4j.core.impl.api.clients;

import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.clients.StoryApiClient;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.api.entity.Story;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.core.impl.api.entity.StoryImpl;
import ru.jewelline.asana4j.core.impl.api.entity.io.SimpleFieldsUpdater;
import ru.jewelline.asana4j.http.HttpClient;
import ru.jewelline.asana4j.http.HttpMethod;

public class StoryApiClientImpl extends ApiClientImpl implements StoryApiClient {

    public StoryApiClientImpl(AuthenticationService authenticationService, HttpClient httpClient) {
        super(authenticationService, httpClient);
    }

    @Override
    public Story getStoryById(long storyId, RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .path("stories/" + String.valueOf(storyId))
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiObject(getEntityContext().getDeserializer(StoryImpl.class));
    }

    @Override
    public PagedList<Story> getTaskStories(long taskId, RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .path("tasks/" + taskId + "/stories")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection(getEntityContext().getDeserializer(StoryImpl.class));
    }

    @Override
    public Story createTaskComment(long taskId, String text, RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .path("tasks/" + taskId + "/stories")
                .setEntity(new SimpleFieldsUpdater()
                        .setField("text", text)
                        .wrapFieldsAsEntity())
                .buildAs(HttpMethod.POST)
                .execute()
                .asApiObject(getEntityContext().getDeserializer(StoryImpl.class));
    }
}
