package ru.jewelline.asana4j.core.impl.api.entity;

import ru.jewelline.asana4j.api.entity.ProjectStatus;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityImpl;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldReader;

import java.util.Arrays;
import java.util.List;

public class ProjectStatusImpl extends ApiEntityImpl<ProjectStatusImpl> implements ProjectStatus {

    private Color color;
    private String text;
    private User author;

    public ProjectStatusImpl(ApiEntityContext context) {
        super(ProjectStatusImpl.class, context);
    }

    @Override
    protected List<JsonFieldReader<ProjectStatusImpl>> getFieldReaders() {
        return Arrays.<JsonFieldReader<ProjectStatusImpl>>asList(ProjectStatusImplProcessor.values());
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public User getAuthor() {
        return this.author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectStatusImpl that = (ProjectStatusImpl) o;

        if (color != that.color) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        return !(author != null ? !author.equals(that.author) : that.author != null);

    }

    @Override
    public int hashCode() {
        int result = color != null ? color.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("ProjectStatus [");
        out.append("color = ").append(getColor());
        out.append(']');
        return out.toString();
    }
}
