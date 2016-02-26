package ru.jewelline.asana4j.core.impl.api.entity;

import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityContext;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityImpl;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldReader;
import ru.jewelline.request.http.HttpMethod;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class UserImpl extends ApiEntityImpl<UserImpl> implements User {

    private static final int RESPONSE_OK_ANSWER_CODE = 200;

    private long id;
    private String name;
    private String email;
    private Map<PhotoSize, String> photoUrl;
    private List<Workspace> workspaces;

    public UserImpl(ApiEntityContext context) {
        super(UserImpl.class, context);
    }

    @Override
    protected List<JsonFieldReader<UserImpl>> getFieldReaders() {
        return Arrays.<JsonFieldReader<UserImpl>>asList(UserImplProcessor.values());
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public Map<PhotoSize, String> getPhotoUrl() {
        return this.photoUrl;
    }

    @Override
    public boolean downloadPhoto(PhotoSize size, OutputStream destination) {
        if (size != null
                && this.getPhotoUrl() != null
                && this.getPhotoUrl().containsKey(size)){
            return getContext().newRequest()
                    .path(this.getPhotoUrl().get(size))
                    .buildAs(HttpMethod.GET)
                    .sendAndReadResponse(destination)
                    .code() == RESPONSE_OK_ANSWER_CODE;
        }
        return false;
    }

    @Override
    public List<Workspace> getWorkspaces() {
        return this.workspaces;
    }

    void setId(long id) {
        this.id = id;
    }

    void setName(String name) {
        this.name = name;
    }

    void setEmail(String email) {
        this.email = email;
    }

    void setPhotoUrl(Map<PhotoSize, String> photoUrl) {
        this.photoUrl = photoUrl;
    }

    void setWorkspaces(List<Workspace> workspaces) {
        this.workspaces = workspaces;
    }

    @Override
    public boolean equals(Object candidate) {
        if (this == candidate){
            return true;
        }
        if (candidate == null || getClass() != candidate.getClass()){
            return false;
        }
        return id == ((UserImpl) candidate).id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("User [");
        out.append("id = ").append(getId());
        out.append(", name = ").append(getName());
        out.append(", email = ").append(getEmail());
        out.append(']');
        return out.toString();
    }
}
