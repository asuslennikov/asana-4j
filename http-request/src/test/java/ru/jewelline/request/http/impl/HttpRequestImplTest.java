package ru.jewelline.request.http.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.NullResponseReceiver;
import ru.jewelline.request.http.entity.StreamBasedEntity;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class HttpRequestImplTest {

    HttpRequestPropertyAccessor propertyAccessor = new HttpRequestPropertyAccessor();
    @Mock
    private HttpRequestFactoryImpl httpRequestFactory;

    private HttpRequestImpl testInstance() {
        this.propertyAccessor.setUrl("example.com");
        this.propertyAccessor.setEntity(new StreamBasedEntity(new ByteArrayInputStream(new byte[]{0})));
        this.propertyAccessor.setHeader("header", "value");
        this.propertyAccessor.setQueryParameter("parameter", "value");
        return new HttpRequestImpl(this.httpRequestFactory, propertyAccessor, HttpMethod.PUT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_createWithNullHttpMethod() {
        new HttpRequestImpl(this.httpRequestFactory, new HttpRequestPropertyAccessor(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_createWithNullHttpFactory() {
        new HttpRequestImpl(null, new HttpRequestPropertyAccessor(), HttpMethod.POST);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_createWithNullPropertyAccessor() {
        new HttpRequestImpl(null, new HttpRequestPropertyAccessor(), HttpMethod.POST);
    }

    @Test
    public void test_normalCreation() {
        testInstance();

        // assert no exception
    }

    @Test
    public void test_getUrlReturnsTheSameAsPropertyAccessor() {
        HttpRequestImpl httpRequest = testInstance();
        assertThat(httpRequest.getUrl()).isEqualTo(propertyAccessor.getUrl());
    }

    @Test
    public void test_getEntityReturnsTheSameAsPropertyAccessor() {
        HttpRequestImpl httpRequest = testInstance();
        assertThat(httpRequest.getEntity()).isEqualTo(propertyAccessor.getEntity());
    }

    @Test
    public void test_getHeadersReturnsTheSameAsPropertyAccessor() {
        HttpRequestImpl httpRequest = testInstance();
        assertThat(httpRequest.getHeaders()).isEqualTo(propertyAccessor.getHeaders());
    }

    @Test
    public void test_getQueryParametersReturnsTheSameAsPropertyAccessor() {
        HttpRequestImpl httpRequest = testInstance();
        assertThat(httpRequest.getQueryParameters()).isEqualTo(propertyAccessor.getQueryParameters());
    }

    @Test
    public void test_executeWithNullReceiver() {
        HttpRequestImpl httpRequest = testInstance();
        httpRequest.execute(null);

        verify(httpRequestFactory).execute(httpRequest, null);
    }

    @Test
    public void test_executeWithReceiver() {
        HttpRequestImpl httpRequest = testInstance();
        NullResponseReceiver receiver = NullResponseReceiver.getInstance();
        httpRequest.execute(receiver);

        verify(httpRequestFactory).execute(httpRequest, receiver);
    }
}
