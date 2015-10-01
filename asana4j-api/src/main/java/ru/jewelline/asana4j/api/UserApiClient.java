package ru.jewelline.asana4j.api;

import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.api.params.QueryParameter;

public interface UserApiClient {
    User getCurrentUser(QueryParameter... params);
    User getUserById(long userId, QueryParameter... params);
    PagedList<User> getUsers(QueryParameter... params);
    PagedList<User> getWorkspaceUsers(long workspaceId, QueryParameter... params);
}
