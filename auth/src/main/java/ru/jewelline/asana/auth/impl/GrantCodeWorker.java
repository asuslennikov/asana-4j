package ru.jewelline.asana.auth.impl;

import ru.jewelline.asana.auth.AuthCodeGrantErrorResponse;
import ru.jewelline.asana.auth.AuthCodeGrantResponse;
import ru.jewelline.asana.auth.AuthenticationException;
import ru.jewelline.asana.auth.AuthenticationProperty;
import ru.jewelline.asana.core.EntityContext;
import ru.jewelline.asana.core.EntityWithErrorResponseReader;
import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.HttpRequestFactory;
import ru.jewelline.request.http.NetworkException;
import ru.jewelline.request.http.UrlBuilder;

import java.io.ByteArrayInputStream;

final class GrantCodeWorker extends AuthenticationWorker {

    private final HttpRequestFactory httpRequestFactory;
    private final EntityContext entityContext;

    public GrantCodeWorker(AuthenticationServiceImpl authenticationService, HttpRequestFactory httpRequestFactory, EntityContext entityContext) {
        super(authenticationService);
        this.httpRequestFactory = httpRequestFactory;
        this.entityContext = entityContext;
    }

    @Override
    void authenticate() throws AuthenticationException {
        try {
            EntityWithErrorResponseReader<AuthCodeGrantResponse, AuthCodeGrantErrorResponse> responseReceiver =
                    this.entityContext.getReader(AuthCodeGrantResponse.class, AuthCodeGrantErrorResponse.class);
            this.httpRequestFactory.newRequest()
                    .setUrl(ACCESS_TOKEN_ENDPOINT)
                    .setHeader("Content-Type", "application/x-www-form-urlencoded")
                    .setEntity(new ByteArrayInputStream(getAccessTokenRequestBody()))
                    .buildAs(HttpMethod.POST)
                    .execute(responseReceiver);
            if (!responseReceiver.hasError()) {
                AuthCodeGrantResponse authResponse = responseReceiver.toEntity();
                getAuthenticationService().setAuthenticationProperty(AuthenticationProperty.ACCESS_TOKEN,
                        authResponse.getAccessToken());
                getAuthenticationService().setAuthenticationProperty(AuthenticationProperty.REFRESH_TOKEN,
                        authResponse.getRefreshToken());
                getAuthenticationService().setAuthenticationProperty(AuthenticationProperty.TOKEN_TYPE,
                        authResponse.getTokenType());
                getAuthenticationService().setAuthenticationProperty(AuthenticationProperty.EXPIRES_IN,
                        authResponse.getExpiresIn());
                getAuthenticationService().setAuthenticationProperty(AuthenticationProperty.AUTHORIZATION_ENDPOINT_STATE,
                        authResponse.getState());
            } else {
                throwAuthenticationError(responseReceiver.getError());
            }
        } catch (NetworkException networkException) {
            throw new AuthenticationException(AuthenticationException.UNABLE_TO_AUTHENTICATE);
        }
    }

    private void throwAuthenticationError(AuthCodeGrantErrorResponse errorResponse) {
        String message = "Failed to authenticate.";
        if (errorResponse != null && errorResponse.getMessage() != null) {
            message = errorResponse.getMessage();
        }
        throw new AuthenticationException(AuthenticationException.UNABLE_TO_AUTHENTICATE, message);
    }

    private byte[] getAccessTokenRequestBody() {
        StringBuilder tokenRequestBody = new StringBuilder();
        String redirectUrl = getRedirectUrlOrTrowException();
        String clientId = getClientIdOrThrowException();
        String clientSecret = getClientSecretOrThrowException();
        String refreshToken = getAuthenticationService().getAuthenticationProperty(AuthenticationProperty.REFRESH_TOKEN);
        if (refreshToken != null) {
            tokenRequestBody.append("grant_type=refresh_token&refresh_token=").append(refreshToken);
        } else {
            String accessCode = getAccessCodeOrThrowException();
            tokenRequestBody.append("grant_type=authorization_code&code=").append(accessCode);
        }
        tokenRequestBody.append("&client_id=").append(clientId)
                .append("&client_secret=").append(clientSecret)
                .append("&redirect_uri=").append(redirectUrl);
        return tokenRequestBody.toString().getBytes();
    }

    @Override
    String getOAuthUrl() {
        String clientId = getClientIdOrThrowException();
        String redirectUrl = getRedirectUrlOrTrowException();
        UrlBuilder urlBuilder = this.httpRequestFactory.urlBuilder()
                .path(USER_OAUTH_ENDPOINT)
                .setQueryParameter("client_id", clientId)
                .setQueryParameter("redirect_uri", redirectUrl)
                .setQueryParameter("response_type", "code");
        String appState = getAuthenticationService().getAuthenticationProperty(AuthenticationProperty.AUTHORIZATION_ENDPOINT_STATE);
        if (appState != null) {
            urlBuilder.setQueryParameter("state", appState);
        }
        return urlBuilder.build();
    }

    @Override
    void parseOAuthResponse(String data) {
        getAuthenticationService().setAuthenticationProperty(AuthenticationProperty.ACCESS_CODE,
                getProperty(data, "code="));
    }
}
