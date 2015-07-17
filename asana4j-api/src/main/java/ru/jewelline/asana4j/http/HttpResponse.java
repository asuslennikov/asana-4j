package ru.jewelline.asana4j.http;

import org.json.JSONObject;

// TODO Javadoc
public interface HttpResponse {
    int status();
    String asString();
    byte[] asByteArray();
    JSONObject asJsonObject();
}
