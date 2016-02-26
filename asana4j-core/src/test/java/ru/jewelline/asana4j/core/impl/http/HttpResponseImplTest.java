package ru.jewelline.asana4j.core.impl.http;

import org.assertj.core.data.MapEntry;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpResponseImplTest {

    public HttpResponseImpl<?> testInstance() {
        return new HttpResponseImpl<>(null);
    }

    @Test
    public void test_createWithNullOutput() {
        testInstance();

        //assert no exception
    }

    @Test
    public void test_createWithOutput() {
        new HttpResponseImpl<>(new ByteArrayOutputStream());

        //assert no exception
    }

    @Test
    public void test_setAndGetCode() {
        HttpResponseImpl<?> httpResponse = testInstance();
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
        HttpResponseImpl<?> httpResponse = testInstance();
        String hKey = "key";
        List<String> hValue = Collections.singletonList("value");
        Map<String, List<String>> headers = new HashMap<>();
        headers.put(hKey, hValue);
        httpResponse.setHeaders(headers);
        assertThat(httpResponse.headers()).hasSize(1);
        assertThat(httpResponse.headers()).containsOnly(MapEntry.entry(hKey, hValue));
    }

    @Test
    public void test_createAndGetNullOutput() {
        HttpResponseImpl<?> httpResponse = testInstance();
        assertThat(httpResponse.output()).isNull();
    }

    @Test
    public void test_createAndGetOutput() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        HttpResponseImpl<ByteArrayOutputStream> httpResponse = new HttpResponseImpl<>(stream);
        assertThat(httpResponse.output()).isEqualTo(stream);
    }
}
