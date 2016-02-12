package ru.jewelline.asana.auth.impl;

import ru.jewelline.asana.auth.AuthCodeGrantResponse;
import ru.jewelline.asana.auth.AuthenticationException;
import ru.jewelline.asana.auth.AuthenticationProperty;
import ru.jewelline.asana.common.EntityBasedOutputStream;
import ru.jewelline.asana.common.EntityContext;
import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.HttpRequestFactory;
import ru.jewelline.request.http.HttpResponse;
import ru.jewelline.request.http.NetworkException;
import ru.jewelline.request.http.UrlBuilder;

import java.net.HttpURLConnection;

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
            AuthCodeGrantResponse authResponse = this.httpRequestFactory.newRequest()
                    .path(ACCESS_TOKEN_ENDPOINT)
                    .setHeader("Content-Type", "application/x-www-form-urlencoded")
                    .setEntity(getAccessTokenRequestBody())
                    .buildAs(HttpMethod.POST)
                    .execute(this.entityContext.getReader(AuthCodeGrantResponse.class))
                    .payload()
                    .toEntity();
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
        } catch (NetworkException networkException) {
            throw new AuthenticationException(AuthenticationException.UNABLE_TO_AUTHENTICATE);
        }
    }

    private void throwAuthenticationError(HttpResponse<EntityBasedOutputStream<AuthCodeGrantResponse>> response) {
        String message = "Failed to authenticate.";
//        try {
//            JSONObject json = responseBody.asJson();
//            message = json.optString("error_description");
//        } catch (NetworkException ne) {
//            if (ne.getErrorCode() == NetworkException.UNREADABLE_RESPONSE) {
//                message = "Failed to authenticate, unreadable server response.";
//            }
//        }
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
                .addQueryParameter("client_id", clientId)
                .addQueryParameter("redirect_uri", redirectUrl)
                .addQueryParameter("response_type", "code");
        String appState = getAuthenticationService().getAuthenticationProperty(AuthenticationProperty.AUTHORIZATION_ENDPOINT_STATE);
        if (appState != null) {
            urlBuilder.addQueryParameter("state", appState);
        }
        return urlBuilder.build();
    }

    @Override
    void parseOAuthResponse(String data) {
        getAuthenticationService().setAuthenticationProperty(AuthenticationProperty.ACCESS_CODE,
                getProperty(data, "code="));
    }
}
