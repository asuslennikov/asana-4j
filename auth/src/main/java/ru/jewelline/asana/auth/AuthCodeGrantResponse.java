package ru.jewelline.asana.auth;

public interface AuthCodeGrantResponse {
    String getAccessToken();

    String getRefreshToken();

    String getTokenType();

    String getExpiresIn();

    String getState();
}
