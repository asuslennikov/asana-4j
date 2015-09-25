package ru.jewelline.asana4j.core.impl.api;

import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.api.ApiResponse;
import ru.jewelline.asana4j.core.impl.api.entity.ApiEntity;
import ru.jewelline.asana4j.http.HttpResponse;
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
        return new ApiRequestBuilderImpl<>(getServiceLocator(), this);
    }

    public abstract T newInstance();

    ApiResponse<AT> wrapResponse(Function<HttpResponse> httpResponseProvider) {
        HttpResponse httpResponse = httpResponseProvider.run();
        //TODO handle auth errors
        if (httpResponse.status() == HttpURLConnection.HTTP_FORBIDDEN ||
                httpResponse.status() == HttpURLConnection.HTTP_UNAUTHORIZED){
            //try to reconnect
            this.serviceLocator.getAuthenticationService().authenticate();
            httpResponse = httpResponseProvider.run();

        }
        return new ApiResponseImpl<AT, T>(httpResponse, this);
    }
}
