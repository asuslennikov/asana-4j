package ru.jewelline.asana4j.core.impl.http;

import org.json.JSONObject;
import ru.jewelline.asana4j.http.HttpRequest;
import ru.jewelline.asana4j.http.HttpRequestBuilder;
import ru.jewelline.asana4j.http.HttpResponse;
import ru.jewelline.asana4j.utils.ServiceLocator;
import ru.jewelline.asana4j.utils.URLBuilder;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestBuilderImpl implements HttpRequestBuilder, HttpRequest {

    private final ServiceLocator serviceLocator;
    private final HttpClientImpl httpClient;
    private URLBuilder urlBuilder;

    private Map<String, String> headers;
    private byte[] requestBody;

    HttpRequestBuilderImpl(ServiceLocator serviceLocator, HttpClientImpl httpClient) {
        this.headers = new HashMap<>();
        this.serviceLocator = serviceLocator;
        this.urlBuilder = serviceLocator.getUrlBuilder();
        this.httpClient = httpClient;
    }

    @Override
    public String getUrl() {
        return this.urlBuilder.build();
    }

    @Override
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public byte[] getRequestBody() {
        return this.requestBody;
    }


    @Override
    public HttpRequestBuilder path(String baseUrl) {
        this.urlBuilder.path(baseUrl);
        return this;
    }

    @Override
    public HttpRequestBuilder addQueryParameter(String parameterKey, String parameterValue) {
        this.urlBuilder.addQueryParameter(parameterKey, parameterValue);
        return this;
    }

    @Override
    public HttpRequestBuilder addHeaders(String headerKey, String headerValue) {
        this.headers.put(headerKey, headerValue);
        return this;
    }

    @Override
    public HttpRequestBuilder entity(byte[] requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    @Override
    public HttpRequestBuilder entity(JSONObject object) {
        if (object != null){
            this.entity(object.toString().getBytes());
        }
        return this;
    }

    @Override
    public HttpRequest build() {
        // TODO validation here
        return this;
    }

    @Override
    public HttpResponse get() {
        return this.httpClient.execute(this, HttpRequestTypeImpl.GET);
    }

    @Override
    public HttpResponse put() {
        return this.httpClient.execute(this, HttpRequestTypeImpl.PUT);
    }

    @Override
    public HttpResponse post() {
        return this.httpClient.execute(this, HttpRequestTypeImpl.POST);
    }

    @Override
    public HttpResponse delete() {
        return this.httpClient.execute(this, HttpRequestTypeImpl.DELETE);
    }
}
