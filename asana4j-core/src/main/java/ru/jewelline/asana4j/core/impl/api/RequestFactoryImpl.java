package ru.jewelline.asana4j.core.impl.api;

import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.request.api.ApiRequestBuilder;
import ru.jewelline.request.api.modifiers.RequestModifier;
import ru.jewelline.request.http.HttpRequestBuilder;
import ru.jewelline.request.http.HttpRequestFactory;

public class RequestFactoryImpl implements RequestFactory {
    private final HttpRequestFactory httpRequestFactory;
    private final AuthenticationService authenticationService;

    public RequestFactoryImpl(HttpRequestFactory httpRequestFactory, AuthenticationService authenticationService) {
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
