package ru.jewelline.request.http;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public final class NullResponseReceiver implements HttpResponseReceiver {
    private static NullResponseReceiver INSTANCE = new NullResponseReceiver();

    public static NullResponseReceiver getInstance() {
        return INSTANCE;
    }

    private NullResponseReceiver() {
    }

    @Override
    public void setResponseCode(int code) {
        // do nothing
    }

    @Override
    public void setResponseHeaders(Map<String, List<String>> headers) {
        // do nothing
    }

    @Override
    public OutputStream getResponseStream() {
        return null;
    }

    @Override
    public OutputStream getErrorStream() {
        return null;
    }
}
