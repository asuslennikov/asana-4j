package ru.jewelline.request.http.config;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleHttpConfigurationTest {

    protected SimpleHttpConfiguration testInstance() {
        return new SimpleHttpConfiguration();
    }

    @Test
    public void test_simpleCreation() {
        testInstance();
        // assert no errors
    }

    @Test
    public void test_checkDefaultValueForRetryCount() {
        assertThat(testInstance().getRetryCount()).isEqualTo(SimpleHttpConfiguration.RETRY_COUNT);
    }

    @Test
    public void test_setCorrectRetryCount() {
        SimpleHttpConfiguration config = testInstance();
        int newRetryCount = 5;
        config.setRetryCount(newRetryCount);
        assertThat(config.getRetryCount()).isEqualTo(newRetryCount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_setZeroRetryCount() {
        SimpleHttpConfiguration config = testInstance();
        int newRetryCount = 0;
        config.setRetryCount(newRetryCount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_setNegativeRetryCount() {
        SimpleHttpConfiguration config = testInstance();
        int newRetryCount = -1000;
        config.setRetryCount(newRetryCount);
    }

    @Test
    public void test_checkDefaultValueForConnectionTimeout() {
        assertThat(testInstance().getConnectionTimeout()).isEqualTo(SimpleHttpConfiguration.CONNECTION_TIMEOUT);
    }

    @Test
    public void test_setCorrectConnectionTimeout() {
        SimpleHttpConfiguration config = testInstance();
        int newConnectionTimeout = 5;
        config.setConnectionTimeout(newConnectionTimeout);
        assertThat(config.getConnectionTimeout()).isEqualTo(newConnectionTimeout);
    }

    @Test
    public void test_setZeroConnectionTimeout() {
        SimpleHttpConfiguration config = testInstance();
        int newConnectionTimeout = 0;
        config.setConnectionTimeout(newConnectionTimeout);
        assertThat(config.getConnectionTimeout()).isEqualTo(newConnectionTimeout);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_setNegativeConnectionTimeout() {
        SimpleHttpConfiguration config = testInstance();
        int newConnectionTimeout = -1000;
        config.setConnectionTimeout(newConnectionTimeout);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_setNullUrlCharset() {
        SimpleHttpConfiguration config = testInstance();
        config.setUrlCharset(null);
    }

    @Test
    public void test_setCorrectUrlCharset() {
        SimpleHttpConfiguration config = testInstance();
        Charset charset = Charset.defaultCharset();
        config.setUrlCharset(charset);
        assertThat(config.getUrlCharset()).isEqualTo(charset);
    }

    @Test
    public void test_setNullProxy(){
        SimpleHttpConfiguration config = testInstance();
        config.setProxy(null);
        assertThat(config.getProxy()).isEqualTo(Proxy.NO_PROXY);
    }

    @Test
    public void test_setProxy(){
        SimpleHttpConfiguration config = testInstance();
        config.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("example.com", 8080)));
        assertThat(config.getProxy()).isNotNull();
        assertThat(((InetSocketAddress) config.getProxy().address()).getPort()).isEqualTo(8080);
        assertThat(((InetSocketAddress) config.getProxy().address()).getHostName()).isEqualTo("example.com");
    }
}
