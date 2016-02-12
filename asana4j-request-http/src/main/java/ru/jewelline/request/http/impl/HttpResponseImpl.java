package ru.jewelline.request.http.impl;

import ru.jewelline.request.http.HttpResponse;

import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HttpResponseImpl<O extends OutputStream> implements HttpResponse<O> {

    private Map<String, List<String>> headers;
    private final O output;
    private int code;

    public HttpResponseImpl(O output) {
        this.output = output;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @param headers unmodifiable map which holds unmodifiable values
     */
    public void setHeaders(Map<String, List<String>> headers){
        this.headers = headers;
    }

    @Override
    public int code() {
        return this.code;
    }

    @Override
    public Map<String, List<String>> headers() {
        return this.headers != null ? this.headers : Collections.<String, List<String>>emptyMap();
    }

    @Override
    public O output() {
        return this.output;
    }
}
