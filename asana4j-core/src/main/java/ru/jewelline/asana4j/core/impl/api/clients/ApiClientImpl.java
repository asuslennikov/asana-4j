package ru.jewelline.asana4j.core.impl.api.clients;

import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityContext;
import ru.jewelline.request.http.HttpRequestBuilder;
import ru.jewelline.request.http.HttpRequestFactory;
import ru.jewelline.request.http.modifiers.RequestModifier;

abstract class ApiClientImpl {

    private final HttpRequestFactory httpRequestFactory;
    private final ApiEntityContext entityContext;

    public ApiClientImpl(HttpRequestFactory httpRequestFactory, ApiEntityContext entityContext) {
        this.httpRequestFactory = httpRequestFactory;
        this.entityContext = entityContext;
    }

    protected ApiEntityContext getEntityContext() {
        return this.entityContext;
    }

    public final HttpRequestBuilder newRequest(RequestModifier... requestModifiers) {
        return this.httpRequestFactory.newRequest(requestModifiers);
    }
}
