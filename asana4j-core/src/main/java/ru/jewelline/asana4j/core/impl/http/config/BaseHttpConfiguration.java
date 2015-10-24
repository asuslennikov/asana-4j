package ru.jewelline.asana4j.core.impl.http.config;

import ru.jewelline.asana4j.core.impl.http.HttpConfiguration;

public class BaseHttpConfiguration implements HttpConfiguration {

    public static final int RETRY_COUNT = 3;
    public static final int CONNECTION_TIMEOUT = 30000;

    private int retryCount = RETRY_COUNT;
    private int connectionTimeout = CONNECTION_TIMEOUT;

    public BaseHttpConfiguration() {
    }

    public BaseHttpConfiguration(int retryCount, int connectionTimeout) {
        this.retryCount = retryCount;
        this.connectionTimeout = connectionTimeout;
    }

    @Override
    public int getRetryCount() {
        return this.retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    @Override
    public int getConnectionTimeout() {
        return this.connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }
}
