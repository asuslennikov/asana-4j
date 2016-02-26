package ru.jewelline.request.http;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class StreamBasedResponseReceiver implements HttpResponseReceiver {
    private final OutputStream responseStream;
    private final OutputStream errorStream;

    private int responseCode;
    private Map<String, List<String>> responseHeaders;

    public StreamBasedResponseReceiver(OutputStream responseStream) {
        this(responseStream, null);
    }

    public StreamBasedResponseReceiver(OutputStream responseStream, OutputStream errorStream) {
        this.responseStream = responseStream;
        this.errorStream = errorStream;
    }

    @Override
    public void setResponseCode(int code) {
        this.responseCode = code;
    }

    public int getResponseCode() {
        return responseCode;
    }

    @Override
    public void setResponseHeaders(Map<String, List<String>> headers) {
        this.responseHeaders = headers;
    }

    public Map<String, List<String>> getResponseHeaders() {
        return responseHeaders;
    }

    @Override
    public OutputStream getResponseStream() {
        return this.responseStream;
    }

    @Override
    public OutputStream getErrorStream() {
        return this.errorStream;
    }
}
