package ru.jewelline.asana4j.core.impl.api.clients;

import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.core.impl.api.ApiRequestBuilderProvider;
import ru.jewelline.asana4j.core.impl.api.entity.ApiEntityContext;
import ru.jewelline.asana4j.http.HttpClient;

public abstract class ApiClientImpl implements ApiRequestBuilderProvider {

    private final AuthenticationService authenticationService;
    private final HttpClient httpClient;
    private final ApiEntityContext entityContext;

    public ApiClientImpl(AuthenticationService authenticationService, HttpClient httpClient) {
        this.authenticationService = authenticationService;
        this.httpClient = httpClient;
        // TODO Don't pass 'this' out of a constructor
        this.entityContext = new ApiEntityContext(this);
    }

    protected AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    protected HttpClient getHttpClient() {
        return httpClient;
    }

    protected ApiEntityContext getEntityContext(){
        return this.entityContext;
    }

    @Override
    public final ApiRequestBuilder newRequest(RequestModifier... requestModifiers) {
        return new ApiRequestWithModifiersBuilder(getAuthenticationService(), getHttpClient())
                .withRequestModifiers(requestModifiers);
    }

}
