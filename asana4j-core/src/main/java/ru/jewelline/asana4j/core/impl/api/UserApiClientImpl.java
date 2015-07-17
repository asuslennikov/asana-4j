package ru.jewelline.asana4j.core.impl.api;

import ru.jewelline.asana4j.api.UserApiClient;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.core.impl.api.entity.UserImpl;
import ru.jewelline.asana4j.utils.ServiceLocator;

import java.util.List;

public class UserApiClientImpl extends ApiClientImpl<User, UserImpl> implements UserApiClient {

    public UserApiClientImpl(ServiceLocator serviceLocator) {
        super(serviceLocator);
    }

    @Override
    public UserImpl newInstance() {
        return new UserImpl();
    }

    @Override
    public User getCurrentUser() {
        return newRequest()
                .path("users/me")
                .build()
                .get()
                .asApiObject();
    }

    @Override
    public User getUserById(long userId) {
        return newRequest()
                .path("users/" + userId)
                .build()
                .get()
                .asApiObject();
    }

    @Override
    public List<User> getUsers() {
        return newRequest()
                .path("users")
                .build()
                .get()
                .asApiCollection();
    }

    @Override
    public List<User> getWorkspaceUsers(long workspaceId) {
        return newRequest()
                .path("workspaces/" + workspaceId + "/users")
                .build()
                .get()
                .asApiCollection();
    }
}
