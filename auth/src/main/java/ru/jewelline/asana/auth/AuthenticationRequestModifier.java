package ru.jewelline.asana.auth;

import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.HttpRequestBuilder;
import ru.jewelline.request.http.modifiers.ModifiersChain;
import ru.jewelline.request.http.modifiers.RequestModifier;

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
    public void modify(HttpRequestBuilder requestBuilder, HttpMethod httpMethod, ModifiersChain modifiersChain) {
        if (this.authenticationService.isAuthenticated()) {
            requestBuilder.setHeader("Authorization", this.authenticationService.getHeader());
        }
        modifiersChain.next(requestBuilder, httpMethod);
    }
}
