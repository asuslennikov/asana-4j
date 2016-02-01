package ru.jewelline.asana4j.auth;

public class AuthenticationException extends RuntimeException {
    public static final int WRONG_AUTHENTICATION_TYPE = 1;
    public static final int NOT_ENOUGH_INFO_FOR_AUTHENTICATION = 2;
    public static final int UNABLE_TO_AUTHENTICATE = 3;

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

    @Override
    public String getMessage() {
        return String.format("ATH%03d: ", this.errorCode) + super.getMessage();
    }
}
