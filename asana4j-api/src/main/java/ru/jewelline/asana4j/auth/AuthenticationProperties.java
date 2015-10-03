package ru.jewelline.asana4j.auth;

public final class AuthenticationProperties {
    /**
     * Each user has their own unique API key, which they can provide to applications to talk to Asana on their behalf.
     * The API uses the widely supported HTTP Basic Authentication mechanism to authenticate requests with the API key.
     */
    public static final String API_KEY = "api_key";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String EXPIRES_IN = "expires_in";
    public static final String TOKEN_TYPE = "token_type";
    public static final String CLIENT_ID = "client_id";
    public static final String CLIENT_SECRET = "client_secret";
    public static final String ACCESS_CODE = "code";
    /**
     * Required property. It holds the URL to redirect to on success or error. This must match the Redirect URL
     * specified in the application settings.
     */
    public static final String AUTHORIZATION_ENDPOINT_REDIRECT_URL = "redirect_uri";
    /**
     * Optional property. It represents state of the app, which will be returned verbatim in the response and can be used
     * to match the response up to a given request.
     */
    public static final String AUTHORIZATION_ENDPOINT_STATE = "state";

    private AuthenticationProperties() {
    }
}
