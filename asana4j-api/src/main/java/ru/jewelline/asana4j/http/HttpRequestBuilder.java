package ru.jewelline.asana4j.http;

import org.json.JSONObject;

public interface HttpRequestBuilder {
    HttpRequestBuilder path(String baseUrl);
    HttpRequestBuilder addQueryParameter(String parameterKey, String parameterValue);
    HttpRequestBuilder addHeaders(String headerKey, String headerValue);
    HttpRequestBuilder entity(byte[] requestBody);
    HttpRequestBuilder entity(JSONObject object);
    HttpRequest build();
}
