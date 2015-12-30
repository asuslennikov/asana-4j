package ru.jewelline.asana4j.core.impl.api.clients;

import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.core.impl.api.ApiEntityInstanceProvider;
import ru.jewelline.asana4j.core.impl.api.ApiRequestBuilderProvider;
import ru.jewelline.asana4j.http.HttpClient;

public abstract class ApiClientImpl<T> implements ApiRequestBuilderProvider, ApiEntityInstanceProvider<T> {

    private final AuthenticationService authenticationService;
    private final HttpClient httpClient;

    public ApiClientImpl(AuthenticationService authenticationService, HttpClient httpClient) {
        this.authenticationService = authenticationService;
        this.httpClient = httpClient;
    }

    protected AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    protected HttpClient getHttpClient() {
        return httpClient;
    }

    @Override
    public ApiRequestBuilder newRequest(RequestModifier... requestModifiers) {
        return new ApiRequestWithModifiersBuilder(getAuthenticationService(), getHttpClient())
                .withRequestModifiers(requestModifiers);
    }

}
