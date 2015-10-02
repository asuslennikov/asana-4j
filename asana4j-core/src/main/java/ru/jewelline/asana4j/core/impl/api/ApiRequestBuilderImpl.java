package ru.jewelline.asana4j.core.impl.api;

import ru.jewelline.asana4j.api.ApiException;
import ru.jewelline.asana4j.api.ApiRequest;
import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.api.options.RequestOption;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.http.HttpClient;
import ru.jewelline.asana4j.http.HttpMethod;
import ru.jewelline.asana4j.http.HttpRequest;
import ru.jewelline.asana4j.http.HttpRequestBuilder;
import ru.jewelline.asana4j.utils.JsonOutputStream;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ApiRequestBuilderImpl<AT, T extends ApiEntity<AT>> implements ApiRequestBuilder<AT> {
    protected static final String BASE_API_URL = "https://app.asana.com/api/1.0/";
    protected static final String GET_API_OPTION_PREFIX = "opt_";

    private final AuthenticationService authenticationService;

    private final ApiEntityInstanceProvider<T> apiInstanceProvider;
    private final HttpRequestBuilder httpRequestBuilder;
    private boolean underConstruction = true;

    public ApiRequestBuilderImpl(AuthenticationService authenticationService, HttpClient httpClient, ApiEntityInstanceProvider<T> apiInstanceProvider) {
        this.authenticationService = authenticationService;
        this.httpRequestBuilder = httpClient.newRequest();
        this.apiInstanceProvider = apiInstanceProvider;
    }

    @Override
    public ApiRequestBuilder<AT> path(String apiSuffix) {
        this.httpRequestBuilder.path(BASE_API_URL + apiSuffix);
        return this;
    }

    @Override
    public ApiRequestBuilder<AT> setQueryParameter(String parameterKey, String parameterValue) {
        this.httpRequestBuilder.setQueryParameter(parameterKey, parameterValue);
        return this;
    }

    @Override
    public ApiRequestBuilder<AT> setHeader(String headerKey, String headerValue) {
        this.httpRequestBuilder.setHeader(headerKey, headerValue);
        return this;
    }

    @Override
    public ApiRequest<AT> buildAs(HttpMethod method) {
        if (!underConstruction){
            throw new ApiException(ApiException.BAD_REQUEST,
                    "You are trying to build request which was already built.");
        }
        if (this.authenticationService.isAuthenticated()) {
            this.setHeader("Authorization", this.authenticationService.getHeader());
        }
        HttpRequest<JsonOutputStream> httpRequest = this.httpRequestBuilder.buildAs(method);
        ApiRequestImpl<AT, T> apiRequest = new ApiRequestImpl<>(httpRequest, this.apiInstanceProvider);
        // TODO set entity for post, put, delete
        this.underConstruction = false;
        return apiRequest;
    }
}
