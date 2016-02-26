package ru.jewelline.request.http.impl;

import org.junit.Test;
import ru.jewelline.request.http.UrlBuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlBuilderImplTest {

    protected UrlBuilder getUrlBuilder() {
        return new UrlBuilderImpl(Charset.isSupported("UTF-8") ? Charset.forName("UTF-8") : Charset.defaultCharset());
    }

    @Test
    public void test_withNullPath() {
        assertThat(getUrlBuilder().path(null).build()).isEqualTo("");
    }

    @Test
    public void test_withPathOnly() {
        assertThat(getUrlBuilder().path("http://example.com").build()).isEqualTo("http://example.com");
    }

    @Test
    public void test_withPathAndOneEmptyQueryParam() {
        assertThat(getUrlBuilder()
                .path("http://example.com")
                .setQueryParameter("p1", null)
                .build()).isEqualTo("http://example.com");
    }

    @Test
    public void test_withPathAndOneQueryParam() {
        assertThat(getUrlBuilder()
                .path("http://example.com")
                .setQueryParameter("p1", "v1")
                .build()).isEqualTo("http://example.com?p1=v1");
    }

    @Test
    public void test_withPathAndOneListQueryParam() {
        assertThat(getUrlBuilder()
                .path("http://example.com")
                .setQueryParameter("p1", "v1", "v2", "v3")
                .build()).isEqualTo("http://example.com?p1=v1&p1=v2&p1=v3");
    }

    @Test
    public void test_withPathAndTwoQueryParam() {
        assertThat(getUrlBuilder()
                .path("http://example.com")
                .setQueryParameter("p1", "v1")
                .setQueryParameter("p2", "v2")
                .build()).isEqualTo("http://example.com?p1=v1&p2=v2");
    }

    @Test
    public void test_withPathAndThreeQueryParam() {
        assertThat(getUrlBuilder()
                .path("http://example.com")
                .setQueryParameter("p1", "v1")
                .setQueryParameter("p2", null)
                .setQueryParameter("p3", "v3")
                .build()).isEqualTo("http://example.com?p1=v1&p3=v3");
    }

    @Test
    public void test_withPathAndEncodedQueryParam() {
        String url = getUrlBuilder()
                .path("http://example.com")
                .setQueryParameter("p!@#$%^&*()_+1", "v 1")
                .setQueryParameter("p2", "?!@#$%^&*%28%29_+:")
                .build();
        assertThat(url).isEqualTo("http://example.com?p2=%3F%21%40%23%24%25%5E%26*%2528%2529_%2B%3A&p%21%40%23%24%25%5E%26*%28%29_%2B1=v%201");
        try {
            new URL(url);
        } catch (MalformedURLException e) {
            assert false : "Malformed url";
        }
    }
}
