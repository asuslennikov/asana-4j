package ru.jewelline.request.http.impl;

import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.HttpRequest;
import ru.jewelline.request.http.HttpRequestBuilder;
import ru.jewelline.request.http.NetworkException;
import ru.jewelline.request.http.modifiers.ModifiersChain;
import ru.jewelline.request.http.modifiers.RequestModifier;

final class HttpRequestBuilderWithModifiers extends HttpRequestBuilderImpl {
    private RequestModifier[] requestModifiers;

    HttpRequestBuilderWithModifiers(HttpRequestFactoryImpl httpRequestFactory) {
        super(httpRequestFactory);
    }

    HttpRequestBuilder withRequestModifiers(RequestModifier[] requestModifiers) {
        this.requestModifiers = requestModifiers;
        return this;
    }

    @Override
    public HttpRequest buildAs(HttpMethod method) {
        if (method == null) {
            throw new NetworkException(NetworkException.MALFORMED_URL, "You must specify a request method");
        }
        ModifiersChain modifiersChain = new ModifiersChain(this.requestModifiers);
        modifiersChain.next(this, method);
        HttpRequestBuilder requestBuilder = modifiersChain.getRequestBuilder();
        HttpMethod httpMethod = modifiersChain.getHttpMethod();
        return requestBuilder == this ? super.buildAs(httpMethod) : requestBuilder.buildAs(httpMethod);
    }
}
