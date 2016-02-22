package ru.jewelline.asana.core;

public final class AsanaException extends RuntimeException {
    public static final int DESERIALIZATION_ERROR = 1;

    private final int errorCode;

    public AsanaException(int errorCode) {
        this.errorCode = errorCode;
    }

    public AsanaException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return String.format("AE%03d: ", getErrorCode()) + super.getMessage();
    }
}
