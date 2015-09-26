package ru.jewelline.asana4j.core.impl.api;

import ru.jewelline.asana4j.api.ApiException;
import ru.jewelline.asana4j.api.ApiRequest;
import ru.jewelline.asana4j.api.ApiRequestBuilder;
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

public class ApiRequestBuilderImpl<T> implements ApiRequestBuilder<T> {
    protected static final String BASE_API_URL = "https://app.asana.com/api/1.0/";
    protected static final String GET_API_OPTION_PREFIX = "opt_";

    private final AuthenticationService authenticationService;

    private final ApiClientImpl<T, ?> apiClient;
    private final HttpRequestBuilder httpRequestBuilder;
    private Map<String, Object> requestOptions;
    private boolean underConstruction = true;

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
        if (!underConstruction){
            throw new ApiException(ApiException.BAD_REQUEST,
                    "You are trying to build request which was already built.");
        }
        if (this.authenticationService.isAuthenticated()) {
            this.setHeader("Authorization", this.authenticationService.getHeader());
        }
        appendApiOptions(method);
        HttpRequest<JsonOutputStream> httpRequest = this.httpRequestBuilder.buildAs(method);
        ApiRequestImpl<T> apiRequest = new ApiRequestImpl<>(httpRequest, this.apiClient);
        // TODO set entity for post, put, delete
        return apiRequest;
    }

    private void appendApiOptions(HttpMethod method){
        if (HttpMethod.GET.equals(method)){
            appendApiOptionsToUrl();
        } else {
            appendApiOptionsToEntity();
        }
    }

    private void appendApiOptionsToUrl(){
        for (Map.Entry<String, Object> option : requestOptions.entrySet()) {
            Object value = option.getValue();
            if (Boolean.TRUE.equals(value)){
                this.httpRequestBuilder.setQueryParameter(GET_API_OPTION_PREFIX + option.getKey(), "true");
            } else if (value instanceof String){
                this.httpRequestBuilder.setQueryParameter(GET_API_OPTION_PREFIX + option.getKey(), (String) value);
            } else if (value instanceof Collection){
                Iterator valueItr = ((Collection) value).iterator();
                if (valueItr.hasNext()) {
                    StringBuilder optionValue = new StringBuilder();
                    while (valueItr.hasNext()) {
                        optionValue.append(valueItr.next()).append(",");
                    }
                    optionValue.setLength(optionValue.length() - 1);
                    this.httpRequestBuilder.setQueryParameter(GET_API_OPTION_PREFIX + option.getKey(), optionValue.toString());
                }
            }
        }
    }

    private void appendApiOptionsToEntity(){

    }
}
