package ru.jewelline.asana4j.core.impl.api.entity;

import ru.jewelline.asana4j.api.entity.Task;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.core.impl.api.entity.io.FieldsUpdater;

public abstract class TaskImplBuilder<T extends Task.TaskBuilder> extends FieldsUpdater implements Task.TaskBuilder<T> {

    private final Class<T> implClass;

    public TaskImplBuilder(Class<T> implClass) {
        super();
        this.implClass = implClass;
    }

    @Override
    public T setAssignee(User assignee) {
        putField(TaskImplProcessor.ASSIGNEE.getFieldName(), assignee);
        return this.implClass.cast(this);
    }

    @Override
    public T setAssigneeStatus(Task.AssigneeStatus assigneeStatus) {
        putField(TaskImplProcessor.ASSIGNEE_STATUS.getFieldName(),
                assigneeStatus != null ? assigneeStatus.getStatusCode() : null);
        return this.implClass.cast(this);
    }

    @Override
    public T setCompleted(boolean isCompleted) {
        putField(TaskImplProcessor.COMPLETED.getFieldName(), isCompleted);
        return this.implClass.cast(this);
    }

    @Override
    public T setDueOn(String dueOn) {
        putField(TaskImplProcessor.DUE_ON.getFieldName(), dueOn);
        return this.implClass.cast(this);
    }

    @Override
    public T setDueAt(long dueAt) {
        putField(TaskImplProcessor.DUE_AT.getFieldName(), dueAt);
        return this.implClass.cast(this);
    }

    @Override
    public T setHearted(boolean isHearted) {
        putField(TaskImplProcessor.HEARTED.getFieldName(), isHearted);
        return this.implClass.cast(this);
    }

    @Override
    public T setName(String name) {
        putField(TaskImplProcessor.NAME.getFieldName(), name);
        return this.implClass.cast(this);
    }

    @Override
    public T setNotes(String notes) {
        putField(TaskImplProcessor.NOTES.getFieldName(), notes);
        return this.implClass.cast(this);
    }
}
