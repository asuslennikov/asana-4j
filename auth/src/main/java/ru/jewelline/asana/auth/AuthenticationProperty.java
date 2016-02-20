package ru.jewelline.asana.auth;

public enum AuthenticationProperty {
    /**
     * Each user has their own unique API key, which they can provide to applications to talk to Asana on their behalf.
     * The API uses the widely supported HTTP Basic Authentication mechanism to authenticate requests with the API key.
     */
    API_KEY("api_key"),
    ACCESS_TOKEN("access_token"),
    REFRESH_TOKEN("refresh_token"),
    EXPIRES_IN("expires_in"),
    TOKEN_TYPE("token_type"),
    CLIENT_ID("client_id"),
    CLIENT_SECRET("client_secret"),
    ACCESS_CODE("code"),
    /**
     * Required property. It holds the URL to redirect to on success or error. This must match the Redirect URL
     * specified in the application settings.
     */
    AUTHORIZATION_ENDPOINT_REDIRECT_URL("redirect_uri"),
    /**
     * Optional property. It represents state of the app, which will be returned verbatim in the response and can be used
     * to match the response up to a given request.
     */
    AUTHORIZATION_ENDPOINT_STATE("state"),;

    private final String propertyName;

    AuthenticationProperty(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return this.propertyName;
    }
}
