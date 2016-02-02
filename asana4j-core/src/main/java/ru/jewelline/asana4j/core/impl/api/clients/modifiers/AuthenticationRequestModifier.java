package ru.jewelline.asana4j.core.impl.api.clients.modifiers;

import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.request.api.ApiRequestBuilder;
import ru.jewelline.request.api.modifiers.ModifiersChain;
import ru.jewelline.request.api.modifiers.RequestModifier;
import ru.jewelline.request.http.HttpMethod;

public class AuthenticationRequestModifier implements RequestModifier {
    private final AuthenticationService authenticationService;

    public AuthenticationRequestModifier(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public int priority() {
        return RequestModifier.PRIORITY_API_OPTIONS;
    }

    @Override
    public void modify(ApiRequestBuilder requestBuilder, HttpMethod httpMethod, ModifiersChain modifiersChain) {
        if (this.authenticationService.isAuthenticated()) {
            requestBuilder.setHeader("Authorization", this.authenticationService.getHeader());
        }
        modifiersChain.next(requestBuilder, httpMethod);
    }
}
