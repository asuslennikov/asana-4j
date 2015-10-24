package ru.jewelline.asana4j.core.impl.http;

import ru.jewelline.asana4j.http.HttpMethod;
import ru.jewelline.asana4j.http.HttpRequest;
import ru.jewelline.asana4j.http.HttpResponse;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestImpl implements HttpRequest {

    private final HttpMethod httpMethod;
    private final HttpClientImpl httpClient;

    private String url;
    private Map<String, String> headers;
    private InputStream entityStream;

    public HttpRequestImpl(HttpMethod httpMethod, HttpClientImpl httpClient) {
        this.httpMethod = httpMethod;
        this.httpClient = httpClient;
    }

    public void setEntityStream(InputStream entityStream) {
        this.entityStream = entityStream;
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public HttpMethod getMethod() {
        return this.httpMethod;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = new HashMap<>(headers);
    }

    @Override
    public InputStream getRequestBody() {
        return this.entityStream;
    }

    @Override
    public HttpResponse<OutputStream> send() {
        return this.sendAndReadResponse(null);
    }

    @Override
    public <T extends OutputStream> HttpResponse<T> sendAndReadResponse(T destinationStream) {
        return this.httpClient.execute(this, new HttpResponseImpl<>(destinationStream));
    }
}
