package ru.jewelline.asana4j.core.impl.api;

import org.json.JSONObject;

import java.io.Serializable;

public interface ApiEntity<A> extends Serializable {
    /**
     * Converts json response from server to java object
     * @param object json response from server
     * @return the same instance with filled parameters or <code>null</code> if the passed object was null
     */
    A fromJson(JSONObject object);
}
