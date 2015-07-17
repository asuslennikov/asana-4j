package ru.jewelline.asana4j.core.impl.http;

import ru.jewelline.asana4j.http.HttpRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

public enum HttpRequestTypeImpl {
    GET ("GET"){
        @Override
        public void handleRequest(HttpRequest httpRequest, HttpURLConnection connection) {
            // do nothing
        }
    },
    POST ("POST"),
    PUT ("PUT"),
    DELETE ("DELETE"),
    ;

    private String method;

    HttpRequestTypeImpl(String method) {
        this.method = method;
    }

    public void handleRequest(HttpRequest httpRequest, HttpURLConnection connection) throws IOException{
        // we assume that httpRequest and connection are never null
        byte[] requestBody = httpRequest.getRequestBody();
        if (requestBody != null && requestBody.length > 0){
            connection.setDoOutput(true);
            connection.setRequestMethod(this.method);
            HttpClientImpl.copyStreams(new ByteArrayInputStream(requestBody), connection.getOutputStream());
        } else {
            connection.setRequestMethod(this.method);
        }
    }
}
