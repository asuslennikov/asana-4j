package ru.jewelline.request.http.impl;

import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.HttpRequest;
import ru.jewelline.request.http.HttpRequestBuilder;
import ru.jewelline.request.http.UrlProvider;
import ru.jewelline.request.http.modifiers.ModifiersChain;
import ru.jewelline.request.http.modifiers.RequestModifier;

final class HttRequestBuilderWithModifiiers extends HttpRequestBuilderImpl {
    private RequestModifier[] requestModifiers;

    HttRequestBuilderWithModifiiers(HttpRequestFactoryImpl httpRequestFactory, UrlProvider urlProvider) {
        super(httpRequestFactory, urlProvider);
    }

    HttpRequestBuilder withRequestModifiers(RequestModifier[] requestModifiers) {
        this.requestModifiers = requestModifiers;
        return this;
    }

    @Override
    public HttpRequest buildAs(HttpMethod method) {
        ModifiersChain modifiersChain = new ModifiersChain(this.requestModifiers);
        modifiersChain.next(this, method);
        HttpRequestBuilder requestBuilder = modifiersChain.getRequestBuilder();
        HttpMethod httpMethod = modifiersChain.getHttpMethod();
        return requestBuilder == this ? super.buildAs(httpMethod) : requestBuilder.buildAs(httpMethod);
    }
}
