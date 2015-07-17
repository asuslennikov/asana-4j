package ru.jewelline.asana4j.api;

import ru.jewelline.asana4j.api.entity.User;

import java.util.List;

public interface UserApiClient {
    User getCurrentUser();
    User getUserById(long userId);
    List<User> getUsers();
    List<User> getWorkspaceUsers(long workspaceId);
}
