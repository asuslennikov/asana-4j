package ru.jewelline.asana4j.core.impl.http;

import ru.jewelline.asana4j.http.HttpResponse;

import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpResponseImpl<O extends OutputStream> implements HttpResponse<O> {

    private final Map<String, String> headers;
    private final O output;
    private int code;

    public HttpResponseImpl(O output) {
        this.output = output;
        this.headers = new HashMap<>();
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setHeaders(String headerKey, String headerValue){
        this.headers.put(headerKey, headerValue);
    }

    @Override
    public int code() {
        return this.code;
    }

    @Override
    public Map<String, String> headers() {
        return Collections.unmodifiableMap(this.headers);
    }

    @Override
    public O output() {
        return this.output;
    }
}
