package ru.jewelline.request.api.impl;

import ru.jewelline.asana4j.api.modifiers.PrettyJsonResponseModifier;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.core.impl.api.clients.modifiers.AuthenticationRequestModifier;
import ru.jewelline.asana4j.core.impl.api.clients.modifiers.DataRootRequestModifier;
import ru.jewelline.asana4j.core.impl.api.clients.modifiers.JsonContentTypeModifier;
import ru.jewelline.asana4j.core.impl.api.clients.modifiers.LoggingRequestModifier;
import ru.jewelline.request.api.ApiRequest;
import ru.jewelline.request.api.ApiRequestBuilder;
import ru.jewelline.request.api.modifiers.ModifiersChain;
import ru.jewelline.request.api.modifiers.RequestModifier;
import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.HttpRequestFactory;

import java.util.Arrays;

class ApiRequestWithModifiersBuilder extends ApiRequestBuilderImpl {
    private static final DataRootRequestModifier DATA_ROOT_REQUEST_MODIFIER = new DataRootRequestModifier();
    private static final JsonContentTypeModifier JSON_CONTENT_TYPE_MODIFIER = new JsonContentTypeModifier();
    private static final LoggingRequestModifier LOGGING_REQUEST_MODIFIER = new LoggingRequestModifier();
    private static final PrettyJsonResponseModifier PRETTY_JSON_RESPONSE_MODIFIER = new PrettyJsonResponseModifier();

    private final AuthenticationService authenticationService;
    private RequestModifier[] requestModifiers;

    public ApiRequestWithModifiersBuilder(HttpRequestFactory httpRequestFactory, AuthenticationService authenticationService) {
        super(httpRequestFactory);
        this.authenticationService = authenticationService;
    }

    public ApiRequestBuilder withRequestModifiers(RequestModifier[] requestModifiers) {
        this.requestModifiers = requestModifiers;
        return this;
    }

    @Override
    public ApiRequest buildAs(HttpMethod method) {
        ModifiersChain modifiersChain = new ModifiersChain(getRequestModifiers());
        modifiersChain.next(this, method);
        ApiRequestBuilder requestBuilder = modifiersChain.getRequestBuilder();
        HttpMethod httpMethod = modifiersChain.getHttpMethod();
        return requestBuilder == this ? super.buildAs(httpMethod) : requestBuilder.buildAs(httpMethod);
    }

    private RequestModifier[] getRequestModifiers() {
        RequestModifier[] mandatoryRequestModifiers = new RequestModifier[]{
                new AuthenticationRequestModifier(this.authenticationService),
                DATA_ROOT_REQUEST_MODIFIER,
                JSON_CONTENT_TYPE_MODIFIER,
                LOGGING_REQUEST_MODIFIER,
                PRETTY_JSON_RESPONSE_MODIFIER
        };
        if (this.requestModifiers == null || this.requestModifiers.length == 0) {
            return mandatoryRequestModifiers;
        }
        RequestModifier[] modifiers = Arrays.copyOf(this.requestModifiers, this.requestModifiers.length + mandatoryRequestModifiers.length);
        System.arraycopy(mandatoryRequestModifiers, 0, modifiers, this.requestModifiers.length, mandatoryRequestModifiers.length);
        return modifiers;
    }
}
