package ru.jewelline.asana4j.core.impl.api.entity;

import ru.jewelline.asana4j.api.entity.Attachment;
import ru.jewelline.asana4j.api.entity.Task;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityImpl;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldReader;

import java.util.List;

public class AttachmentImpl extends ApiEntityImpl<AttachmentImpl> implements Attachment {

    private long id;
    private String name;
    private String createdAt;
    private String downloadUrl;
    private Host host;
    private Task parent;
    private String viewUrl;

    public AttachmentImpl(ApiEntityContext context) {
        super(AttachmentImpl.class, context);
    }

    @Override
    protected List<JsonFieldReader<AttachmentImpl>> getFieldReaders() {
        return null;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    @Override
    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    @Override
    public Task getParent() {
        return parent;
    }

    public void setParent(Task parent) {
        this.parent = parent;
    }

    @Override
    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return id == ((AttachmentImpl) o).id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("Attachment [");
        out.append("id = ").append(getId());
        out.append(", name = ").append(getName());
        out.append(']');
        return out.toString();
    }
}