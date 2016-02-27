package ru.jewelline.asana4j.examples;

import ru.jewelline.asana4j.utils.PropertiesStore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryPreferenceService implements PropertiesStore {
    private Map<Object, Object> store = new ConcurrentHashMap<>();

    @Override
    public String getString(String key) {
        return (String) store.get(key);
    }

    @Override
    public void setString(String key, String value) {
        putWithNullCheck(key, value);
    }

    private void putWithNullCheck(String key, Object value) {
        if (key != null && value != null) {
            store.put(key, value);
        } else if (key != null) {
            store.remove(key);
        }
    }
}
