package ru.jewelline.asana4j.impl.clients;

import ru.jewelline.asana4j.core.impl.api.RequestFactory;
import ru.jewelline.asana4j.impl.entity.common.ApiEntityContext;
import ru.jewelline.request.api.ApiRequestBuilder;
import ru.jewelline.request.api.modifiers.RequestModifier;
import ru.jewelline.request.http.HttpRequestBuilder;

abstract class ApiClientImpl implements RequestFactory {

    private final RequestFactory requestFactory;
    private final ApiEntityContext entityContext;

    public ApiClientImpl(RequestFactory requestFactory, ApiEntityContext entityContext) {
        this.requestFactory = requestFactory;
        this.entityContext = entityContext;
    }

    protected ApiEntityContext getEntityContext() {
        return this.entityContext;
    }

    @Override
    public HttpRequestBuilder httpRequest() {
        return this.requestFactory.httpRequest();
    }

    @Override
    public final ApiRequestBuilder apiRequest(RequestModifier... requestModifiers) {
        return this.requestFactory.apiRequest(requestModifiers);
    }
}
