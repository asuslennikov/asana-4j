package ru.jewelline.asana4j.core.impl.api;

import ru.jewelline.asana4j.api.ApiException;
import ru.jewelline.asana4j.api.ApiRequest;
import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.core.impl.api.entity.ApiEntityInstanceProvider;
import ru.jewelline.asana4j.http.HttpClient;
import ru.jewelline.asana4j.http.HttpMethod;
import ru.jewelline.asana4j.http.HttpRequest;
import ru.jewelline.asana4j.http.HttpRequestBuilder;
import ru.jewelline.asana4j.utils.JsonOutputStream;

public class ApiRequestBuilderImpl<AT, T extends ApiEntity<AT>> implements ApiRequestBuilder<AT> {
    protected static final String BASE_API_URL = "https://app.asana.com/api/1.0/";
    private final ApiEntityInstanceProvider<T> apiInstanceProvider;
    private final HttpRequestBuilder httpRequestBuilder;
    private boolean underConstruction = true;

    public ApiRequestBuilderImpl(HttpClient httpClient, ApiEntityInstanceProvider<T> apiInstanceProvider) {
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
        if (!this.underConstruction){
            throw new ApiException(ApiException.BAD_REQUEST,
                    "You are trying to build request which was already built.");
        }
        this.underConstruction = false;
        HttpRequest<JsonOutputStream> httpRequest = this.httpRequestBuilder.buildAs(method);
        ApiRequestImpl<AT, ?> apiRequest = new ApiRequestImpl<>(httpRequest, this.apiInstanceProvider);
        // TODO set entity for post, put, delete
        return apiRequest;
    }
}
