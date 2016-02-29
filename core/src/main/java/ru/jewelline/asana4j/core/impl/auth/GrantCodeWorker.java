package ru.jewelline.asana4j.core.impl.auth;

import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.asana4j.auth.AuthenticationException;
import ru.jewelline.asana4j.auth.AuthenticationProperty;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityResponseReceiver;
import ru.jewelline.asana4j.utils.JsonOutputStream;
import ru.jewelline.asana4j.utils.StringUtils;
import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.HttpRequestFactory;
import ru.jewelline.request.http.NetworkException;
import ru.jewelline.request.http.UrlBuilder;

import java.io.ByteArrayInputStream;
import java.net.HttpURLConnection;

final class GrantCodeWorker extends AuthenticationWorker {

    private final HttpRequestFactory httpRequestFactory;

    public GrantCodeWorker(AuthenticationServiceImpl authenticationService, HttpRequestFactory httpRequestFactory) {
        super(authenticationService);
        this.httpRequestFactory = httpRequestFactory;
    }

    @Override
    void authenticate() throws AuthenticationException {
        try {
            ApiEntityResponseReceiver responseReceiver = this.httpRequestFactory.newRequest()
                    .setUrl(ACCESS_TOKEN_ENDPOINT)
                    .setHeader("Content-Type", "application/x-www-form-urlencoded")
                    .setEntity(new ByteArrayInputStream(getAccessTokenRequestBody()))
                    .buildAs(HttpMethod.POST)
                    .execute(new ApiEntityResponseReceiver());
            if (responseReceiver.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throwAuthenticationError(responseReceiver.getErrorStream());
            }
            JSONObject authResponse = responseReceiver.getResponseStream().asJson();

            getAuthenticationService().setAuthenticationProperty(AuthenticationProperty.ACCESS_TOKEN,
                    getStringPropertyFromJson(authResponse, "access_token"));
            getAuthenticationService().setAuthenticationProperty(AuthenticationProperty.REFRESH_TOKEN,
                    getStringPropertyFromJson(authResponse, "refresh_token"));
            getAuthenticationService().setAuthenticationProperty(AuthenticationProperty.TOKEN_TYPE,
                    getStringPropertyFromJson(authResponse, "token_type"));
            getAuthenticationService().setAuthenticationProperty(AuthenticationProperty.EXPIRES_IN,
                    getStringPropertyFromJson(authResponse, "expires_in"));
            getAuthenticationService().setAuthenticationProperty(AuthenticationProperty.AUTHORIZATION_ENDPOINT_STATE,
                    getStringPropertyFromJson(authResponse, "state"));

        } catch (NetworkException networkException) {
            throw new AuthenticationException(AuthenticationException.UNABLE_TO_AUTHENTICATE);
        }
    }

    private String getStringPropertyFromJson(JSONObject obj, String property) {
        String result = null;
        if (obj != null && property != null && obj.has(property)) {
            try {
                result = obj.get(property).toString();
            } catch (JSONException e) {
                // TODO log exception
            }
        }
        return result;
    }

    private void throwAuthenticationError(JsonOutputStream responseBody) {
        String message = "Failed to authenticate.";
        try {
            JSONObject json = responseBody.asJson();
            message = json.optString("error_description");
        } catch (NetworkException ne) {
            if (ne.getErrorCode() == NetworkException.UNREADABLE_RESPONSE) {
                message = "Failed to authenticate, unreadable server response.";
            }
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
        return tokenRequestBody.toString().getBytes(StringUtils.getCharset());
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
