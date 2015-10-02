package ru.jewelline.asana4j.api;

import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.api.options.RequestOption;

public interface UserApiClient {
    User getCurrentUser(RequestOption... requestOptions);
    User getUserById(long userId, RequestOption... requestOptions);
    PagedList<User> getUsers(RequestOption... requestOptions);
    PagedList<User> getWorkspaceUsers(long workspaceId, RequestOption... requestOptions);
}
