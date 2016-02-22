package ru.jewelline.asana.json;

import ru.jewelline.asana4j.api.entities.ProjectColor;
import ru.jewelline.asana4j.api.entities.Tag;
import ru.jewelline.asana4j.impl.entity.io.FieldsUpdater;

public class TagBuilderImpl<T extends Tag.TagBuilder> extends FieldsUpdater implements Tag.TagBuilder<T> {
    private final Class<T> implClass;

    public TagBuilderImpl(Class<T> implClass) {
        super();
        this.implClass = implClass;
    }

    @Override
    public T setName(String name) {
        putField(TagImplProcessor.NAME.getFieldName(), name);
        return this.implClass.cast(this);
    }

    @Override
    public T setNotes(String notes) {
        putField(TagImplProcessor.NOTES.getFieldName(), notes);
        return this.implClass.cast(this);
    }

    @Override
    public T setColor(ProjectColor color) {
        putField(TagImplProcessor.COLOR.getFieldName(), color != null ? color.getColorCode() : null);
        return this.implClass.cast(this);
    }
}
