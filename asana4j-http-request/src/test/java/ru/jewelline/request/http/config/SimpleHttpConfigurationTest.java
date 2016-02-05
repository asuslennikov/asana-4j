package ru.jewelline.request.http.config;

import org.junit.Test;

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
}
