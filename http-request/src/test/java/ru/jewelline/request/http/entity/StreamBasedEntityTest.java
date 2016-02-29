package ru.jewelline.request.http.entity;

import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class StreamBasedEntityTest {

    @Test
    public void test_implementsSerializableEntity(){
        assertThat(new StreamBasedEntity(null) instanceof SerializableEntity).isTrue();
    }

    @Test
    public void test_createWithNullStream() {
        SerializableEntity entity = new StreamBasedEntity(null);
        assertThat(entity.getSerialized()).isEqualTo(null);
    }

    @Test
    public void test_createWithStream() {
        ByteArrayInputStream stream = new ByteArrayInputStream(new byte[]{1, 2, 3});
        SerializableEntity entity = new StreamBasedEntity(stream);
        assertThat(entity.getSerialized()).isEqualTo(stream);
    }
}
