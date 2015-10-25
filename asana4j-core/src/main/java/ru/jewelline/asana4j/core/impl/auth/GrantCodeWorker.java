package ru.jewelline.asana4j.core.impl.auth;

import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.asana4j.auth.AuthenticationException;
import ru.jewelline.asana4j.auth.AuthenticationProperties;
import ru.jewelline.asana4j.http.HttpClient;
import ru.jewelline.asana4j.http.HttpMethod;
import ru.jewelline.asana4j.http.HttpResponse;
import ru.jewelline.asana4j.http.NetworkException;
import ru.jewelline.asana4j.utils.JsonOutputStream;
import ru.jewelline.asana4j.utils.ServiceLocator;
import ru.jewelline.asana4j.utils.URLBuilder;

import java.net.HttpURLConnection;

public class GrantCodeWorker extends AuthenticationWorker {

    public GrantCodeWorker(ServiceLocator serviceLocator) {
        super(serviceLocator);
    }

    @Override
    void authenticate() throws AuthenticationException {
        HttpClient httpClient = getServiceLocator().getHttpClient();
        try {
            JsonOutputStream responseBody = new JsonOutputStream();
            HttpResponse response = httpClient.newRequest()
                    .path(ACCESS_TOKEN_ENDPOINT)
                    .setHeader("Content-Type", "application/x-www-form-urlencoded")
                    .entity(getAccessTokenRequestBody())
                    .buildAs(HttpMethod.POST)
                    .sendAndReadResponse(responseBody);
            if (response.code() != HttpURLConnection.HTTP_OK) {
                throw new AuthenticationException(AuthenticationException.UNABLE_TO_AUTHENTICATE);
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

        } catch (NetworkException networkExcepton) {
            throw new AuthenticationException(AuthenticationException.UNABLE_TO_AUTHENTICATE);
        }
    }

    private String getStringPropertyFromJson(JSONObject obj, String property) {
        String result = null;
        if (obj != null && property != null && obj.has(property)) {
            try {
                result = obj.getString(property);
            } catch (JSONException e) {
            }
        }
        return result;
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
        URLBuilder urlBuilder = getServiceLocator().getUrlBuilder()
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
