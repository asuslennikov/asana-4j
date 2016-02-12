package ru.jewelline.request.http.impl;

import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.HttpRequest;
import ru.jewelline.request.http.NetworkException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public enum HttpMethodSettings {
    GET(HttpMethod.GET) {
        @Override
        public void apply(HttpURLConnection connection, HttpRequest httpRequest) {
            if (connection == null || httpRequest == null){
                throw new IllegalArgumentException("Connection and request can not be null.");
            }
            // do nothing
        }
    },
    POST(HttpMethod.POST),
    PUT(HttpMethod.PUT),
    DELETE(HttpMethod.DELETE),
    ;

    private HttpMethod httpMethod;

    HttpMethodSettings(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public void apply(HttpURLConnection connection, HttpRequest httpRequest) throws IOException {
        if (connection == null || httpRequest == null){
            throw new IllegalArgumentException("Connection and request can not be null.");
        }
        InputStream entity = httpRequest.getEntity();
        if (entity != null) {
            connection.setDoOutput(true);
            connection.setRequestMethod(this.httpMethod.method());
            HttpRequestFactoryImpl.copyStreams(entity, connection.getOutputStream());
        } else {
            connection.setRequestMethod(this.httpMethod.method());
        }
    }

    public static HttpMethodSettings getForHttpMethod(HttpMethod httpMethod){
        for (HttpMethodSettings settings : HttpMethodSettings.values()) {
            if (settings.httpMethod.equals(httpMethod)){
                return settings;
            }
        }
        /**
         This should never happen because we have a null check here:
         {@link HttpRequestBuilderImpl#buildAs(HttpMethod)}
         but let's process such case
        */
        throw new NetworkException(NetworkException.MALFORMED_URL,
                "You are trying to send request with unknown request method.");
    }
}
