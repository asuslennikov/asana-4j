package ru.jewelline.asana4j.auth;

public class AuthenticationException extends RuntimeException {
    public static final int WRONG_AUTHENTICATION_TYPE = 1 << 0;
    public static final int NOT_ENOUGH_INFO_FOR_AUTHENTICATION = 1 << 1;
    public static final int UNABLE_TO_AUTHENTICATE = 1 << 2;

    private final int errorCode;

    public AuthenticationException(int errorCode){
        this.errorCode = errorCode;
    }

    public AuthenticationException(int errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
