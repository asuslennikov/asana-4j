package ru.jewelline.asana4j.api.beans;

import ru.jewelline.asana4j.api.entities.Story;
import ru.jewelline.asana4j.api.entities.Task;
import ru.jewelline.asana4j.api.entities.User;

import java.util.List;

public class StoryBean extends BaseBean implements Story {

    private long id;
    private String createdAt;
    private User createdBy;
    private boolean hearted;
    private List<User> heartsAuthors;
    private int numberOfHearts;
    private String text;
    private String htmlText;
    private Task target;
    private String source;
    private String type;

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public boolean isHearted() {
        return hearted;
    }

    public void setHearted(boolean hearted) {
        this.hearted = hearted;
    }

    @Override
    public List<User> getHeartsAuthors() {
        return heartsAuthors;
    }

    public void setHeartsAuthors(List<User> heartsAuthors) {
        this.heartsAuthors = heartsAuthors;
    }

    @Override
    public int getNumberOfHearts() {
        return numberOfHearts;
    }

    public void setNumberOfHearts(int numberOfHearts) {
        this.numberOfHearts = numberOfHearts;
    }

    @Override
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getHtmlText() {
        return htmlText;
    }

    public void setHtmlText(String htmlText) {
        this.htmlText = htmlText;
    }

    @Override
    public Task getTarget() {
        return target;
    }

    public void setTarget(Task target) {
        this.target = target;
    }

    @Override
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
