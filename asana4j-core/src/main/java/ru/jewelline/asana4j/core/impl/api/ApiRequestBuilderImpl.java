package ru.jewelline.asana4j.core.impl.api;

import ru.jewelline.asana4j.api.ApiRequest;
import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.http.HttpClient;
import ru.jewelline.asana4j.http.HttpMethod;
import ru.jewelline.asana4j.http.HttpRequest;
import ru.jewelline.asana4j.http.HttpRequestBuilder;
import ru.jewelline.asana4j.utils.JsonOutputStream;
import ru.jewelline.asana4j.utils.ServiceLocator;

import java.util.HashMap;
import java.util.Map;

public class ApiRequestBuilderImpl<T> implements ApiRequestBuilder<T> {
    protected static final String BASE_API_URL = "https://app.asana.com/api/1.0/";

    private final AuthenticationService authenticationService;

    private final ApiClientImpl<T, ?> apiClient;
    private final HttpRequestBuilder httpRequestBuilder;
    private Map<String, Object> requestOptions;

    public ApiRequestBuilderImpl(AuthenticationService authenticationService, HttpClient httpClient, ApiClientImpl<T, ?> apiClient) {
        this.authenticationService = authenticationService;
        this.httpRequestBuilder = httpClient.newRequest();
        this.apiClient = apiClient;
        this.requestOptions = new HashMap<>(8);
    }

    @Override
    public ApiRequestBuilder<T> path(String apiSuffix) {
        this.httpRequestBuilder.path(BASE_API_URL + apiSuffix);
        return this;
    }

    @Override
    public ApiRequestBuilder<T> setApiOption(String option, Object value) {
        this.requestOptions.put(option, value);
        return this;
    }

    @Override
    public ApiRequestBuilder<T> setQueryParameter(String parameterKey, String parameterValue) {
        this.httpRequestBuilder.setQueryParameter(parameterKey, parameterValue);
        return this;
    }

    @Override
    public ApiRequestBuilder<T> setHeader(String headerKey, String headerValue) {
        this.httpRequestBuilder.setHeader(headerKey, headerValue);
        return this;
    }

    @Override
    public ApiRequest<T> buildAs(HttpMethod method) {
        if (this.authenticationService.isAuthenticated()) {
            this.setHeader("Authorization", this.authenticationService.getHeader());
        }
        ApiRequestImpl<T> apiRequest = new ApiRequestImpl<>(this.httpRequestBuilder.<JsonOutputStream>buildAs(method), this.apiClient);
        // TODO set entity for post, put, delete
        return apiRequest;
    }
}
