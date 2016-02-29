package ru.jewelline.request.http.impl;

import org.assertj.core.data.MapEntry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import ru.jewelline.request.http.entity.SerializableEntity;
import ru.jewelline.request.http.entity.StreamBasedEntity;

import java.io.ByteArrayInputStream;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class HttpRequestPropertyAccessorTest {

    private HttpRequestPropertyAccessor testInstance() {
        return new HttpRequestPropertyAccessor();
    }

    @Test
    public void test_normalCreation() {
        testInstance();

        // assert no exception
    }

    @Test
    public void test_setAndGetNullUrl() {
        HttpRequestPropertyAccessor propertyAccessor = testInstance();
        String url = null;
        propertyAccessor.setUrl(url);
        assertThat(propertyAccessor.getUrl()).isEqualTo(url);
    }

    @Test
    public void test_setAndGetUrl() {
        HttpRequestPropertyAccessor propertyAccessor = testInstance();
        String url = "some url";
        propertyAccessor.setUrl(url);
        assertThat(propertyAccessor.getUrl()).isEqualTo(url);
    }

    @Test
    public void test_setAndGetHeaders() {
        HttpRequestPropertyAccessor propertyAccessor = testInstance();
        String hKey = "key";
        String hValue = "value";
        propertyAccessor.setHeader(hKey, hValue);
        assertThat(propertyAccessor.getHeaders()).hasSize(1);
        assertThat(propertyAccessor.getHeaders()).containsOnly(MapEntry.entry(hKey, Collections.singletonList(hValue)));
    }

    @Test
    public void test_setHeaderNullKey() {
        HttpRequestPropertyAccessor propertyAccessor = testInstance();
        String hKey = null;
        String hValue = "value";
        propertyAccessor.setHeader(hKey, hValue);
        assertThat(propertyAccessor.getHeaders()).hasSize(0);
    }

    @Test
    public void test_setHeaderNullValue() {
        HttpRequestPropertyAccessor propertyAccessor = testInstance();
        String hKey = "key";
        String hValue = null;
        propertyAccessor.setHeader(hKey, hValue);
        assertThat(propertyAccessor.getHeaders()).hasSize(0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_getHeadersReturnsUnmodifiableMap() {
        HttpRequestPropertyAccessor propertyAccessor = testInstance();
        String hKey = "key";
        String hValue = "value";
        propertyAccessor.setHeader(hKey, hValue);
        propertyAccessor.getHeaders().put("key2", Collections.singletonList("value2"));

        // assert exception
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_setAndGetHeadersReturnsUnmodifiableMap() {
        HttpRequestPropertyAccessor propertyAccessor = testInstance();
        propertyAccessor.getHeaders().put("key2", Collections.singletonList("value2"));

        // assert exception
    }

    @Test
    public void test_setAndGetQueryParameters() {
        HttpRequestPropertyAccessor propertyAccessor = testInstance();
        String qKey = "key";
        String qValue = "value";
        propertyAccessor.setQueryParameter(qKey, qValue);
        assertThat(propertyAccessor.getQueryParameters()).hasSize(1);
        assertThat(propertyAccessor.getQueryParameters()).containsOnly(MapEntry.entry(qKey, Collections.singletonList(qValue)));
    }

    @Test
    public void test_setQueryParametersNullKey() {
        HttpRequestPropertyAccessor propertyAccessor = testInstance();
        String qKey = null;
        String qValue = "value";
        propertyAccessor.setQueryParameter(qKey, qValue);
        assertThat(propertyAccessor.getQueryParameters()).hasSize(0);
    }

    @Test
    public void test_setQueryParametersNullValue() {
        HttpRequestPropertyAccessor propertyAccessor = testInstance();
        String qKey = "key";
        String qValue = null;
        propertyAccessor.setQueryParameter(qKey, qValue);
        assertThat(propertyAccessor.getQueryParameters()).hasSize(0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_getQueryParametersReturnsUnmodifiableMap() {
        HttpRequestPropertyAccessor propertyAccessor = testInstance();
        String qKey = "key";
        String qValue = "value";
        propertyAccessor.setQueryParameter(qKey, qValue);
        propertyAccessor.getQueryParameters().put("key2", Collections.singletonList("value2"));

        // assert exception
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_setAndGetQueryParametersReturnsUnmodifiableMap() {
        HttpRequestPropertyAccessor propertyAccessor = testInstance();
        propertyAccessor.getQueryParameters().put("key2", Collections.singletonList("value2"));

        // assert exception
    }

    @Test
    public void test_setAndGetNullEntity() {
        HttpRequestPropertyAccessor propertyAccessor = testInstance();
        propertyAccessor.setEntity(null);
        assertThat(propertyAccessor.getEntity()).isNull();
    }

    @Test
    public void test_setAndGetEntity() {
        HttpRequestPropertyAccessor propertyAccessor = testInstance();
        SerializableEntity entity = new StreamBasedEntity(new ByteArrayInputStream(new byte[]{1, 2, 3}));
        propertyAccessor.setEntity(entity);
        assertThat(propertyAccessor.getEntity()).isEqualTo(entity);
    }
}
