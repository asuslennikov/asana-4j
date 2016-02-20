package ru.jewelline.asana4j.api;

import ru.jewelline.asana.core.EntityContext;
import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.HttpRequestFactory;
import ru.jewelline.request.http.modifiers.RequestModifier;

public class HttpCommand<T> implements Command<T> {
    private HttpRequestFactory httpRequestFactory;
    private EntityContext entityContext;
    private Class<T> entityClass;

    public HttpCommand(EntityContext entityContext, HttpRequestFactory httpRequestFactory, Class<T> entityClass) {
        this.httpRequestFactory = httpRequestFactory;
        this.entityClass = entityClass;
    }

    @Override
    public T execute(RequestModifier... requestModifiers) {
        return httpRequestFactory.newRequest(requestModifiers)
                .setUrl("")
                .buildAs(HttpMethod.GET)
                .execute(entityContext.getReader(entityClass))
                .toEntity();
    }
}
