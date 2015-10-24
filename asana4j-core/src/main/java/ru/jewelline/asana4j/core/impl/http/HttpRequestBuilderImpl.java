package ru.jewelline.asana4j.core.impl.http;

import ru.jewelline.asana4j.http.HttpMethod;
import ru.jewelline.asana4j.http.HttpRequest;
import ru.jewelline.asana4j.http.HttpRequestBuilder;
import ru.jewelline.asana4j.http.NetworkException;
import ru.jewelline.asana4j.utils.URLBuilder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestBuilderImpl implements HttpRequestBuilder {

    private static final String HTTP_PREFIX = "http://";
    private static final String HTTPS_PREFIX = "https://";

    private final HttpClientImpl httpClient;

    private URLBuilder urlBuilder;
    private Map<String, String> headers;
    private InputStream entityStream;

    HttpRequestBuilderImpl(URLBuilder urlBuilder, HttpClientImpl httpClient) {
        this.headers = new HashMap<>();
        this.urlBuilder = urlBuilder;
        this.httpClient = httpClient;
    }

    @Override
    public HttpRequestBuilder path(String baseUrl) {
        if (baseUrl != null) {
            if (baseUrl.startsWith(HTTP_PREFIX) || baseUrl.startsWith(HTTPS_PREFIX)) {
                this.urlBuilder.path(baseUrl);
            } else {
                this.urlBuilder.path(HTTP_PREFIX + baseUrl);
            }
        }
        return this;
    }

    @Override
    public HttpRequestBuilder setQueryParameter(String parameterKey, String parameterValue) {
        this.urlBuilder.addQueryParameter(parameterKey, parameterValue);
        return this;
    }

    @Override
    public HttpRequestBuilder setHeader(String headerKey, String headerValue) {
        this.headers.put(headerKey, headerValue);
        return this;
    }

    @Override
    public HttpRequestBuilder entity(byte[] requestBody) {
        return this.entity(new ByteArrayInputStream(requestBody));
    }

    @Override
    public HttpRequestBuilder entity(InputStream entityStream) {
        this.entityStream = entityStream;
        return this;
    }

    @Override
    public HttpRequest buildAs(HttpMethod method) {
        if (method == null) {
            throw new NetworkException(NetworkException.MALFORMED_URL, "You must specify a request method");
        }
        String url = this.urlBuilder.build();
        if (url == null || !url.startsWith(HTTPS_PREFIX) && !url.startsWith(HTTPS_PREFIX)) {
            throw new NetworkException(NetworkException.MALFORMED_URL, "You must specify the base url for your request");
        }
        HttpRequestImpl request = new HttpRequestImpl(method, httpClient);
        request.setUrl(url);
        request.setHeaders(this.headers);
        request.setEntityStream(this.entityStream);
        return request;
    }
}
