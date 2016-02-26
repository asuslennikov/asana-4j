package ru.jewelline.asana4j.core.impl.auth;

import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.asana4j.auth.AuthenticationException;
import ru.jewelline.asana4j.auth.AuthenticationProperties;
import ru.jewelline.asana4j.http.HttpClient;
import ru.jewelline.asana4j.http.HttpResponse;
import ru.jewelline.asana4j.http.NetworkException;
import ru.jewelline.asana4j.utils.JsonOutputStream;
import ru.jewelline.asana4j.utils.URLCreator;
import ru.jewelline.request.http.HttpMethod;

import java.net.HttpURLConnection;

final class GrantCodeWorker extends AuthenticationWorker {

    private final HttpClient httpClient;
    private final URLCreator urlCreator;

    public GrantCodeWorker(AuthenticationServiceImpl authenticationService, HttpClient httpClient, URLCreator urlCreator) {
        super(authenticationService);
        this.httpClient = httpClient;
        this.urlCreator = urlCreator;
    }

    @Override
    void authenticate() throws AuthenticationException {
        try {
            JsonOutputStream responseBody = new JsonOutputStream();
            HttpResponse response = this.httpClient.newRequest()
                    .path(ACCESS_TOKEN_ENDPOINT)
                    .setHeader("Content-Type", "application/x-www-form-urlencoded")
                    .entity(getAccessTokenRequestBody())
                    .buildAs(HttpMethod.POST)
                    .sendAndReadResponse(responseBody);
            if (response.code() != HttpURLConnection.HTTP_OK) {
                throwAuthenticationError(responseBody);
            }
            JSONObject authResponse = responseBody.asJson();

            getAuthenticationService().setAuthenticationProperty(AuthenticationProperties.ACCESS_TOKEN,
                    getStringPropertyFromJson(authResponse, "access_token"));
            getAuthenticationService().setAuthenticationProperty(AuthenticationProperties.REFRESH_TOKEN,
                    getStringPropertyFromJson(authResponse, "refresh_token"));
            getAuthenticationService().setAuthenticationProperty(AuthenticationProperties.TOKEN_TYPE,
                    getStringPropertyFromJson(authResponse, "token_type"));
            getAuthenticationService().setAuthenticationProperty(AuthenticationProperties.EXPIRES_IN,
                    getStringPropertyFromJson(authResponse, "expires_in"));
            getAuthenticationService().setAuthenticationProperty(AuthenticationProperties.AUTHORIZATION_ENDPOINT_STATE,
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
        String refreshToken = getAuthenticationService().getAuthenticationProperty(AuthenticationProperties.REFRESH_TOKEN);
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
        URLCreator.Builder urlBuilder = this.urlCreator.builder()
                .path(USER_OAUTH_ENDPOINT)
                .addQueryParameter("client_id", clientId)
                .addQueryParameter("redirect_uri", redirectUrl)
                .addQueryParameter("response_type", "code");
        String appState = getAuthenticationService().getAuthenticationProperty(AuthenticationProperties.AUTHORIZATION_ENDPOINT_STATE);
        if (appState != null) {
            urlBuilder.addQueryParameter("state", appState);
        }
        return urlBuilder.build();
    }

    @Override
    void parseOAuthResponse(String data) {
        getAuthenticationService().setAuthenticationProperty(AuthenticationProperties.ACCESS_CODE,
                getProperty(data, "code="));
    }
}
