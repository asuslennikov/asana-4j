package ru.jewelline.asana4j.core.impl.http;

import org.junit.Test;
import ru.jewelline.asana4j.core.impl.http.config.BaseHttpConfiguration;

public class BaseHttpConfigurationTest {

    protected BaseHttpConfiguration testInstance() {
        return new BaseHttpConfiguration();
    }

    @Test
    public void test_simpleCreation() {
        testInstance();
        // assert no errors
    }

    @Test
    public void test_checkDefaultValueForRetryCount() {
        assertThat(testInstance().getRetryCount()).isEqualTo(BaseHttpConfiguration.RETRY_COUNT);
    }

    @Test
    public void test_setCorrectRetryCount() {
        BaseHttpConfiguration config = testInstance();
        int newRetryCount = 5;
        config.setRetryCount(newRetryCount);
        assertThat(config.getRetryCount()).isEqualTo(newRetryCount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_setZeroRetryCount() {
        BaseHttpConfiguration config = testInstance();
        int newRetryCount = 0;
        config.setRetryCount(newRetryCount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_setNegativeRetryCount() {
        BaseHttpConfiguration config = testInstance();
        int newRetryCount = -1000;
        config.setRetryCount(newRetryCount);
    }

    @Test
    public void test_checkDefaultValueForConnectionTimeout() {
        assertThat(testInstance().getConnectionTimeout()).isEqualTo(BaseHttpConfiguration.CONNECTION_TIMEOUT);
    }

    @Test
    public void test_setCorrectConnectionTimeout() {
        BaseHttpConfiguration config = testInstance();
        int newConnectionTimeout = 5;
        config.setConnectionTimeout(newConnectionTimeout);
        assertThat(config.getConnectionTimeout()).isEqualTo(newConnectionTimeout);
    }

    @Test
    public void test_setZeroConnectionTimeout() {
        BaseHttpConfiguration config = testInstance();
        int newConnectionTimeout = 0;
        config.setConnectionTimeout(newConnectionTimeout);
        assertThat(config.getConnectionTimeout()).isEqualTo(newConnectionTimeout);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_setNegativeConnectionTimeout() {
        BaseHttpConfiguration config = testInstance();
        int newConnectionTimeout = -1000;
        config.setConnectionTimeout(newConnectionTimeout);
    }
}
