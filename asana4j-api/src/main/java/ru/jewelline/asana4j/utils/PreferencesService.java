package ru.jewelline.asana4j.utils;

public interface PreferencesService {
    String NETWORK_MAX_RETRY_COUNT = "network_max_retry_count";
    String NETWORK_CONNECTION_TIMEOUT = "network_connection_timeout";

    Integer getInteger(String key);

    Long getLong(String key);

    String getString(String key);

    Integer getInteger(String key, Integer defaultValue);

    Long getLong(String key, Long defaultValue);

    String getString(String key, String defaultValue);

    void setInteger(String key, Integer value);

    void setLong(String key, Long value);

    void setString(String key, String value);
}
