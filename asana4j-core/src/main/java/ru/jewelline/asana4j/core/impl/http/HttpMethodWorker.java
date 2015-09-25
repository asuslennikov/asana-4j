package ru.jewelline.asana4j.core.impl.http;

import ru.jewelline.asana4j.http.HttpMethod;
import ru.jewelline.asana4j.http.NetworkException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public enum HttpMethodWorker {
    GET(HttpMethod.GET) {
        @Override
        public void process(HttpRequestImpl httpRequest, HttpURLConnection connection) {
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

    public void process(HttpRequestImpl httpRequest, HttpURLConnection connection) throws IOException {
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

    public static HttpMethodWorker getWorker(HttpMethod httpMethod){
        for (HttpMethodWorker worker : HttpMethodWorker.values()) {
            if (worker.httpMethod.equals(httpMethod)){
                return worker;
            }
        }
        /*
         This should never happen because we have a null check here:
         {@see HttpRequestBuilderImpl#buildAs(HttpMethod)}
         but let's process such case
        */
        throw new NetworkException(NetworkException.MALFORMED_URL,
                "You are trying to send request with unknown request method.");
    }
}
