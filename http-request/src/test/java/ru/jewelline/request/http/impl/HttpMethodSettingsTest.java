package ru.jewelline.request.http.impl;

import org.junit.Test;
import org.mockito.Matchers;
import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.HttpRequest;
import ru.jewelline.request.http.NetworkException;
import ru.jewelline.request.http.entity.SerializableEntity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class HttpMethodSettingsTest {

    @Test(expected = NetworkException.class)
    public void test_getForNullTrowsException() {
        HttpMethodSettings.getForHttpMethod(null);
    }

    @Test
    public void test_getSettingsForGetMethod() {
        assertThat(HttpMethodSettings.getForHttpMethod(HttpMethod.GET)).isEqualTo(HttpMethodSettings.GET);
    }

    @Test
    public void test_getSettingsForPutMethod() {
        assertThat(HttpMethodSettings.getForHttpMethod(HttpMethod.PUT)).isEqualTo(HttpMethodSettings.PUT);
    }

    @Test
    public void test_getSettingsForPostMethod() {
        assertThat(HttpMethodSettings.getForHttpMethod(HttpMethod.POST)).isEqualTo(HttpMethodSettings.POST);
    }

    @Test
    public void test_getSettingsForDeleteMethod() {
        assertThat(HttpMethodSettings.getForHttpMethod(HttpMethod.DELETE)).isEqualTo(HttpMethodSettings.DELETE);
    }

    @Test
    public void test_applySettingsForGetWithoutEnity() throws IOException {
        HttpURLConnection urlConnection = mock(HttpURLConnection.class);
        HttpRequest httpRequest = mock(HttpRequest.class);
        HttpMethodSettings.GET.apply(urlConnection, httpRequest);
        verifyZeroInteractions(urlConnection, httpRequest);
    }

    @Test
    public void test_applySettingsForGetWithEntity() throws IOException {
        HttpURLConnection urlConnection = mock(HttpURLConnection.class);
        HttpRequest httpRequest = mock(HttpRequest.class);
        SerializableEntity serializableEntity = mock(SerializableEntity.class);
        InputStream entity = mock(InputStream.class);
        when(serializableEntity.getSerialized()).thenReturn(entity);
        when(httpRequest.getEntity()).thenReturn(serializableEntity);
        HttpMethodSettings.GET.apply(urlConnection, httpRequest);
        verifyZeroInteractions(urlConnection, httpRequest);
    }

    private void applySettingsForSomeWithoutEnity(HttpMethodSettings some) throws IOException {
        HttpURLConnection urlConnection = mock(HttpURLConnection.class);
        HttpRequest httpRequest = mock(HttpRequest.class);
        some.apply(urlConnection, httpRequest);
        verify(urlConnection, never()).setDoOutput(true);
        verify(urlConnection, never()).getOutputStream();
        verify(urlConnection).setRequestMethod(Matchers.anyString());
    }

    private void applySettingsForSomeWithEntity(HttpMethodSettings some) throws IOException {
        HttpURLConnection urlConnection = mock(HttpURLConnection.class);
        HttpRequest httpRequest = mock(HttpRequest.class);
        SerializableEntity serializableEntity = mock(SerializableEntity.class);
        InputStream entity = mock(InputStream.class);
        when(serializableEntity.getSerialized()).thenReturn(entity);
        when(httpRequest.getEntity()).thenReturn(serializableEntity);
        some.apply(urlConnection, httpRequest);
        verify(urlConnection).setDoOutput(true);
        verify(urlConnection).getOutputStream();
        verify(urlConnection).setRequestMethod(Matchers.anyString());
    }

    @Test
    public void test_applySettingsForPutWithoutEnity() throws IOException {
        applySettingsForSomeWithoutEnity(HttpMethodSettings.PUT);
    }

    @Test
    public void test_applySettingsForPutWithEnity() throws IOException {
        applySettingsForSomeWithEntity(HttpMethodSettings.PUT);
    }

    @Test
    public void test_applySettingsForPostWithoutEnity() throws IOException {
        applySettingsForSomeWithoutEnity(HttpMethodSettings.POST);
    }

    @Test
    public void test_applySettingsForPostWithEnity() throws IOException {
        applySettingsForSomeWithEntity(HttpMethodSettings.POST);
    }

    @Test
    public void test_applySettingsForDeleteWithoutEnity() throws IOException {
        applySettingsForSomeWithoutEnity(HttpMethodSettings.DELETE);
    }

    @Test
    public void test_applySettingsForDeleteWithEnity() throws IOException {
        applySettingsForSomeWithEntity(HttpMethodSettings.DELETE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_applySettingsNullConnection() throws IOException {
        HttpMethodSettings.PUT.apply(null, mock(HttpRequest.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_applySettingsNullRequest() throws IOException {
        HttpMethodSettings.PUT.apply(mock(HttpURLConnection.class), null);
    }
}
