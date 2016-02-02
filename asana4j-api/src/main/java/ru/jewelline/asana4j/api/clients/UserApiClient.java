package ru.jewelline.asana4j.api.clients;

import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.request.api.PagedList;
import ru.jewelline.request.api.modifiers.RequestModifier;

public interface UserApiClient {
    User getCurrentUser(RequestModifier... requestModifiers);
    User getUserById(long userId, RequestModifier... requestModifiers);
    PagedList<User> getUsers(RequestModifier... requestModifiers);
    PagedList<User> getWorkspaceUsers(long workspaceId, RequestModifier... requestModifiers);
}
