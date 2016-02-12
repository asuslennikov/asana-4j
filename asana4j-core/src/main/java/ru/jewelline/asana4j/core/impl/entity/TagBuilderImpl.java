package ru.jewelline.asana4j.core.impl.entity;

import ru.jewelline.asana4j.core.api.entity.ProjectColor;
import ru.jewelline.asana4j.core.api.entity.Tag;
import ru.jewelline.asana4j.core.impl.entity.io.FieldsUpdater;

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
