package ru.jewelline.asana4j.core.impl.api;

import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.api.ApiResponse;
import ru.jewelline.asana4j.core.impl.api.entity.ApiEntity;
import ru.jewelline.asana4j.http.HttpRequest;
import ru.jewelline.asana4j.http.HttpResponse;
import ru.jewelline.asana4j.utils.JsonOutputStream;
import ru.jewelline.asana4j.utils.ServiceLocator;

import java.net.HttpURLConnection;

public abstract class ApiClientImpl<AT, T extends ApiEntity<AT>> implements ApiEntityInstanceProvider<T> {

    private final ServiceLocator serviceLocator;

    public ApiClientImpl(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
    }

    protected ServiceLocator getServiceLocator(){
        return this.serviceLocator;
    }

    public ApiRequestBuilder<AT> newRequest(){
        return new ApiRequestBuilderImpl<>(getServiceLocator().getAuthenticationService(), getServiceLocator().getHttpClient(), this);
    }

    public abstract T newInstance();

    public ApiResponse<AT> wrapHttpResponse(HttpRequest<JsonOutputStream> httpRequest) {
        HttpResponse<JsonOutputStream> httpResponse = httpRequest.sendAndReadResponse(new JsonOutputStream());
        //TODO handle auth errors
        if (httpResponse.code() == HttpURLConnection.HTTP_FORBIDDEN ||
                httpResponse.code() == HttpURLConnection.HTTP_UNAUTHORIZED){
            //try to reconnect
            this.serviceLocator.getAuthenticationService().authenticate();
            httpResponse = httpRequest.sendAndReadResponse(new JsonOutputStream());

        }
        return new ApiResponseImpl<>(httpResponse, this);
    }
}
