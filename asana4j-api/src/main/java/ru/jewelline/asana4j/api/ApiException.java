package ru.jewelline.asana4j.api;

public class ApiException extends RuntimeException {
    public static final int INCORRECT_RESPONSE_FORMAT = 1;
    public static final int INCORRECT_RESPONSE_FIELD_FORMAT = 2;
    public static final int API_ENTITY_SERIALIZATION_FAIL = 3;
    public static final int API_ENTITY_INSTANTIATION_FAIL = 4;

    private final int errorCode;

    public ApiException(int errorCode) {
        this.errorCode = errorCode;
    }

    public ApiException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return String.format("AEX%03d: ", this.errorCode) + super.getMessage();
    }
}
