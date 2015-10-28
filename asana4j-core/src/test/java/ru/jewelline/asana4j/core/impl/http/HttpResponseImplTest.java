package ru.jewelline.asana4j.core.impl.http;

import org.assertj.core.data.MapEntry;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseImplTest {

    public HttpResponseImpl testInstance() {
        return new HttpResponseImpl(null);
    }

    @Test
    public void test_createWithNullOutput() {
        testInstance();

        //assert no exception
    }

    @Test
    public void test_createWithOutpu() {
        new HttpResponseImpl(new ByteArrayOutputStream());

        //assert no exception
    }

    @Test
    public void test_setAndGetCode() {
        HttpResponseImpl httpResponse = testInstance();
        int code = Integer.MAX_VALUE;
        httpResponse.setCode(code);
        assertThat(httpResponse.code()).isEqualTo(code);
    }

    @Test
    public void test_hasEmptyHeadersAfterCreation() {
        HttpResponseImpl httpResponse = testInstance();
        assertThat(httpResponse.headers()).isEmpty();
    }

    @Test
    public void test_setAndGetHeaders() {
        HttpResponseImpl httpResponse = testInstance();
        String hKey = "key";
        List<String> hValue = Arrays.asList("value");
        httpResponse.setHeader(hKey, hValue);
        assertThat(httpResponse.headers()).hasSize(1);
        assertThat(httpResponse.headers()).containsOnly(MapEntry.entry(hKey, hValue));
    }

    @Test
    public void test_setAndGetHeadersWitnNullValue() {
        HttpResponseImpl httpResponse = testInstance();
        httpResponse.setHeader("key", null);
        assertThat(httpResponse.headers()).isEmpty();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_addingToMapIsNotSupported() {
        HttpResponseImpl httpResponse = testInstance();
        httpResponse.headers().put("key", Arrays.asList("value"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_addingToListValueIsNotSupported() {
        HttpResponseImpl httpResponse = testInstance();
        String hKey = "key";
        List<String> hValue = new ArrayList<>();
        httpResponse.setHeader(hKey, hValue);
        Map<String, List<String>> headers = httpResponse.headers();
        headers.get(hKey).add("some");
    }

    @Test
    public void test_createAndGetNullOutput() {
        HttpResponseImpl httpResponse = testInstance();
        assertThat(httpResponse.output()).isNull();
    }

    @Test
    public void test_createAndGetOutput() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        HttpResponseImpl httpResponse = new HttpResponseImpl(stream);
        assertThat(httpResponse.output()).isEqualTo(stream);
    }
}
