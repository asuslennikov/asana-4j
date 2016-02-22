package ru.jewelline.asana4j.impl.clients;

import ru.jewelline.asana.core.EntityContext;
import ru.jewelline.asana.core.utils.PagedList;
import ru.jewelline.asana.core.utils.StringUtils;
import ru.jewelline.asana4j.api.clients.UsersClient;
import ru.jewelline.asana4j.api.entities.User;
import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.HttpRequestFactory;
import ru.jewelline.request.http.modifiers.RequestModifier;

public class UsersClientImpl implements UsersClient {
    private static final String BASE_URL = "https://app.asana.com/api/1.0/";
    private final HttpRequestFactory httpRequestFactory;
    private final EntityContext entityContext;

    public UsersClientImpl(HttpRequestFactory httpRequestFactory, EntityContext entityContext) {
        this.httpRequestFactory = httpRequestFactory;
        this.entityContext = entityContext;
    }

    private String appendToBaseUrl(String url) {
        return StringUtils.emptyOrOnlyWhiteSpace(url) ? null : BASE_URL + (url.startsWith("/") ? url.substring(1) : url);
    }
    @Override
    public User getCurrentUser(RequestModifier... requestModifiers) {
        return httpRequestFactory.newRequest(requestModifiers)
                .setUrl(appendToBaseUrl("users/me"))
                .buildAs(HttpMethod.GET)
                .execute(entityContext.getReader(User.class, null))
                .toEntity();
    }

    @Override
    public User getUserById(long userId, RequestModifier... requestModifiers) {
        return null;
    }

    @Override
    public PagedList<User> getUsers(RequestModifier... requestModifiers) {
        return null;
    }

    @Override
    public PagedList<User> getWorkspaceUsers(long workspaceId, RequestModifier... requestModifiers) {
        return null;
    }
}
