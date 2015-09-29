package ru.jewelline.asana4j.core.impl.api;

import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.api.ApiResponse;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.core.impl.api.entity.ApiEntity;
import ru.jewelline.asana4j.http.HttpClient;
import ru.jewelline.asana4j.http.HttpRequest;
import ru.jewelline.asana4j.http.HttpResponse;
import ru.jewelline.asana4j.utils.JsonOutputStream;

import java.net.HttpURLConnection;

public abstract class ApiClientImpl<AT, T extends ApiEntity<AT>> implements ApiEntityInstanceProvider<T> {

    private final AuthenticationService authenticationService;
    private final HttpClient httpClient;

    public ApiClientImpl(AuthenticationService authenticationService, HttpClient httpClient) {
        this.authenticationService = authenticationService;
        this.httpClient = httpClient;
    }

    protected AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    protected HttpClient getHttpClient() {
        return httpClient;
    }

    public ApiRequestBuilder<AT> newRequest() {
        return new ApiRequestBuilderImpl<>(getAuthenticationService(), getHttpClient(), this);
    }

    public abstract T newInstance();

    public ApiResponse<AT> wrapHttpResponse(HttpRequest<JsonOutputStream> httpRequest) {
        HttpResponse<JsonOutputStream> httpResponse = httpRequest.sendAndReadResponse(new JsonOutputStream());
        //TODO handle auth errors
        if (httpResponse.code() == HttpURLConnection.HTTP_FORBIDDEN ||
                httpResponse.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
            //try to reconnect
            getAuthenticationService().authenticate();
            httpResponse = httpRequest.sendAndReadResponse(new JsonOutputStream());

        }
        return new ApiResponseImpl<>(httpResponse, this);
    }
}
