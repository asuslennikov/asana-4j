package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONObject;
import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.asana4j.core.impl.api.entity.processors.WorkspaceImplProcessor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class WorkspaceImpl extends ApiEntityImpl<Workspace> implements Workspace {

    private long id;
    private String name;
    private boolean organisation;

    public WorkspaceImpl() {
        super(Workspace.class);
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

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isOrganisation() {
        return organisation;
    }

    public void setOrganisation(boolean organisation) {
        this.organisation = organisation;
    }

    @Override
    public boolean equals(Object candidate) {
        if (this == candidate){
            return true;
        }
        if (candidate == null || getClass() != candidate.getClass()){
            return false;
        }
        return id == ((WorkspaceImpl) candidate).id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("Workspace [");
        out.append("id = ").append(getId());
        out.append(", name = ").append(getName());
        out.append(", organisation = ").append(isOrganisation());
        out.append("]");
        return out.toString();
    }

    @Override
    protected List<ApiEntityFieldWriter<Workspace, WorkspaceImpl>> getFieldWriters() {
        return Arrays.<ApiEntityFieldWriter<Workspace, WorkspaceImpl>>asList(WorkspaceImplProcessor.values());
    }

    @Override
    public JSONObject asJson() {
        JSONObject object = new JSONObject();
        object.put("id", getId());
        object.put("name", getName());
        JSONObject data = new JSONObject();
        data.put("data", object);
        return data;
    }

    @Override
    public InputStream getSerialized() {
        return new ByteArrayInputStream(asJson().toString().getBytes());
    }
}
