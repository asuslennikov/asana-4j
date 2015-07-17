package ru.jewelline.asana4j.core.impl.api;

import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.asana4j.api.ApiRequest;
import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.api.ApiResponse;
import ru.jewelline.asana4j.http.HttpRequest;
import ru.jewelline.asana4j.http.HttpRequestBuilder;
import ru.jewelline.asana4j.http.HttpResponse;
import ru.jewelline.asana4j.utils.ServiceLocator;

import java.util.HashMap;
import java.util.Map;

public class ApiRequestBuilderImpl<T> implements ApiRequestBuilder<T>, ApiRequest<T> {
    protected static final String BASE_API_URL = "https://app.asana.com/api/1.0/";

    private final ServiceLocator serviceLocator;
    private final ApiClientImpl<T, ?> apiClient;
    private final HttpRequestBuilder httpRequestBuilder;
    private JSONObject entity;
    private Map<String, Object> requestOptions;
    private HttpRequest httpRequest;

    public ApiRequestBuilderImpl(ServiceLocator serviceLocator, ApiClientImpl<T, ?> apiClient) {
        this.serviceLocator = serviceLocator;
        this.apiClient = apiClient;
        this.httpRequestBuilder = this.serviceLocator.getHttpClient().newRequest();
        this.requestOptions = new HashMap<>(8);
    }

    @Override
    public ApiRequestBuilder<T> path(String baseUrl) {
        this.httpRequestBuilder.path(BASE_API_URL + baseUrl);
        return this;
    }

    @Override
    public ApiRequestBuilder<T> addQueryParameter(String parameterKey, String parameterValue) {
        this.httpRequestBuilder.addQueryParameter(parameterKey, parameterValue);
        return this;
    }

    @Override
    public ApiRequestBuilder<T> addHeaders(String headerKey, String headerValue) {
        this.httpRequestBuilder.addHeaders(headerKey, headerValue);
        return this;
    }

    @Override
    public ApiRequestBuilder<T> entity(byte[] requestBody) {
        if (requestBody != null && requestBody.length != 0){
            try {
                this.entity(new JSONObject(new String(requestBody)));
            } catch (JSONException ex){
                // TODO throw API exception
            }
        } else {
            this.entity = null;
        }
        return this;
    }

    @Override
    public ApiRequestBuilder<T> entity(JSONObject object) {
        this.entity = object;
        return this;
    }

    @Override
    public ApiRequestBuilder<T> addApiOption(String option, Object value) {
        this.requestOptions.put(option, value);
        return this;
    }

    @Override
    public ApiRequest<T> build() {
        if (this.serviceLocator.getAuthenticationService().isAuthenticated()) {
            this.addHeaders("Authorization", this.serviceLocator.getAuthenticationService().getHeader());
        }
        this.httpRequest = this.httpRequestBuilder.build();
        return this;
    }

    @Override
    public String getUrl() {
        return this.httpRequest.getUrl();
    }

    @Override
    public byte[] getRequestBody() {
        return this.httpRequest.getRequestBody();
    }

    @Override
    public Map<String, String> getHeaders() {
        return this.httpRequest.getHeaders();
    }

    @Override
    public ApiResponse<T> get() {
        Function<HttpResponse> httpResponseProvider = new Function<HttpResponse>() {
            @Override
            public HttpResponse run(){
                return ApiRequestBuilderImpl.this.httpRequest.get();
            }
        };
        return this.apiClient.wrapResponse(httpResponseProvider);
    }

    @Override
    public ApiResponse<T> put() {
        Function<HttpResponse> httpResponseProvider = new Function<HttpResponse>() {
            @Override
            public HttpResponse run(){
                return ApiRequestBuilderImpl.this.httpRequest.put();
            }
        };
        return this.apiClient.wrapResponse(httpResponseProvider);
    }

    @Override
    public ApiResponse<T> post() {
        Function<HttpResponse> httpResponseProvider = new Function<HttpResponse>() {
            @Override
            public HttpResponse run(){
                return ApiRequestBuilderImpl.this.httpRequest.delete();
            }
        };
        return this.apiClient.wrapResponse(httpResponseProvider);
    }

    @Override
    public ApiResponse<T> delete() {
        Function<HttpResponse> httpResponseProvider = new Function<HttpResponse>() {
            @Override
            public HttpResponse run(){
                return ApiRequestBuilderImpl.this.httpRequest.get();
            }
        };
        return this.apiClient.wrapResponse(httpResponseProvider);
    }
}
