package ru.jewelline.asana4j.core.impl.http;

import ru.jewelline.asana4j.http.HttpMethod;
import ru.jewelline.asana4j.http.HttpRequest;
import ru.jewelline.asana4j.http.NetworkException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public enum HttpMethodSettings {
    GET(HttpMethod.GET) {
        @Override
        public void apply(HttpURLConnection connection, HttpRequest httpRequest) {
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
        // we assume that httpRequest and connection are never null
        InputStream entity = httpRequest.getEntity();
        if (entity != null) {
            connection.setDoOutput(true);
            connection.setRequestMethod(this.httpMethod.method());
            HttpClientImpl.copyStreams(entity, connection.getOutputStream());
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
