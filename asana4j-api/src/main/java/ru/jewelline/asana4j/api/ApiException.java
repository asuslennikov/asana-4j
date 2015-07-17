package ru.jewelline.asana4j.api;

public class ApiException extends RuntimeException {
    public static final int INCORRECT_RESPONSE_FORMAT = 1 << 0;
    public static final int INCORRECT_RESPONSE_FIELD_FORMAT = 1 << 1;

    private final int errorCode;
    private String requestUrl;

    public ApiException(int errorCode){
        this.errorCode = errorCode;
    }

    public ApiException(int errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestUrl() {
        return requestUrl;
    }
}
