package ru.jewelline.asana4j.core.impl.entity;

import ru.jewelline.asana4j.core.api.entity.Attachment;
import ru.jewelline.asana4j.core.api.entity.ExternalData;
import ru.jewelline.asana4j.core.api.entity.Project;
import ru.jewelline.asana4j.core.api.entity.Story;
import ru.jewelline.asana4j.core.api.entity.Tag;
import ru.jewelline.asana4j.core.api.entity.Task;
import ru.jewelline.asana4j.core.api.entity.User;
import ru.jewelline.asana4j.core.api.entity.Workspace;
import ru.jewelline.asana4j.core.impl.entity.common.ApiEntityContext;
import ru.jewelline.asana4j.core.impl.entity.common.ApiEntityImpl;
import ru.jewelline.asana4j.core.impl.entity.common.JsonFieldReader;
import ru.jewelline.asana4j.core.impl.entity.io.MultipartFormEntity;
import ru.jewelline.asana4j.core.impl.entity.io.SimpleFieldsUpdater;
import ru.jewelline.request.api.PagedList;
import ru.jewelline.request.api.modifiers.RequestModifier;
import ru.jewelline.request.http.HttpMethod;

import java.io.InputStream;
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
    private ExternalData externalData;
    private List<User> followers;
    private boolean hearted;
    private List<User> heartsAuthors;
    private String modifiedAt;
    private int numberOfHearts;
    private List<Project> projects;
    private Task parent;
    private String notes;
    private Workspace workspace;
    private List<Tag> tags;

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

    void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    void setName(String name) {
        this.name = name;
    }

    @Override
    public User getAssignee() {
        return this.assignee;
    }

    void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    @Override
    public AssigneeStatus getAssigneeStatus() {
        return this.assigneeStatus;
    }

    void setAssigneeStatus(AssigneeStatus assigneeStatus) {
        this.assigneeStatus = assigneeStatus;
    }

    @Override
    public boolean isCompleted() {
        return this.completed;
    }

    void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String getNotes() {
        return this.notes;
    }

    void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String getCreatedAt() {
        return createdAt;
    }

    void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String getCompletedAt() {
        return completedAt;
    }

    void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    @Override
    public String getDueOn() {
        return dueOn;
    }

    void setDueOn(String dueOn) {
        this.dueOn = dueOn;
    }

    @Override
    public String getDueAt() {
        return dueAt;
    }

    @Override
    public ExternalData getExternalData() {
        return externalData;
    }

    void setExternalData(ExternalData externalData) {
        this.externalData = externalData;
    }

    public void setDueAt(String dueAt) {
        this.dueAt = dueAt;
    }

    @Override
    public List<User> getFollowers() {
        return followers;
    }

    void setFollowers(List<User> followers) {
        this.followers = followers;
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
    public String getModifiedAt() {
        return modifiedAt;
    }

    void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    @Override
    public int getNumberOfHearts() {
        return numberOfHearts;
    }

    void setNumberOfHearts(int numberOfHearts) {
        this.numberOfHearts = numberOfHearts;
    }

    @Override
    public List<Project> getProjects() {
        return projects;
    }

    void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public Task getParent() {
        return parent;
    }

    void setParent(Task parent) {
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

    void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    @Override
    public List<Tag> getTags() {
        return tags;
    }

    void setTags(List<Tag> tags) {
        this.tags = tags;
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
        getContext().apiRequest()
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

    void stopUpdate() {
        this.updater = null;
    }

    @Override
    public TaskCreator createSubTask() {
        return new TaskImplCreator(this.getContext()).setParent(this.getId());
    }

    @Override
    public PagedList<Task> getSubTasks() {
        return getContext().apiRequest()
                .path("tasks/" + getId() + "/subtasks")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection(this.getContext().getDeserializer(TaskImpl.class));
    }

    @Override
    public void setParentTask(Long parentTaskId) {
        getContext().apiRequest()
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
        getContext().apiRequest()
                .path("/tasks/" + getId() + "/removeProject")
                .setQueryParameter("project", String.valueOf(projectId))
                .buildAs(HttpMethod.POST)
                .execute();
    }

    @Override
    public PagedList<Story> getStories(RequestModifier... requestModifiers) {
        return getContext().apiRequest(requestModifiers)
                .path("tasks/" + getId() + "/stories")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection(getContext().getDeserializer(StoryImpl.class));
    }

    @Override
    public Story addComment(String text, RequestModifier... requestModifiers) {
        return getContext().apiRequest(requestModifiers)
                .path("tasks/" + getId() + "/stories")
                .setEntity(new SimpleFieldsUpdater()
                        .setField("text", text)
                        .wrapFieldsAsEntity())
                .buildAs(HttpMethod.POST)
                .execute()
                .asApiObject(getContext().getDeserializer(StoryImpl.class));
    }

    @Override
    public PagedList<Attachment> getAttachments(RequestModifier... requestModifiers) {
        return getContext().apiRequest(requestModifiers)
                .path("tasks/" + getId() + "/attachments")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection(getContext().getDeserializer(AttachmentImpl.class));
    }

    @Override
    public Attachment uploadAttachment(String name, InputStream attachment) {
        MultipartFormEntity entity = new MultipartFormEntity(name, attachment);
        return getContext().apiRequest()
                .path("tasks/" + getId() + "/attachments")
                .setHeader("Content-Type", "multipart/form-data; boundary=" + entity.getBoundary())
                .setEntity(entity)
                .buildAs(HttpMethod.POST)
                .execute()
                .asApiObject(getContext().getDeserializer(AttachmentImpl.class));
    }

    @Override
    public void addTag(long tagId) {
        getContext().apiRequest()
                .path("tasks/" + getId() + "/addTag")
                .setEntity(new SimpleFieldsUpdater()
                        .setField("tag", tagId)
                        .wrapFieldsAsEntity())
                .buildAs(HttpMethod.POST)
                .execute();
        // TODO reload after operation?
    }

    @Override
    public void removeTag(long tagId) {
        getContext().apiRequest()
                .path("tasks/" + getId() + "/removeTag")
                .setEntity(new SimpleFieldsUpdater()
                        .setField("tag", tagId)
                        .wrapFieldsAsEntity())
                .buildAs(HttpMethod.POST)
                .execute();
        // TODO reload after operation?
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
            this.entityContext.apiRequest()
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
