package ru.jewelline.asana4j.core.impl.http;

import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.asana4j.http.HttpRequest;
import ru.jewelline.asana4j.http.HttpResponse;
import ru.jewelline.asana4j.http.NetworkException;

public class HttpResponseImpl implements HttpResponse {
    private final HttpRequest originalRequest;
    private int responseCode;
    private byte[] response;

    HttpResponseImpl(HttpRequest originalRequest) {
        this.originalRequest = originalRequest;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public void setResponseBody(byte[] response) {
        this.response = response;
    }

    @Override
    public int status() {
        return this.responseCode;
    }

    @Override
    public String asString() {
        return this.response != null ? new String(this.response) : null;
    }

    @Override
    public byte[] asByteArray(){
        return this.response;
    }

    @Override
    public JSONObject asJsonObject() {
        JSONObject result = null;
        String responseAsString = asString();
        if (responseAsString != null) {
            try {
                result = new JSONObject(responseAsString);
            } catch (JSONException e) {
                NetworkException networkException = new NetworkException(NetworkException.UNREDABLE_RESPONSE);
                networkException.setRequestUrl(this.originalRequest.getUrl());
                throw networkException;
            }
        }
        return result;
    }
}
