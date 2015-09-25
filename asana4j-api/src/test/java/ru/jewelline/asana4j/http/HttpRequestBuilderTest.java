package ru.jewelline.asana4j.http;

import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public abstract class HttpRequestBuilderTest {
    private static final String STRING = "STRING!@#$%^&*()_+1234567890-==\\~ /";

    protected abstract HttpRequestBuilder getRequestBuilder();

    @Test
    public void test_throwsExceptionForMissedBaseUrl(){
        try {
            getRequestBuilder().buildAs(HttpMethod.GET);
            fail("The NetworkException should be thrown since there is no base url");
        } catch (NetworkException ex){
            assertThat(ex.getErrorCode()).isEqualTo(NetworkException.MALFORMED_URL);
        }
    }

    @Test
    public void test_addDefaultTransportPrefix(){
        HttpRequest httpRequest = getRequestBuilder().path("www.example.com").buildAs(HttpMethod.GET);
        assertThat(httpRequest.getUrl()).startsWith("http://");
    }

    @Test
    public void test_encodeQueryParameters(){
        HttpRequest httpRequest = getRequestBuilder()
                .path("www.example.com")
                .setQueryParameter(STRING, STRING)
                .buildAs(HttpMethod.GET);
        assertThat(httpRequest.getUrl()).doesNotContain("!@#$");
        assertThat(httpRequest.getUrl()).doesNotContain("^&*()_+");
        assertThat(httpRequest.getUrl()).doesNotContain("~");
    }

    @Test
    public void test_canAddQueryParameters(){
        HttpRequest httpRequest = getRequestBuilder()
                .path("www.example.com")
                .setQueryParameter("key1", "value1")
                .setQueryParameter("key2", "value2")
                .buildAs(HttpMethod.GET);
        assertThat(httpRequest.getUrl()).contains("key1=value1&key2=value2");
    }

    @Test
    public void test_overrideQueryParameterOnSecondCall(){
        HttpRequest httpRequest = getRequestBuilder()
                .path("www.example.com")
                .setQueryParameter("key", "value1")
                .setQueryParameter("key", "value2")
                .buildAs(HttpMethod.GET);
        assertThat(httpRequest.getUrl()).contains("key=value2");
    }

    @Test
    public void test_overrideHeaderOnSecondCall(){
        HttpRequest httpRequest = getRequestBuilder()
                .path("www.example.com")
                .setHeader("key", "value1")
                .setHeader("key", "value2")
                .buildAs(HttpMethod.GET);
        Map<String, String> headers = httpRequest.getHeaders();
        assertThat(headers).hasSize(1);
        assertThat(headers.get("key")).isEqualTo("value2");
    }

    @Test
    public void test_canAddHeaders(){
        HttpRequest httpRequest = getRequestBuilder()
                .path("www.example.com")
                .setHeader("key1", "value1")
                .setHeader("key2", "value2")
                .buildAs(HttpMethod.GET);
        Map<String, String> headers = httpRequest.getHeaders();
        assertThat(headers).hasSize(2);
        assertThat(headers.get("key1")).isEqualTo("value1");
        assertThat(headers.get("key2")).isEqualTo("value2");
    }
}
