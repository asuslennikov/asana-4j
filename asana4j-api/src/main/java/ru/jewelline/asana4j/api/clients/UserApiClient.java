package ru.jewelline.asana4j.api.clients;

import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.api.entity.User;

public interface UserApiClient {
    User getCurrentUser(RequestModifier... requestModifiers);
    User getUserById(long userId, RequestModifier... requestModifiers);
    PagedList<User> getUsers(RequestModifier... requestModifiers);
    PagedList<User> getWorkspaceUsers(long workspaceId, RequestModifier... requestModifiers);
}
