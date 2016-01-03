package ru.jewelline.asana4j.core.impl.api.entity;

import ru.jewelline.asana4j.api.entity.Project;
import ru.jewelline.asana4j.api.entity.ProjectStatus;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityDeserializer;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldWriter;
import ru.jewelline.asana4j.http.HttpMethod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProjectImplUpdater implements Project.ProjectUpdater {

    private final ProjectImpl target;
    private final List<JsonFieldWriter<ProjectImpl>> updatedFields;

    private String name;
    private User owner;
    private ProjectStatusImpl status;
    private String dueDate;
    private boolean archived;
    private boolean pub;
    private String color;
    private String notes;

    public ProjectImplUpdater(ProjectImpl target) {
        this.target = target;
        this.updatedFields = new ArrayList<>();
    }

    @Override
    public ProjectImplUpdater setName(String name) {
        this.name = name;
        this.updatedFields.add(ProjectImplProcessor.NAME);
        return this;
    }

    @Override
    public ProjectImplUpdater setOwner(User owner) {
        this.owner = owner;
        this.updatedFields.add(ProjectImplProcessor.OWNER);
        return this;
    }

    @Override
    public Project.ProjectUpdater setStatus(ProjectStatus.Color color, String text, User author) {
        this.status = this.target.getContext().getEntity(ProjectStatusImpl.class);
        this.status.setColor(color);
        this.status.setText(text);
        this.status.setAuthor(author);
        this.updatedFields.add(ProjectImplProcessor.CURRENT_STATUS);
        return this;
    }

    @Override
    public ProjectImplUpdater setDueDate(String dueDate) {
        this.dueDate = dueDate;
        this.updatedFields.add(ProjectImplProcessor.DUE_DATE);
        return this;
    }

    @Override
    public ProjectImplUpdater setArchived(boolean archived) {
        this.archived = archived;
        this.updatedFields.add(ProjectImplProcessor.ARCHIVED);
        return this;
    }

    @Override
    public ProjectImplUpdater setPublic(boolean isPublic) {
        this.pub = isPublic;
        this.updatedFields.add(ProjectImplProcessor.PUBLIC);
        return this;
    }

    @Override
    public ProjectImplUpdater setColor(String color) {
        this.color = color;
        this.updatedFields.add(ProjectImplProcessor.COLOR);
        return this;
    }

    @Override
    public ProjectImplUpdater setNotes(String notes) {
        this.notes = notes;
        this.updatedFields.add(ProjectImplProcessor.NOTES);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return this.target.equals(((ProjectImplUpdater) o).target);
    }

    @Override
    public int hashCode() {
        return this.target.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("ProjectUpdater [");
        out.append("id = ").append(this.target.getId());
        out.append(']');
        return out.toString();
    }

    @Override
    public Project update() {
        // even if this fields are not modified they will be updated after the request
        this.target.setName(this.name);
        this.target.setOwner(this.owner);
        this.target.setCurrentStatus(this.status);
        this.target.setDueDate(this.dueDate);
        this.target.setColor(this.color);
        this.target.setNotes(this.notes);
        this.target.setArchived(this.archived);
        this.target.setPublic(this.pub);
        this.target.stopUpdate(this.updatedFields);
        return this.target.getContext()
                .newRequest()
                .path("projects/" + this.target.getId())
                .setEntity(this.target)
                .buildAs(HttpMethod.PUT)
                .execute()
                .asApiObject(new ApiEntityDeserializer<>(this.target));
    }

    @Override
    public Project abandon() {
        this.target.stopUpdate(Collections.<JsonFieldWriter<ProjectImpl>>emptyList());
        return this.target;
    }
}
