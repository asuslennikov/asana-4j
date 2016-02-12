package ru.jewelline.asana4j.core.impl.clients;

import ru.jewelline.asana4j.core.api.clients.UserApiClient;
import ru.jewelline.asana4j.core.api.entity.User;
import ru.jewelline.asana4j.core.impl.api.RequestFactory;
import ru.jewelline.asana4j.core.impl.entity.UserImpl;
import ru.jewelline.asana4j.core.impl.entity.common.ApiEntityContext;
import ru.jewelline.request.api.PagedList;
import ru.jewelline.request.api.entity.EntityDeserializer;
import ru.jewelline.request.api.modifiers.RequestModifier;
import ru.jewelline.request.http.HttpMethod;

public class UserApiClientImpl extends ApiClientImpl implements UserApiClient {

    public UserApiClientImpl(RequestFactory requestFactory, ApiEntityContext entityContext) {
        super(requestFactory, entityContext);
    }

    private EntityDeserializer<UserImpl> getUserDeserializer() {
        return getEntityContext().getDeserializer(UserImpl.class);
    }

    @Override
    public User getCurrentUser(RequestModifier... requestModifiers) {
        return apiRequest(requestModifiers)
                .path("users/me")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiObject(getUserDeserializer());
    }

    @Override
    public User getUserById(long userId, RequestModifier... requestModifiers) {
        return apiRequest(requestModifiers)
                .path("users/" + userId)
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiObject(getUserDeserializer());
    }

    @Override
    public PagedList<User> getUsers(RequestModifier... requestModifiers) {
        return apiRequest(requestModifiers)
                .path("users")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection(getUserDeserializer());
    }

    @Override
    public PagedList<User> getWorkspaceUsers(long workspaceId, RequestModifier... requestModifiers) {
        return apiRequest(requestModifiers)
                .path("workspaces/" + workspaceId + "/users")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection(getUserDeserializer());
    }
}
