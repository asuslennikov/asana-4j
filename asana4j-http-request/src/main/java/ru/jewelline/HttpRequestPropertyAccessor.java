package ru.jewelline;

import java.util.List;
import java.util.Map;

public interface HttpRequestPropertyAccessor {
    String getUrl();

    Map<String, List<String>> getQueryParameters();

    Map<String, List<String>> getHeaders();

    SerializableEntity getEntity();
}
