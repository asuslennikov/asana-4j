package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.request.api.ApiException;
import ru.jewelline.request.api.entity.JsonEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@RunWith(MockitoJUnitRunner.class)
public class WorkspaceImplTest {

    private static JSONObject getJsonResponse() {
        JSONObject json = new JSONObject();
        json.put(WorkspaceImplProcessor.ID.getFieldName(), 36641231);
        json.put(WorkspaceImplProcessor.NAME.getFieldName(), "workspace");
        json.put(WorkspaceImplProcessor.ORGANISATION.getFieldName(), Boolean.TRUE);
        return json;
    }
    
    private WorkspaceImpl testInstance(){
        return new WorkspaceImpl(null);
    }

    @Test
    public void test_implementsJsonEntity() {
        assertThat(testInstance()).isInstanceOf(JsonEntity.class);
    }

    @Test
    public void test_tryFillFromNull() {
        WorkspaceImpl workspace = testInstance();
        assertThat(workspace.fromJson(null)).isNull();
    }

    @Test(expected = ApiException.class)
    public void test_fillFromIncorrectJson() {
        JSONObject json = new JSONObject();
        json.put("data", getJsonResponse());
        testInstance().fromJson(json);
    }

    @Test
    public void test_fillFromJsonWithBadId() {
        JSONObject json = getJsonResponse();
        json.put(WorkspaceImplProcessor.ID.getFieldName(), "string_id");
        try {
            testInstance().fromJson(json);
        } catch (ApiException ex) {
            assertThat(ex.getErrorCode() == ApiException.INCORRECT_RESPONSE_FIELD_FORMAT);
            assertThat(ex.getMessage()).contains("'" + WorkspaceImplProcessor.ID.getFieldName() + "'");
            return;
        }
        fail("Exception expected!");
    }

    @Test
    public void test_fillFromJsonWithNullId() {
        JSONObject json = getJsonResponse();
        json.put(WorkspaceImplProcessor.ID.getFieldName(), JSONObject.NULL);
        WorkspaceImpl workspace = testInstance().fromJson(json);
        assertThat(workspace.getId()).isEqualTo(0);
    }

    @Test
    public void test_fillFromJsonWithBadName() {
        JSONObject json = getJsonResponse();
        json.put(WorkspaceImplProcessor.NAME.getFieldName(), 123);
        try {
            testInstance().fromJson(json);
        } catch (ApiException ex) {
            assertThat(ex.getErrorCode() == ApiException.INCORRECT_RESPONSE_FIELD_FORMAT);
            assertThat(ex.getMessage()).contains("'" + WorkspaceImplProcessor.NAME.getFieldName() + "'");
            return;
        }
        fail("Exception expected!");
    }

    @Test
    public void test_fillFromJsonWithNullName() {
        JSONObject json = getJsonResponse();
        json.put(WorkspaceImplProcessor.NAME.getFieldName(), JSONObject.NULL);
        WorkspaceImpl workspace = testInstance().fromJson(json);
        assertThat(workspace.getName()).isNull();
    }

    @Test
    public void test_fillWithCorrectJson() {
        JSONObject json = getJsonResponse();
        Workspace workspace = testInstance().fromJson(json);
        assertThat(workspace).isNotNull();
        assertThat(workspace.getId()).isEqualTo(36641231);
        assertThat(workspace.getName()).isEqualTo("workspace");
    }

    @Test
    public void test_fillFromJsonWithNullOrganisation() {
        JSONObject json = getJsonResponse();
        json.put(WorkspaceImplProcessor.ORGANISATION.getFieldName(), JSONObject.NULL);
        WorkspaceImpl workspace = testInstance().fromJson(json);
        assertThat(workspace.isOrganisation()).isFalse();
    }

    @Test
    public void test_fillFromJsonWithBadOrganisation() {
        JSONObject json = getJsonResponse();
        json.put(WorkspaceImplProcessor.ORGANISATION.getFieldName(), "bla-bla");
        try {
            testInstance().fromJson(json);
        } catch (ApiException ex) {
            assertThat(ex.getErrorCode() == ApiException.INCORRECT_RESPONSE_FIELD_FORMAT);
            assertThat(ex.getMessage()).contains("'" + WorkspaceImplProcessor.ORGANISATION.getFieldName() + "'");
            return;
        }
        fail("Exception expected!");
    }
}
