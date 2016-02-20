package ru.jewelline.asana4j.impl.entity;

import ru.jewelline.asana4j.api.entity.Story;
import ru.jewelline.asana4j.api.entity.Task;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.impl.entity.common.ApiEntityContext;
import ru.jewelline.asana4j.impl.entity.common.ApiEntityImpl;
import ru.jewelline.asana4j.impl.entity.common.JsonFieldReader;

import java.util.Arrays;
import java.util.List;

public class StoryImpl extends ApiEntityImpl<StoryImpl> implements Story {

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

    public StoryImpl(ApiEntityContext context) {
        super(StoryImpl.class, context);
    }

    @Override
    protected List<JsonFieldReader<StoryImpl>> getFieldReaders() {
        return Arrays.<JsonFieldReader<StoryImpl>>asList(StoryImplProcessor.values());
    }

    @Override
    public long getId() {
        return id;
    }

    void setId(long id) {
        this.id = id;
    }

    @Override
    public String getCreatedAt() {
        return createdAt;
    }

    void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public User getCreatedBy() {
        return createdBy;
    }

    void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public boolean isHearted() {
        return hearted;
    }

    void setHearted(boolean hearted) {
        this.hearted = hearted;
    }

    @Override
    public List<User> getHeartsAuthors() {
        return heartsAuthors;
    }

    void setHeartsAuthors(List<User> heartsAuthors) {
        this.heartsAuthors = heartsAuthors;
    }

    @Override
    public int getNumberOfHearts() {
        return numberOfHearts;
    }

    void setNumberOfHearts(int numberOfHearts) {
        this.numberOfHearts = numberOfHearts;
    }

    @Override
    public String getText() {
        return text;
    }

    void setText(String text) {
        this.text = text;
    }

    @Override
    public String getHtmlText() {
        return htmlText;
    }

    void setHtmlText(String htmlText) {
        this.htmlText = htmlText;
    }

    @Override
    public Task getTarget() {
        return target;
    }

    void setTarget(Task target) {
        this.target = target;
    }

    @Override
    public String getSource() {
        return source;
    }

    void setSource(String source) {
        this.source = source;
    }

    @Override
    public String getType() {
        return type;
    }

    void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return id == ((StoryImpl) o).id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("Story [");
        out.append("id = ").append(getId());
        out.append(", type = ").append(getType());
        out.append(", text = ").append(getText());
        out.append(']');
        return out.toString();
    }
}
