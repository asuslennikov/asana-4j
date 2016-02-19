package ru.jewelline.asana.auth;

public final class AuthCodeGrantErrorResponse {
    private final String message;

    public AuthCodeGrantErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
