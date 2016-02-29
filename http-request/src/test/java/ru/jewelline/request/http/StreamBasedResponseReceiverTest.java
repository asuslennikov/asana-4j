package ru.jewelline.request.http;

import org.assertj.core.data.MapEntry;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class StreamBasedResponseReceiverTest {

    @Test
    public void test_implementsHttpResponseReceiver() {
        assertThat(new StreamBasedResponseReceiver(null) instanceof HttpResponseReceiver).isTrue();
    }

    @Test
    public void test_createWithNullStreams() {
        StreamBasedResponseReceiver receiver = new StreamBasedResponseReceiver(null, null);
        assertThat(receiver.getResponseStream()).isEqualTo(null);
        assertThat(receiver.getErrorStream()).isEqualTo(null);
    }

    @Test
    public void test_createWithNonNullStreams(){
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        StreamBasedResponseReceiver receiver = new StreamBasedResponseReceiver(responseStream, errorStream);

        assertThat(receiver.getResponseStream()).isEqualTo(responseStream);
        assertThat(receiver.getErrorStream()).isEqualTo(errorStream);
    }

    @Test
    public void test_setResponseCode(){
        StreamBasedResponseReceiver receiver = new StreamBasedResponseReceiver(null, null);
        int responseCode = 200;
        receiver.setResponseCode(responseCode);

        assertThat(receiver.getResponseCode()).isEqualTo(responseCode);
    }

    @Test
    public void test_setResponseHeaders(){
        StreamBasedResponseReceiver receiver = new StreamBasedResponseReceiver(null, null);
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("key", Collections.singletonList("value"));
        receiver.setResponseHeaders(headers);

        assertThat(receiver.getResponseHeaders()).hasSize(1).contains(MapEntry.entry("key", Collections.singletonList("value")));
    }
}
