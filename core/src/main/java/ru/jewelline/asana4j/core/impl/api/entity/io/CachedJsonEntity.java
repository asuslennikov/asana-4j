package ru.jewelline.asana4j.core.impl.api.entity.io;

import org.json.JSONObject;
import ru.jewelline.asana4j.api.entity.io.JsonEntity;
import ru.jewelline.asana4j.utils.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class CachedJsonEntity implements JsonEntity {
    private final JSONObject json;

    public CachedJsonEntity(JsonEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity can not be null.");
        }
        this.json = entity.asJson();
    }

    public CachedJsonEntity(Map<?, ?> source) {
        if (source == null) {
            throw new IllegalArgumentException("Source map can not be null.");
        }
        this.json = new JSONObject();
        LinkedList<ConversionEntry> accumulator = new LinkedList<>();
        accumulator.add(new ConversionEntry(this.json, source));
        convertToJsonSafeNullObj(accumulator);
    }

    private void convertToJsonSafeNullObj(List<ConversionEntry> convertAccumulator) {
        while (!convertAccumulator.isEmpty()) {
            ConversionEntry conversionEntry = convertAccumulator.remove(0);
            if (conversionEntry.source != null) {
                for (Map.Entry<?, ?> entry : conversionEntry.source.entrySet()) {
                    if (entry.getKey() != null) {
                        addEntryToJson(conversionEntry.target, entry, convertAccumulator);
                    }
                }
            }
        }
    }

    private void addEntryToJson(JSONObject json, Map.Entry<?, ?> entry, List<ConversionEntry> convertAccumulator) {
        if (entry.getValue() == null) {
            json.put(entry.getKey().toString(), JSONObject.NULL);
        } else if (entry.getValue() instanceof Map) {
            JSONObject complexValue = new JSONObject();
            json.put(entry.getKey().toString(), complexValue);
            convertAccumulator.add(new ConversionEntry(complexValue, (Map<?, ?>) entry.getValue()));
        } else {
            json.put(entry.getKey().toString(), entry.getValue());
        }
    }

    @Override
    public JSONObject asJson() {
        return this.json;
    }

    @Override
    public InputStream getSerialized() {
        return new ByteArrayInputStream(asJson().toString().getBytes(StringUtils.getCharset()));
    }

    // Utility class
    private static class ConversionEntry {
        JSONObject target;
        Map<?, ?> source;

        ConversionEntry(JSONObject target, Map<?, ?> source) {
            this.target = target;
            this.source = source;
        }
    }
}
