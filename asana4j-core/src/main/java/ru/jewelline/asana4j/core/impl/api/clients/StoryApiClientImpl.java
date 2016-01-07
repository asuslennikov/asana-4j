package ru.jewelline.asana4j.core.impl.api.clients;

import ru.jewelline.asana4j.api.clients.StoryApiClient;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.api.entity.Story;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.core.impl.api.entity.StoryImpl;
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
}
