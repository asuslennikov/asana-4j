package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import ru.jewelline.asana4j.api.ApiException;
import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.asana4j.api.entity.io.JsonEntity;

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

    @Test
    public void test_implementsApiEntity() {
        assertThat(new WorkspaceImpl(null)).isInstanceOf(JsonEntity.class);
    }

    @Test
    public void test_tryFillFromNull() {
        WorkspaceImpl workspace = new WorkspaceImpl(null);
        assertThat(workspace.fromJson(null)).isNull();
    }

    @Test(expected = ApiException.class)
    public void test_fillFromIncorrectJson() {
        JSONObject json = new JSONObject();
        json.put("data", getJsonResponse());
        new WorkspaceImpl(null).fromJson(json);
    }

    @Test
    public void test_fillFromJsonWithBadId() {
        JSONObject json = getJsonResponse();
        json.put(WorkspaceImplProcessor.ID.getFieldName(), "string_id");
        try {
            new WorkspaceImpl(null).fromJson(json);
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
        try {
            new WorkspaceImpl(null).fromJson(json);
        } catch (ApiException ex) {
            assertThat(ex.getErrorCode() == ApiException.INCORRECT_RESPONSE_FIELD_FORMAT);
            assertThat(ex.getMessage()).contains("'" + WorkspaceImplProcessor.ID.getFieldName() + "'");
            return;
        }
        fail("Exception expected!");
    }

    @Test
    public void test_fillFromJsonWithBadName() {
        JSONObject json = getJsonResponse();
        json.put(WorkspaceImplProcessor.NAME.getFieldName(), 123);
        try {
            new WorkspaceImpl(null).fromJson(json);
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
        try {
            new WorkspaceImpl(null).fromJson(json);
        } catch (ApiException ex) {
            assertThat(ex.getErrorCode() == ApiException.INCORRECT_RESPONSE_FIELD_FORMAT);
            assertThat(ex.getMessage()).contains("'" + WorkspaceImplProcessor.NAME.getFieldName() + "'");
            return;
        }
        fail("Exception expected!");
    }

    @Test
    public void test_fillWithCorrectJson() {
        JSONObject json = getJsonResponse();
        Workspace workspace = new WorkspaceImpl(null).fromJson(json);
        assertThat(workspace).isNotNull();
        assertThat(workspace.getId()).isEqualTo(36641231);
        assertThat(workspace.getName()).isEqualTo("workspace");
    }

    @Test
    public void test_fillFromJsonWithNullOrganisation() {
        JSONObject json = getJsonResponse();
        json.put(WorkspaceImplProcessor.ORGANISATION.getFieldName(), JSONObject.NULL);
        try {
            new WorkspaceImpl(null).fromJson(json);
        } catch (ApiException ex) {
            assertThat(ex.getErrorCode() == ApiException.INCORRECT_RESPONSE_FIELD_FORMAT);
            assertThat(ex.getMessage()).contains("'" + WorkspaceImplProcessor.ORGANISATION.getFieldName() + "'");
            return;
        }
        fail("Exception expected!");
    }

    @Test
    public void test_fillFromJsonWithBadOrganisation() {
        JSONObject json = getJsonResponse();
        json.put(WorkspaceImplProcessor.ORGANISATION.getFieldName(), "bla-bla");
        try {
            new WorkspaceImpl(null).fromJson(json);
        } catch (ApiException ex) {
            assertThat(ex.getErrorCode() == ApiException.INCORRECT_RESPONSE_FIELD_FORMAT);
            assertThat(ex.getMessage()).contains("'" + WorkspaceImplProcessor.ORGANISATION.getFieldName() + "'");
            return;
        }
        fail("Exception expected!");
    }
}
