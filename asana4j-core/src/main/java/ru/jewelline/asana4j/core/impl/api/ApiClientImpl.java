package ru.jewelline.asana4j.core.impl.api;

import ru.jewelline.asana4j.api.ApiRequest;
import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.api.options.RequestOption;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.http.HttpClient;
import ru.jewelline.asana4j.http.HttpMethod;

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
        return newRequest((RequestOption[]) null);
    }

    protected ApiRequestBuilder<AT> newRequest(RequestOption... requestOptions) {
        return new ApiRequestWithOptionsBuilder<>(getAuthenticationService(), getHttpClient(), this).withRequestOptions(requestOptions);
    }

    public abstract T newInstance();

    private static final class ApiRequestWithOptionsBuilder<AT, T extends ApiEntity<AT>> extends ApiRequestBuilderImpl<AT, T> {

        private RequestOption[] requestOptions;

        public ApiRequestWithOptionsBuilder(AuthenticationService authenticationService, HttpClient httpClient, ApiEntityInstanceProvider<T> apiInstanceProvider) {
            super(authenticationService, httpClient, apiInstanceProvider);
        }

        protected ApiRequestBuilder<AT> withRequestOptions(RequestOption[] requestOptions) {
            this.requestOptions = requestOptions;
            return this;
        }

        @Override
        public ApiRequest<AT> buildAs(HttpMethod method) {
            ApiRequestBuilder<AT> requestBuilder = this;
            if (this.requestOptions != null) {
                for (RequestOption requestOption : requestOptions) {
                    requestBuilder = requestOption.applyTo(requestBuilder, method);
                }
            }
            return requestBuilder == this ? super.buildAs(method) : requestBuilder.buildAs(method);
        }
    }
}
