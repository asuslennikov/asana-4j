package ru.jewelline.request.http;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface HttpResponseReceiver {

    void setResponseCode(int code);

    void setResponseHeaders(Map<String, List<String>> headers);

    OutputStream getResponseStream();

    OutputStream getErrorStream();
}
