package ru.jewelline.asana4j.api.beans;

import ru.jewelline.asana4j.api.entities.Project;
import ru.jewelline.asana4j.api.entities.ProjectColor;
import ru.jewelline.asana4j.api.entities.ProjectStatus;
import ru.jewelline.asana4j.api.entities.User;
import ru.jewelline.asana4j.impl.entity.common.ApiEntityDeserializer;
import ru.jewelline.asana4j.impl.entity.io.FieldsUpdater;
import ru.jewelline.asana4j.impl.entity.io.SimpleFieldsUpdater;
import ru.jewelline.request.http.HttpMethod;

import java.util.Map;

class ProjectImplUpdater extends FieldsUpdater implements Project.ProjectUpdater {

    private final ProjectImpl target;

    public ProjectImplUpdater(ProjectImpl target) {
        this.target = target;
    }

    @Override
    public Project.ProjectUpdater setName(String name) {
        putField(ProjectImplProcessor.NAME.getFieldName(), name);
        return this;
    }

    @Override
    public Project.ProjectUpdater setOwner(User user) {
        putField(ProjectImplProcessor.OWNER.getFieldName(), user);
        return this;
    }

    @Override
    public Project.ProjectUpdater setStatus(ProjectStatus.Color color, String text) {
        Map<String, Object> status = new SimpleFieldsUpdater()
                .setField(ProjectStatusImplProcessor.COLOR.getFieldName(), color.getColorCode())
                .setField(ProjectStatusImplProcessor.TEXT.getFieldName(), text)
                .wrapAsMap();
        putField(ProjectImplProcessor.CURRENT_STATUS.getFieldName(), status);
        return this;
    }

    @Override
    public Project.ProjectUpdater setDueDate(String date) {
        putField(ProjectImplProcessor.DUE_DATE.getFieldName(), date);
        return this;
    }

    @Override
    public Project.ProjectUpdater setColor(ProjectColor color) {
        putField(ProjectImplProcessor.COLOR.getFieldName(), color.getColorCode());
        return this;
    }

    @Override
    public Project.ProjectUpdater setNotes(String notes) {
        putField(ProjectImplProcessor.NOTES.getFieldName(), notes);
        return this;
    }

    @Override
    public Project.ProjectUpdater setArchived(boolean isArchived) {
        putField(ProjectImplProcessor.ARCHIVED.getFieldName(), isArchived);
        return this;
    }

    @Override
    public Project.ProjectUpdater setPublic(boolean isPublic) {
        putField(ProjectImplProcessor.PUBLIC.getFieldName(), isPublic);
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
        this.target.stopUpdate();
        return this.target.getContext()
                .apiRequest()
                .path("projects/" + this.target.getId())
                .setEntity(wrapFieldsAsEntity())
                .buildAs(HttpMethod.PUT)
                .execute()
                .asApiObject(new ApiEntityDeserializer<>(this.target));
    }

    @Override
    public Project abandon() {
        this.target.stopUpdate();
        return this.target;
    }
}
