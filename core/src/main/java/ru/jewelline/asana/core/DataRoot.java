package ru.jewelline.asana.core;

import java.util.Map;

public class DataRoot<T> {
    private T data;
    private Map<String, Object> options;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map<String, Object> getOptions() {
        return options;
    }

    public void setOptions(Map<String, Object> options) {
        this.options = options;
    }
}
