package ru.jewelline.asana4j.core.impl.http;

import ru.jewelline.asana4j.http.HttpMethod;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public enum HttpMethodWorker {
    GET(HttpMethod.GET) {
        @Override
        public void handleRequest(HttpRequestImpl httpRequest, HttpURLConnection connection) {
            // do nothing
        }
    },
    POST(HttpMethod.POST),
    PUT(HttpMethod.PUT),
    DELETE(HttpMethod.DELETE),
    ;

    private HttpMethod httpMethod;

    HttpMethodWorker(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public void handleRequest(HttpRequestImpl httpRequest, HttpURLConnection connection) throws IOException {
        // we assume that httpRequest and connection are never null
        InputStream requestBody = httpRequest.getRequestBody();
        if (requestBody != null) {
            connection.setDoOutput(true);
            connection.setRequestMethod(this.httpMethod.method());
            HttpClientImpl.copyStreams(requestBody, connection.getOutputStream());
        } else {
            connection.setRequestMethod(this.httpMethod.method());
        }
    }
}
