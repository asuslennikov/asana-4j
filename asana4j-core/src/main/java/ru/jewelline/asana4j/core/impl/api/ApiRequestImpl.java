package ru.jewelline.asana4j.core.impl.api;

import org.json.JSONObject;
import ru.jewelline.asana4j.api.ApiRequest;
import ru.jewelline.asana4j.api.ApiResponse;
import ru.jewelline.asana4j.http.HttpRequest;
import ru.jewelline.asana4j.utils.JsonOutputStream;

import java.util.Map;

public class ApiRequestImpl<T> implements ApiRequest<T> {
    private final HttpRequest<JsonOutputStream> httpRequest;
    private final ApiClientImpl<T, ?> apiClient;

    public ApiRequestImpl(HttpRequest<JsonOutputStream> httpRequest, ApiClientImpl<T, ?> apiClient) {
        this.httpRequest = httpRequest;
        this.apiClient = apiClient;
    }

    @Override
    public String getUrl() {
        return this.httpRequest.getUrl();
    }

    @Override
    public Map<String, String> getHeaders() {
        return this.httpRequest.getHeaders();
    }

    @Override
    public JSONObject getRequestEntity() {
        return null; //TODO inplement POST,PUT,DELETE
    }

    @Override
    public ApiResponse<T> execute() {
        return this.apiClient.wrapHttpResponse(this.httpRequest);
    }
}
