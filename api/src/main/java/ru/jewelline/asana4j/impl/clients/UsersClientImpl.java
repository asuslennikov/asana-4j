package ru.jewelline.asana4j.impl.clients;

import ru.jewelline.asana4j.api.beans.UserBean;
import ru.jewelline.asana4j.api.clients.UsersClient;
import ru.jewelline.asana4j.api.entities.User;
import ru.jewelline.asana4j.core.impl.api.RequestFactory;
import ru.jewelline.asana4j.impl.entity.common.ApiEntityContext;
import ru.jewelline.request.api.PagedList;
import ru.jewelline.request.api.entity.EntityDeserializer;
import ru.jewelline.request.api.modifiers.RequestModifier;
import ru.jewelline.request.http.HttpMethod;

public class UsersClientImpl extends ApiClientImpl implements UsersClient {

    public UsersClientImpl(RequestFactory requestFactory, ApiEntityContext entityContext) {
        super(requestFactory, entityContext);
    }

    private EntityDeserializer<UserBean> getUserDeserializer() {
        return getEntityContext().getDeserializer(UserBean.class);
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
