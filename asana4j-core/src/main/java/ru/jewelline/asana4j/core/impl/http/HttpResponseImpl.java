package ru.jewelline.asana4j.core.impl.http;

import ru.jewelline.asana4j.http.HttpResponse;

import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpResponseImpl<O extends OutputStream> implements HttpResponse<O> {

    private final Map<String, List<String>> headers;
    private final O output;
    private int code;

    public HttpResponseImpl(O output) {
        this.output = output;
        this.headers = new HashMap<>();
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setHeader(String headerKey, List<String> headerValue){
        if (headerKey != null && headerValue != null) {
            this.headers.put(headerKey, Collections.unmodifiableList(headerValue));
        }
    }

    @Override
    public int code() {
        return this.code;
    }

    @Override
    public Map<String, List<String>> headers() {
        return Collections.unmodifiableMap(this.headers);
    }

    @Override
    public O output() {
        return this.output;
    }
}
