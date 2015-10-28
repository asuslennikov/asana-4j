package ru.jewelline.asana4j.core.impl.api;

import ru.jewelline.asana4j.api.ApiRequest;
import ru.jewelline.asana4j.api.ApiResponse;
import ru.jewelline.asana4j.core.impl.api.entity.ApiEntityInstanceProvider;
import ru.jewelline.asana4j.http.HttpRequest;
import ru.jewelline.asana4j.http.HttpResponse;
import ru.jewelline.asana4j.utils.JsonOutputStream;

import java.io.InputStream;
import java.util.Map;

public class ApiRequestImpl<AT, T extends ApiEntity<AT>> implements ApiRequest<AT> {
    private final HttpRequest httpRequest;
    private final ApiEntityInstanceProvider<T> instanceProvider;

    public ApiRequestImpl(HttpRequest httpRequest, ApiEntityInstanceProvider<T> apiInstanceProvider) {
        this.httpRequest = httpRequest;
        this.instanceProvider = apiInstanceProvider;
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
    public InputStream getEntity() {
        return this.httpRequest.getEntity();
    }

    @Override
    public ApiResponse<AT> execute() {
        HttpResponse<JsonOutputStream> httpResponse = this.httpRequest.sendAndReadResponse(new JsonOutputStream());
        return new ApiResponseImpl<>(httpResponse, this.instanceProvider);
    }
}
