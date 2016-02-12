package ru.jewelline.request.api.impl;

import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.core.impl.api.ApiRequestWithModifiersBuilder;
import ru.jewelline.request.api.ApiRequestBuilder;
import ru.jewelline.request.api.ApiRequestFactory;
import ru.jewelline.request.api.modifiers.RequestModifier;
import ru.jewelline.request.http.HttpRequestBuilder;
import ru.jewelline.request.http.HttpRequestFactory;

public class ApiRequestFactoryImpl implements ApiRequestFactory {
    private final HttpRequestFactory httpRequestFactory;
    private final AuthenticationService authenticationService;

    public ApiRequestFactoryImpl(HttpRequestFactory httpRequestFactory, AuthenticationService authenticationService) {
        this.httpRequestFactory = httpRequestFactory;
        this.authenticationService = authenticationService;
    }

    @Override
    public HttpRequestBuilder httpRequest() {
        return this.httpRequestFactory.httpRequest();
    }

    @Override
    public ApiRequestBuilder apiRequest(RequestModifier... requestModifiers) {
        return new ApiRequestWithModifiersBuilder(this.httpRequestFactory, this.authenticationService)
                .withRequestModifiers(requestModifiers);
    }
}
