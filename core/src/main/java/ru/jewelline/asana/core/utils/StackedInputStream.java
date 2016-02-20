package ru.jewelline.asana.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class StackedInputStream extends InputStream {

    private static final int NO_DATA = -1;

    public static class Builder {
        private transient boolean done = false;
        private final StackedInputStream underConstruction;

        private Builder() {
            this.underConstruction = new StackedInputStream();
        }

        private void checkState() {
            if (done) {
                throw new IllegalStateException("Builder was already finished.");
            }
        }

        public Builder add(InputStream stream) {
            checkState();
            if (stream == null) {
                throw new IllegalArgumentException("You can not add a null stream.");
            }
            this.underConstruction.streams.add(stream);
            return this;
        }

        public StackedInputStream finish() {
            checkState();
            this.done = true;
            return this.underConstruction;
        }
    }

    public static Builder from(InputStream inputStream) {
        return new Builder().add(inputStream);
    }

    private List<InputStream> streams;
    private int streamMarker;

    private StackedInputStream() {
        this.streams = new ArrayList<>();
    }

    @Override
    public int read() throws IOException {
        do {
            if (this.streamMarker >= this.streams.size()) {
                return NO_DATA;
            }
            int r = this.streams.get(this.streamMarker).read();
            if (r == NO_DATA) {
                this.streamMarker++;
            } else {
                return r;
            }
        } while (true);
    }

    @Override
    public void close() throws IOException {
        for (InputStream stream : streams) {
            stream.close();
        }
    }
}
