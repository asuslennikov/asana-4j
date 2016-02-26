package ru.jewelline.asana4j.core.impl.api.clients;

import ru.jewelline.asana4j.core.impl.api.RequestFactory;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityContext;
import ru.jewelline.request.http.HttpRequestBuilder;
import ru.jewelline.request.http.modifiers.RequestModifier;

abstract class ApiClientImpl implements RequestFactory {

    private final RequestFactory requestFactory;
    private final ApiEntityContext entityContext;

    public ApiClientImpl(RequestFactory requestFactory, ApiEntityContext entityContext) {
        this.requestFactory = requestFactory;
        this.entityContext = entityContext;
    }

    protected ApiEntityContext getEntityContext(){
        return this.entityContext;
    }

    @Override
    public final HttpRequestBuilder newRequest(RequestModifier... requestModifiers) {
        return this.requestFactory.newRequest(requestModifiers);
    }
}
