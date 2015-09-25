package ru.jewelline.asana4j.example;

import ru.jewelline.asana4j.utils.PreferencesService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryPreferenceService implements PreferencesService {
    private Map<Object, Object> store = new ConcurrentHashMap<>();
    @Override
    public Integer getInteger(String key) {
        return (Integer) store.get(key);
    }

    @Override
    public Long getLong(String key) {
        return (Long) store.get(key);
    }

    @Override
    public String getString(String key) {
        return (String) store.get(key);
    }

    @Override
    public Integer getInteger(String key, Integer defaultValue) {
        Integer value = getInteger(key);
        return value != null ? value : defaultValue;
    }

    @Override
    public Long getLong(String key, Long defaultValue) {
        Long value = getLong(key);
        return value != null ? value : defaultValue;
    }

    @Override
    public String getString(String key, String defaultValue) {
        String value = getString(key);
        return value != null ? value : defaultValue;
    }

    @Override
    public void setInteger(String key, Integer value) {
        store.put(key, value);
    }

    @Override
    public void setLong(String key, Long value) {
        store.put(key, value);
    }

    @Override
    public void setString(String key, String value) {
        store.put(key, value);
    }
}
