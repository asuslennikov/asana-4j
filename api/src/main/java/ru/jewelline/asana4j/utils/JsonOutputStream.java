package ru.jewelline.asana4j.utils;

import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.request.http.NetworkException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class JsonOutputStream extends OutputStream {
    private ByteArrayOutputStream out;

    public JsonOutputStream() {
        super();
        this.out = new ByteArrayOutputStream();
    }

    @Override
    public void write(int b) throws IOException {
        this.out.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        this.out.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        this.out.write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
        this.out.flush();
    }

    @Override
    public void close() throws IOException {
        this.out.close();
    }

    public String asString() {
        return new String(this.out.toByteArray(), StringUtils.getCharset());
    }

    public byte[] asByteArray() {
        return this.out.toByteArray();
    }

    public JSONObject asJson() {
        JSONObject result = null;
        String responseAsString = asString();
        // System.out.println("=======================\nResponse: " + responseAsString);
        if (responseAsString != null) {
            try {
                result = new JSONObject(responseAsString);
            } catch (JSONException e) {
                throw new NetworkException(NetworkException.UNREADABLE_RESPONSE);
            }
        }
        return result;
    }

}
