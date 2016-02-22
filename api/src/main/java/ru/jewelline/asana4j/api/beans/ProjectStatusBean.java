package ru.jewelline.asana4j.api.beans;

import ru.jewelline.asana4j.api.entities.ProjectStatus;
import ru.jewelline.asana4j.api.entities.User;

public class ProjectStatusBean implements ProjectStatus {

    private Color color;
    private String text;
    private User author;

    @Override
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
