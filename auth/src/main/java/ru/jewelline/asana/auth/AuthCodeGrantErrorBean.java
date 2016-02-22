package ru.jewelline.asana.auth;

public final class AuthCodeGrantErrorBean {
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
