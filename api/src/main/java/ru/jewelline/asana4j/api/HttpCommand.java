package ru.jewelline.asana4j.api;

import ru.jewelline.asana.common.EntityContext;
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
        return null;
    }
}
