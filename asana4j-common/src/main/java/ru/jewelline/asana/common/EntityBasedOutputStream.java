package ru.jewelline.asana.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public abstract class EntityBasedOutputStream<T> extends OutputStream {
    private final ByteArrayOutputStream baos;

    public EntityBasedOutputStream() {
        this.baos = new ByteArrayOutputStream(8192);
    }

    @Override
    public void write(int b) throws IOException {
        this.baos.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        this.baos.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        this.baos.write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
        this.baos.flush();
    }

    @Override
    public void close() throws IOException {
        this.baos.close();
    }

    public abstract T toEntity();

    public abstract PagedList<T> toEntityCollection();
}
