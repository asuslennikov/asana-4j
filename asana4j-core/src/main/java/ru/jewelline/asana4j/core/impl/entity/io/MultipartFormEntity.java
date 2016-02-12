package ru.jewelline.asana4j.core.impl.entity.io;

import ru.jewelline.asana4j.utils.StackedInputStream;
import ru.jewelline.asana4j.utils.StringUtils;
import ru.jewelline.request.api.entity.EntitySerializer;
import ru.jewelline.request.api.entity.SerializableEntity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLConnection;

public class MultipartFormEntity implements SerializableEntity {
    private static final String LINE_END = "\r\n";

    private final InputStream attachmentBody;
    private final String boundary;

    public MultipartFormEntity(String name, InputStream attachmentBody) {
        this.boundary = "###BOUNDARY_" + System.currentTimeMillis() + "###";
        this.attachmentBody = StackedInputStream.create()
                .add(new ByteArrayInputStream(getOpening(name)))
                .add(attachmentBody)
                .add(new ByteArrayInputStream(getEnding()))
                .finish();
    }

    private byte[] getOpening(String name) {
        StringBuilder opening = new StringBuilder();
        opening.append("--").append(this.boundary).append(LINE_END);
        opening.append("Content-Disposition: form-data; name=\"file\"; filename=\"").append(name).append("\"").append(LINE_END);
        opening.append("Content-Type: ").append(URLConnection.guessContentTypeFromName(name)).append(LINE_END);
        opening.append(LINE_END);
        return opening.toString().getBytes(StringUtils.getCharset());
    }

    private byte[] getEnding() {
        StringBuilder ending = new StringBuilder();
        ending.append(LINE_END);
        ending.append(LINE_END);
        ending.append("--").append(this.boundary).append("--").append(LINE_END);
        return ending.toString().getBytes(StringUtils.getCharset());
    }

    public String getBoundary() {
        return this.boundary;
    }

    @Override
    public InputStream getSerialized() {
        return this.attachmentBody;
    }

    @Override
    public EntitySerializer<?> getSerializer() {
        return new MultipartFormEntitySerializer();
    }

    private static class MultipartFormEntitySerializer implements EntitySerializer<MultipartFormEntity> {
        @Override
        public InputStream serialize(MultipartFormEntity entity) {
            return entity.getSerialized();
        }
    }
}
