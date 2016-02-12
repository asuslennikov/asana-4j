package ru.jewelline.request.api.impl;

import ru.jewelline.asana4j.utils.JsonOutputStream;
import ru.jewelline.request.api.ApiRequest;
import ru.jewelline.request.api.ApiResponse;
import ru.jewelline.request.http.HttpRequest;
import ru.jewelline.request.http.HttpResponse;

import java.io.InputStream;
import java.util.Map;

class ApiRequestImpl implements ApiRequest {
    private final HttpRequest httpRequest;

    public ApiRequestImpl(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
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
    public ApiResponse execute() {
        HttpResponse<JsonOutputStream> httpResponse = this.httpRequest.sendAndReadResponse(new JsonOutputStream());
        return new ApiResponseImpl(httpResponse);
    }
}
