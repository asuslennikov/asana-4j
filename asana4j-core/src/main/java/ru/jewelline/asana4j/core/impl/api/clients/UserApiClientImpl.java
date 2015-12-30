package ru.jewelline.asana4j.core.impl.api.clients;

import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.clients.UserApiClient;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityDeserializer;
import ru.jewelline.asana4j.core.impl.api.entity.UserImpl;
import ru.jewelline.asana4j.http.HttpClient;
import ru.jewelline.asana4j.http.HttpMethod;

public class UserApiClientImpl extends ApiClientImpl<UserImpl> implements UserApiClient {

    private final ApiEntityDeserializer<UserImpl> userDeserializer;

    public UserApiClientImpl(AuthenticationService authenticationService, HttpClient httpClient) {
        super(authenticationService, httpClient);
        this.userDeserializer = new ApiEntityDeserializer<>(this);
    }

    @Override
    public UserImpl getInstance() {
        return new UserImpl();
    }

    @Override
    public User getCurrentUser(RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .path("users/me")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiObject(this.userDeserializer);
    }

    @Override
    public User getUserById(long userId, RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .path("users/" + userId)
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiObject(this.userDeserializer);
    }

    @Override
    public PagedList<User> getUsers(RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .path("users")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection(this.userDeserializer);
    }

    @Override
    public PagedList<User> getWorkspaceUsers(long workspaceId, RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .path("workspaces/" + workspaceId + "/users")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection(this.userDeserializer);
    }
}
