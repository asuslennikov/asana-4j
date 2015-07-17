package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import ru.jewelline.asana4j.api.ApiException;
import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.asana4j.core.impl.api.entity.writers.WorkspaceImplWriter;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class WorkspaceImplTest {

    private static JSONObject getJsonResponse() {
        JSONObject json = new JSONObject();
        json.put(WorkspaceImplWriter.ID.getFieldName(), 36641231);
        json.put(WorkspaceImplWriter.NAME.getFieldName(), "workspace");
        return json;
    }

    @Test
    public void test_hasConstructorWithoutParameters(){
        new WorkspaceImpl();
        // asserts no exceptions
    }

    @Test
    public void test_implementsApiEntity(){
        assertThat(new WorkspaceImpl()).isInstanceOf(ApiEntity.class);
    }

    @Test
    public void test_tryFillFromNull(){
        WorkspaceImpl workspace = new WorkspaceImpl();
        assertThat(workspace.fromJson(null)).isNull();
    }

    @Test(expected = ApiException.class)
    public void test_fillFromIncorrectJson(){
        JSONObject json = new JSONObject();
        json.put("data", getJsonResponse());
        new WorkspaceImpl().fromJson(json);
    }

    @Test
    public void test_fillFromJsonWithMissedId(){
        JSONObject json = getJsonResponse();
        json.remove(WorkspaceImplWriter.ID.getFieldName());
        try {
            new WorkspaceImpl().fromJson(json);
        } catch (ApiException ex){
            assertThat(ex.getErrorCode() == ApiException.INCORRECT_RESPONSE_FORMAT);
            assertThat(ex.getMessage()).contains("'" + WorkspaceImplWriter.ID.getFieldName() + "'");
        }
    }

    @Test
    public void test_fillFromJsonWithBadId(){
        JSONObject json = getJsonResponse();
        json.put(WorkspaceImplWriter.ID.getFieldName(), "string_id");
        try {
            new WorkspaceImpl().fromJson(json);
        } catch (ApiException ex){
            assertThat(ex.getErrorCode() == ApiException.INCORRECT_RESPONSE_FIELD_FORMAT);
            assertThat(ex.getMessage()).contains("'" + WorkspaceImplWriter.ID.getFieldName() + "'");
        }
    }

    @Test
    public void test_fillFromJsonWithMissedName(){
        JSONObject json = getJsonResponse();
        json.remove(WorkspaceImplWriter.NAME.getFieldName());
        try {
            new WorkspaceImpl().fromJson(json);
        } catch (ApiException ex){
            assertThat(ex.getErrorCode() == ApiException.INCORRECT_RESPONSE_FORMAT);
            assertThat(ex.getMessage()).contains("'" + WorkspaceImplWriter.NAME.getFieldName() + "'");
        }
    }

    @Test
    public void test_fillFromJsonWithBadName(){
        JSONObject json = getJsonResponse();
        json.put(WorkspaceImplWriter.NAME.getFieldName(), 123);
        try {
            new WorkspaceImpl().fromJson(json);
        } catch (ApiException ex){
            assertThat(ex.getErrorCode() == ApiException.INCORRECT_RESPONSE_FIELD_FORMAT);
            assertThat(ex.getMessage()).contains("'" + WorkspaceImplWriter.NAME.getFieldName() + "'");
        }
    }

    @Test
    public void test_fillWithCorrectJson(){
        JSONObject json = getJsonResponse();
        Workspace workspace = new WorkspaceImpl().fromJson(json);
        assertThat(workspace).isNotNull();
        assertThat(workspace.getId()).isEqualTo(36641231);
        assertThat(workspace.getName()).isEqualTo("workspace");
    }
}
