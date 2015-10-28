package ru.jewelline.asana4j.http;

public class NetworkException extends RuntimeException {
    public static final int YOU_ARE_TRYING_TO_SEND_EMPTY_REQUEST = 1;
    public static final int UNREADABLE_RESPONSE = 1 << 1;
    public static final int MALFORMED_URL = 1 << 2;
    public static final int CONNECTION_TIMED_OUT = 1 << 3;
    public static final int COMMUNICATION_FAILED = 1 << 4; // generally for IO Exception

    private final int errorCode;
    private String requestUrl;

    public NetworkException(int errorCode){
        this.errorCode = errorCode;
    }

    public NetworkException(int errorCode, String message){
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
