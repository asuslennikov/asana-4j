package ru.jewelline.asana4j.core.impl.http;

import ru.jewelline.asana4j.http.HttpClient;
import ru.jewelline.asana4j.http.HttpMethod;
import ru.jewelline.asana4j.http.HttpRequest;
import ru.jewelline.asana4j.http.HttpResponse;

import java.io.ByteArrayOutputStream;
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
    private OutputStream responseStream;

    public HttpRequestImpl(HttpMethod httpMethod, HttpClientImpl httpClient) {
        this.httpMethod = httpMethod;
        this.httpClient = httpClient;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = new HashMap<>(headers);
    }

    public void setEntityStream(InputStream entityStream) {
        this.entityStream = entityStream;
    }

    public void setResponseStream(OutputStream responseStream) {
        this.responseStream = responseStream;
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    @Override
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public InputStream getRequestBody() {
        return this.entityStream;
    }

    public OutputStream getResponseStream(int expectedLength) {
        if (this.responseStream == null){
            this.responseStream = new ByteArrayOutputStream(expectedLength);
        }
        return this.responseStream;
    }

    @Override
    public void execute() {

    }
}
