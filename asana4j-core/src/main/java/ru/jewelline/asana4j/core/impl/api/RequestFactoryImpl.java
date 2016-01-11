package ru.jewelline.asana4j.core.impl.api;

import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.http.HttpClient;
import ru.jewelline.asana4j.http.HttpRequestBuilder;

public class RequestFactoryImpl implements RequestFactory {
    private final HttpClient httpClient;
    private final AuthenticationService authenticationService;

    public RequestFactoryImpl(HttpClient httpClient, AuthenticationService authenticationService) {
        this.httpClient = httpClient;
        this.authenticationService = authenticationService;
    }

    @Override
    public HttpRequestBuilder httpRequest() {
        return this.httpClient.newRequest();
    }

    @Override
    public ApiRequestBuilder apiRequest(RequestModifier... requestModifiers) {
        return new ApiRequestWithModifiersBuilder(this.httpClient, this.authenticationService)
                .withRequestModifiers(requestModifiers);
    }
}
