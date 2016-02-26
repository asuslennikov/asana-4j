package ru.jewelline.asana4j.core.impl.api.clients;

import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.clients.UserApiClient;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.api.entity.io.EntityDeserializer;
import ru.jewelline.asana4j.core.impl.api.RequestFactory;
import ru.jewelline.asana4j.core.impl.api.entity.UserImpl;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityContext;
import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.modifiers.RequestModifier;

public class UserApiClientImpl extends ApiClientImpl implements UserApiClient {

    public UserApiClientImpl(RequestFactory requestFactory, ApiEntityContext entityContext) {
        super(requestFactory, entityContext);
    }

    private EntityDeserializer<UserImpl> getUserDeserializer() {
        return getEntityContext().getDeserializer(UserImpl.class);
    }

    @Override
    public User getCurrentUser(RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .setUrl("users/me")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiObject(getUserDeserializer());
    }

    @Override
    public User getUserById(long userId, RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .setUrl("users/" + userId)
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiObject(getUserDeserializer());
    }

    @Override
    public PagedList<User> getUsers(RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .setUrl("users")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection(getUserDeserializer());
    }

    @Override
    public PagedList<User> getWorkspaceUsers(long workspaceId, RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .setUrl("workspaces/" + workspaceId + "/users")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection(getUserDeserializer());
    }
}
