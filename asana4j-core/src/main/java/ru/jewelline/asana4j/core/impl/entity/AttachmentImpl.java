package ru.jewelline.asana4j.core.impl.entity;

import ru.jewelline.asana4j.core.api.entity.Attachment;
import ru.jewelline.asana4j.core.api.entity.Task;
import ru.jewelline.asana4j.core.impl.entity.common.ApiEntityContext;
import ru.jewelline.asana4j.core.impl.entity.common.ApiEntityImpl;
import ru.jewelline.asana4j.core.impl.entity.common.JsonFieldReader;
import ru.jewelline.asana4j.utils.StringUtils;
import ru.jewelline.request.http.HttpMethod;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

public class AttachmentImpl extends ApiEntityImpl<AttachmentImpl> implements Attachment {

    private static final int RESPONSE_OK_ANSWER_CODE = 200;

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
        return Arrays.<JsonFieldReader<AttachmentImpl>>asList(AttachmentImplProcessor.values());
    }

    @Override
    public long getId() {
        return id;
    }

    void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    @Override
    public String getCreatedAt() {
        return createdAt;
    }

    void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String getDownloadUrl() {
        return downloadUrl;
    }

    void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    @Override
    public Host getHost() {
        return host;
    }

    void setHost(Host host) {
        this.host = host;
    }

    @Override
    public Task getParent() {
        return parent;
    }

    void setParent(Task parent) {
        this.parent = parent;
    }

    @Override
    public String getViewUrl() {
        return viewUrl;
    }

    void setViewUrl(String viewUrl) {
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

    @Override
    public boolean download(OutputStream destinationStream) {
        if (!StringUtils.emptyOrOnlyWhiteSpace(getDownloadUrl())) {
            return getContext().httpRequest()
                    .path(getDownloadUrl())
                    .buildAs(HttpMethod.GET)
                    .sendAndReadResponse(destinationStream)
                    .code() == RESPONSE_OK_ANSWER_CODE;
        }
        return false;
    }

    @Override
    public boolean downloadPreview(OutputStream destinationStream) {
        if (!StringUtils.emptyOrOnlyWhiteSpace(getDownloadUrl())) {
            return getContext().httpRequest()
                    .path(getViewUrl())
                    .buildAs(HttpMethod.GET)
                    .sendAndReadResponse(destinationStream)
                    .code() == RESPONSE_OK_ANSWER_CODE;
        }
        return false;
    }
}
