package ru.jewelline.asana4j.core.impl.api.clients;

import ru.jewelline.asana4j.api.ApiRequest;
import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.api.clients.modifiers.ModifiersChain;
import ru.jewelline.asana4j.api.clients.modifiers.PrettyJsonResponseModifier;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.core.impl.api.ApiRequestBuilderImpl;
import ru.jewelline.asana4j.core.impl.api.clients.modifiers.AuthenticationRequestModifier;
import ru.jewelline.asana4j.core.impl.api.clients.modifiers.DataRootRequestModifier;
import ru.jewelline.asana4j.core.impl.api.clients.modifiers.JsonContentTypeModifier;
import ru.jewelline.asana4j.core.impl.api.clients.modifiers.LoggingRequestModifier;
import ru.jewelline.asana4j.http.HttpClient;
import ru.jewelline.asana4j.http.HttpMethod;

import java.util.Arrays;

public class ApiRequestWithModifiersBuilder extends ApiRequestBuilderImpl {
    private static final DataRootRequestModifier DATA_ROOT_REQUEST_MODIFIER = new DataRootRequestModifier();
    private static final JsonContentTypeModifier JSON_CONTENT_TYPE_MODIFIER = new JsonContentTypeModifier();
    private static final LoggingRequestModifier LOGGING_REQUEST_MODIFIER = new LoggingRequestModifier();
    private static final PrettyJsonResponseModifier PRETTY_JSON_RESPONSE_MODIFIER = new PrettyJsonResponseModifier();

    private final AuthenticationService authenticationService;
    private RequestModifier[] requestModifiers;

    public ApiRequestWithModifiersBuilder(AuthenticationService authenticationService, HttpClient httpClient) {
        super(httpClient);
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
