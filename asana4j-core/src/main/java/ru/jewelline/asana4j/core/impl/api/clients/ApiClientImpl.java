package ru.jewelline.asana4j.core.impl.api.clients;

import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.core.impl.api.RequestFactory;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityContext;
import ru.jewelline.asana4j.http.HttpRequestBuilder;

public abstract class ApiClientImpl implements RequestFactory {

    private final RequestFactory requestFactory;
    private final ApiEntityContext entityContext;

    public ApiClientImpl(RequestFactory requestFactory) {
        this.requestFactory = requestFactory;
        this.entityContext = new ApiEntityContext(requestFactory);
    }

    protected ApiEntityContext getEntityContext(){
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
