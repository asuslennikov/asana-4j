package ru.jewelline.asana4j.core.impl.api;

import ru.jewelline.asana4j.api.UserApiClient;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.core.impl.api.entity.UserImpl;
import ru.jewelline.asana4j.http.HttpClient;
import ru.jewelline.asana4j.http.HttpMethod;
import ru.jewelline.asana4j.utils.ServiceLocator;

import java.util.List;

public class UserApiClientImpl extends ApiClientImpl<User, UserImpl> implements UserApiClient {

    public UserApiClientImpl(AuthenticationService authenticationService, HttpClient httpClient) {
        super(authenticationService, httpClient);
    }

    @Override
    public UserImpl newInstance() {
        return new UserImpl();
    }

    @Override
    public User getCurrentUser() {
        return newRequest()
                .path("users/me")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiObject();
    }

    @Override
    public User getUserById(long userId) {
        return newRequest()
                .path("users/" + userId)
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiObject();
    }

    @Override
    public List<User> getUsers() {
        return newRequest()
                .path("users")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection();
    }

    @Override
    public List<User> getWorkspaceUsers(long workspaceId) {
        return newRequest()
                .path("workspaces/" + workspaceId + "/users")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection();
    }
}
