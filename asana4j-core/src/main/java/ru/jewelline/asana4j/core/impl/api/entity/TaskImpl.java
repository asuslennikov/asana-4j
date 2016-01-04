package ru.jewelline.asana4j.core.impl.api.entity;

import ru.jewelline.asana4j.api.entity.Task;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityImpl;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldReader;
import ru.jewelline.asana4j.http.HttpMethod;

import java.util.Arrays;
import java.util.List;

public class TaskImpl extends ApiEntityImpl<TaskImpl> implements Task {

    private long id;
    private String name;
    private User assignee;
    private String assigneeStatus;
    private boolean completed;
    private String notes;

    public TaskImpl(ApiEntityContext context) {
        super(TaskImpl.class, context);
    }

    @Override
    protected List<JsonFieldReader<TaskImpl>> getFieldReaders() {
        return Arrays.<JsonFieldReader<TaskImpl>>asList(TaskImplProcessor.values());
    }

    @Override
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public User getAssignee() {
        return this.assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    @Override
    public String getAssigneeStatus() {
        return this.assigneeStatus;
    }

    public void setAssigneeStatus(String assigneeStatus) {
        this.assigneeStatus = assigneeStatus;
    }

    @Override
    public boolean isCompleted() {
        return this.completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return id == ((TaskImpl) o).id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("Task [");
        out.append("id = ").append(getId());
        out.append(", name = ").append(getName());
        out.append(", completed = ").append(isCompleted());
        out.append(']');
        return out.toString();
    }

    @Override
    public void delete() {
        getContext().newRequest()
                .path("tasks/" + getId())
                .buildAs(HttpMethod.DELETE)
                .execute();
    }
}
