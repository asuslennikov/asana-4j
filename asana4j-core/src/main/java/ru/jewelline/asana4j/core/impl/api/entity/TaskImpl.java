package ru.jewelline.asana4j.core.impl.api.entity;

import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.api.entity.Project;
import ru.jewelline.asana4j.api.entity.Stories;
import ru.jewelline.asana4j.api.entity.Task;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityImpl;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldReader;
import ru.jewelline.asana4j.http.HttpMethod;

import java.util.Arrays;
import java.util.List;

public class TaskImpl extends ApiEntityImpl<TaskImpl> implements Task {

    private long id;
    private String name;
    private User assignee;
    private AssigneeStatus assigneeStatus;
    private String createdAt;
    private boolean completed;
    private String completedAt;
    private String dueOn;
    private String dueAt;
    private List<User> followers;
    private boolean hearted;
    private List<User> heartsAuthors;
    private String modifiedAt;
    private int numberOfHearts;
    private List<Project> projects;
    private Task parent;
    private String notes;
    private Workspace workspace;

    private TaskUpdater updater;

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
    public AssigneeStatus getAssigneeStatus() {
        return this.assigneeStatus;
    }

    public void setAssigneeStatus(AssigneeStatus assigneeStatus) {
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
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    @Override
    public String getDueOn() {
        return dueOn;
    }

    public void setDueOn(String dueOn) {
        this.dueOn = dueOn;
    }

    @Override
    public String getDueAt() {
        return dueAt;
    }

    public void setDueAt(String dueAt) {
        this.dueAt = dueAt;
    }

    @Override
    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
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
    public String getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    @Override
    public int getNumberOfHearts() {
        return numberOfHearts;
    }

    public void setNumberOfHearts(int numberOfHearts) {
        this.numberOfHearts = numberOfHearts;
    }

    @Override
    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public Task getParent() {
        return parent;
    }

    public void setParent(Task parent) {
        this.parent = parent;
    }

    @Override
    public Workspace getWorkspace() {
        return workspace;
    }

    @Override
    public boolean isSection() {
        return getName() != null && getName().endsWith(":");
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
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

    @Override
    public Task.TaskUpdater startUpdate() {
        if (this.updater != null) {
            throw new IllegalStateException("Another update process is in progress");
        }
        this.updater = new TaskImplUpdater(this);
        return this.updater;
    }

    public void stopUpdate() {
        this.updater = null;
    }

    @Override
    public TaskCreator createSubTask() {
        return new TaskImplCreator(this.getContext()).setParent(this.getId());
    }

    @Override
    public PagedList<Task> getSubTasks() {
        return getContext().newRequest()
                .path("tasks/" + getId() + "/subtasks")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection(this.getContext().getDeserializer(TaskImpl.class));
    }

    @Override
    public void setParentTask(Long parentTaskId) {
        getContext().newRequest()
                .path("tasks/" + getId() + "/setParent")
                .setQueryParameter("parent", String.valueOf(parentTaskId))
                .buildAs(HttpMethod.POST)
                .execute();
    }

    @Override
    public AddProjectBuilder addProject(long projectId) {
        return new AddProjectBuilderImpl(getContext(), getId(), projectId);
    }

    @Override
    public void removeProject(long projectId) {
        getContext().newRequest()
                .path("/tasks/" + getId() + "/removeProject")
                .setQueryParameter("project", String.valueOf(projectId))
                .buildAs(HttpMethod.POST)
                .execute();
    }

    @Override
    public PagedList<Stories> getStories(RequestModifier... requestModifiers) {
        return getContext().newRequest(requestModifiers)
                .path("/tasks/" + getId() + "/stories")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection(getContext().getDeserializer(StoriesImpl.class));
    }

    private static class AddProjectBuilderImpl implements AddProjectBuilder {
        private final ApiEntityContext entityContext;
        private final long taskId;
        private final long projectId;

        private Long insertAfter;
        private Long insertBefore;
        private Long section;

        public AddProjectBuilderImpl(ApiEntityContext entityContext, long taskId, long projectId) {
            this.entityContext = entityContext;
            this.taskId = taskId;
            this.projectId = projectId;
        }

        @Override
        public void update() {
            this.entityContext.newRequest()
                    .path("tasks/" + this.taskId + "/addProject")
                    .setQueryParameter("project", String.valueOf(projectId))
                    .setQueryParameter("insertAfter", String.valueOf(this.insertAfter))
                    .setQueryParameter("insertBefore", String.valueOf(this.insertBefore))
                    .setQueryParameter("section", String.valueOf(this.section))
                    .buildAs(HttpMethod.POST)
                    .execute();
        }

        @Override
        public AddProjectBuilder section(Long sectionId) {
            this.section = sectionId;
            return this;
        }

        @Override
        public AddProjectBuilder insertBefore(Long taskId) {
            this.insertBefore = taskId;
            return this;
        }

        @Override
        public AddProjectBuilder insertAfter(Long taskId) {
            this.insertAfter = taskId;
            return this;
        }
    }
}
