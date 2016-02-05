package ru.jewelline.request.http.impl;

import ru.jewelline.request.http.HttpResponse;

import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

class HttpResponseImpl<T extends OutputStream> implements HttpResponse<T> {

    private Map<String, List<String>> headers;
    private final T output;
    private int code;

    HttpResponseImpl(T output) {
        this.output = output;
    }

    void setCode(int code) {
        this.code = code;
    }

    /**
     * @param headers unmodifiable map which holds unmodifiable values
     */
    void setHeaders(Map<String, List<String>> headers) {
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
    public T payload() {
        return this.output;
    }
}
