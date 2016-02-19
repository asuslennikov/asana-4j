package ru.jewelline.request.http.config;

import java.net.Proxy;
import java.nio.charset.Charset;

public final class SimpleHttpConfiguration implements HttpConfiguration {

    public static final int RETRY_COUNT = 3;
    public static final int CONNECTION_TIMEOUT = 30000;

    private int retryCount = RETRY_COUNT;
    private int connectionTimeout = CONNECTION_TIMEOUT;
    private Charset urlCharset;
    private Proxy proxy = Proxy.NO_PROXY;

    public SimpleHttpConfiguration() {
        this.urlCharset = Charset.isSupported("UTF-8") ? Charset.forName("UTF-8") : Charset.defaultCharset();
    }

    @Override
    public int getRetryCount() {
        return this.retryCount;
    }

    public void setRetryCount(int retryCount) {
        if (retryCount <= 0) {
            throw new IllegalArgumentException("Retry count can not be less then 1");
        }
        this.retryCount = retryCount;
    }

    @Override
    public int getConnectionTimeout() {
        return this.connectionTimeout;
    }

    @Override
    public Proxy getProxy() {
        return this.proxy;
    }

    public void setProxy(Proxy proxy){
        this.proxy = proxy;
    }

    @Override
    public Charset getUrlCharset() {
        return this.urlCharset;
    }

    public void setUrlCharset(Charset urlCharset) {
        if (urlCharset == null) {
            throw new IllegalArgumentException("Url charset can not be null.");
        }
        this.urlCharset = urlCharset;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        if (connectionTimeout < 0) {
            throw new IllegalArgumentException("Connection timeout can not be less then 0");
        }
        this.connectionTimeout = connectionTimeout;
    }
}
