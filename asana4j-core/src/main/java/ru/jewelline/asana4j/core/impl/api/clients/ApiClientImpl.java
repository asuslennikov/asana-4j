package ru.jewelline.asana4j.core.impl.api.clients;

import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.core.impl.api.ApiClient;
import ru.jewelline.asana4j.core.impl.api.entity.ApiEntityContext;
import ru.jewelline.asana4j.http.HttpClient;
import ru.jewelline.asana4j.http.HttpRequestBuilder;

public abstract class ApiClientImpl implements ApiClient {

    private final AuthenticationService authenticationService;
    private final HttpClient httpClient;
    private final ApiEntityContext entityContext;

    public ApiClientImpl(AuthenticationService authenticationService, HttpClient httpClient) {
        this.authenticationService = authenticationService;
        this.httpClient = httpClient;
        // TODO Don't pass 'this' out of a constructor
        this.entityContext = new ApiEntityContext(this);
    }

    protected ApiEntityContext getEntityContext(){
        return this.entityContext;
    }

    @Override
    public HttpRequestBuilder newRawRequest() {
        return this.httpClient.newRequest();
    }

    @Override
    public final ApiRequestBuilder newRequest(RequestModifier... requestModifiers) {
        return new ApiRequestWithModifiersBuilder(this.authenticationService, this.httpClient)
                .withRequestModifiers(requestModifiers);
    }
}
