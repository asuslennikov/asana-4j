package ru.jewelline.asana4j.core.impl.api.entity;

import ru.jewelline.asana4j.api.entity.Project;
import ru.jewelline.asana4j.api.entity.ProjectStatus;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityImpl;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldReader;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldWriter;
import ru.jewelline.asana4j.http.HttpMethod;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProjectImpl extends ApiEntityImpl<ProjectImpl> implements Project {

    private long id;
    private String name;
    private User owner;
    private ProjectStatus currentStatus;
    private String dueDate;
    private boolean archived;
    private boolean pub;
    private List<User> members;
    private Color color;
    private String notes;
    private Workspace workspace;

    private ProjectUpdater updater;
    private List<JsonFieldWriter<ProjectImpl>> updatedFields = Collections.emptyList();

    public ProjectImpl(ApiEntityContext context) {
        super(ProjectImpl.class, context);
    }

    @Override
    protected List<JsonFieldReader<ProjectImpl>> getFieldReaders() {
        return Arrays.<JsonFieldReader<ProjectImpl>>asList(ProjectImplProcessor.values());
    }

    @Override
    protected List<JsonFieldWriter<ProjectImpl>> getFieldWriters() {
        return this.updatedFields;
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
    public User getOwner() {
        return this.owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public ProjectStatus getCurrentStatus() {
        return this.currentStatus;
    }

    public void setCurrentStatus(ProjectStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    @Override
    public String getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public boolean isArchived() {
        return this.archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    @Override
    public boolean isPublic() {
        return this.pub;
    }

    public void setPublic(boolean isPublic) {
        this.pub = isPublic;
    }

    @Override
    public List<User> getMembers() {
        return this.members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public Workspace getWorkspace() {
        return this.workspace;
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
        return id == ((ProjectImpl) o).id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("Project [");
        out.append("id = ").append(getId());
        out.append(", name = ").append(getName());
        out.append(']');
        return out.toString();
    }

    @Override
    public void delete() {
        getContext().newRequest()
            .path("projects/" + getId())
            .buildAs(HttpMethod.DELETE)
            .execute();
    }

    @Override
    public ProjectUpdater startUpdate() {
        if (this.updater != null) {
            throw new IllegalStateException("Another update process is in progress");
        }
        this.updater = new ProjectImplUpdater(this);
        return this.updater;
    }

    public void stopUpdate(List<JsonFieldWriter<ProjectImpl>> updatedFields){
        this.updater = null;
        this.updatedFields = updatedFields;
    }
}
