package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import ru.jewelline.asana4j.api.ApiException;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.api.entity.io.JsonEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

@RunWith(MockitoJUnitRunner.class)
public class UserImplTest {

    private static JSONObject getJsonResponse() {
        JSONObject json = new JSONObject();
        json.put(UserImplProcessor.ID.getFieldName(), 354185431);
        json.put(UserImplProcessor.NAME.getFieldName(), "User for test");
        json.put(UserImplProcessor.EMAIL.getFieldName(), "test@example.com");
        json.put(UserImplProcessor.PHOTO.getFieldName(), "http://example.com");
        json.put(UserImplProcessor.WORKSPACES.getFieldName(), Collections.emptyList());
        return json;
    }

    private UserImpl testInstance(){
        return new UserImpl(null);
    }

    @Test
    public void test_implementsApiEntity(){
        assertThat(testInstance()).isInstanceOf(JsonEntity.class);
    }

    @Test
    public void test_tryFillFromNull(){
        UserImpl user = testInstance();
        assertThat(user.fromJson(null)).isNull();
    }

    @Test(expected = ApiException.class)
    public void test_fillFromIncorrectJson(){
        JSONObject json = new JSONObject();
        json.put("data", getJsonResponse());
        testInstance().fromJson(json);
    }

    @Test
    public void test_fillFromJsonWithNullId(){
        JSONObject json = getJsonResponse();
        json.put(UserImplProcessor.ID.getFieldName(), JSONObject.NULL);
        try {
            testInstance().fromJson(json);
        } catch (ApiException ex){
            assertThat(ex.getErrorCode() == ApiException.INCORRECT_RESPONSE_FORMAT);
            assertThat(ex.getMessage()).contains("'" + UserImplProcessor.ID.getFieldName() + "'");
            return;
        }
        fail("Exception expected!");
    }

    @Test
    public void test_fillFromJsonWithBadId(){
        JSONObject json = getJsonResponse();
        json.put(UserImplProcessor.ID.getFieldName(), "string_id");
        try {
            testInstance().fromJson(json);
        } catch (ApiException ex){
            assertThat(ex.getErrorCode() == ApiException.INCORRECT_RESPONSE_FIELD_FORMAT);
            assertThat(ex.getMessage()).contains("'" + UserImplProcessor.ID.getFieldName() + "'");
            return;
        }
        fail("Exception expected!");
    }

    @Test
    public void test_fillFromJsonWithNullName(){
        JSONObject json = getJsonResponse();
        json.put(UserImplProcessor.NAME.getFieldName(), JSONObject.NULL);
        try {
            testInstance().fromJson(json);
        } catch (ApiException ex){
            assertThat(ex.getErrorCode() == ApiException.INCORRECT_RESPONSE_FORMAT);
            assertThat(ex.getMessage()).contains("'" + UserImplProcessor.NAME.getFieldName() + "'");
            return;
        }
        fail("Exception expected!");
    }

    @Test
    public void test_fillFromJsonWithBadName(){
        JSONObject json = getJsonResponse();
        json.put(UserImplProcessor.NAME.getFieldName(), 123);
        try {
            testInstance().fromJson(json);
        } catch (ApiException ex){
            assertThat(ex.getErrorCode() == ApiException.INCORRECT_RESPONSE_FIELD_FORMAT);
            assertThat(ex.getMessage()).contains("'" + UserImplProcessor.NAME.getFieldName() + "'");
            return;
        }
        fail("Exception expected!");
    }

    @Test
    public void test_fillFromJsonWithNullMail(){
        JSONObject json = getJsonResponse();
        json.put(UserImplProcessor.EMAIL.getFieldName(), JSONObject.NULL);
        try {
            testInstance().fromJson(json);
        } catch (ApiException ex){
            assertThat(ex.getErrorCode() == ApiException.INCORRECT_RESPONSE_FORMAT);
            assertThat(ex.getMessage()).contains("'" + UserImplProcessor.EMAIL.getFieldName() + "'");
            return;
        }
        fail("Exception expected!");
    }

    @Test
    public void test_fillFromJsonWithBadMail(){
        JSONObject json = getJsonResponse();
        json.put(UserImplProcessor.EMAIL.getFieldName(), 123);
        try {
            testInstance().fromJson(json);
        } catch (ApiException ex){
            assertThat(ex.getErrorCode() == ApiException.INCORRECT_RESPONSE_FIELD_FORMAT);
            assertThat(ex.getMessage()).contains("'" + UserImplProcessor.EMAIL.getFieldName() + "'");
            return;
        }
        fail("Exception expected!");
    }

    @Test
    public void test_fillFromJsonWithNullPhoto(){
        JSONObject json = getJsonResponse();
        json.put(UserImplProcessor.PHOTO.getFieldName(),JSONObject.NULL);

        User user = testInstance().fromJson(json);
        assertThat(user.getPhotoUrl()).isNull();
    }

    @Test
    public void test_fillFromJsonWithBadPhoto(){
        JSONObject json = getJsonResponse();
        json.put(UserImplProcessor.PHOTO.getFieldName(), 123);
        try {
            testInstance().fromJson(json);
        } catch (ApiException ex){
            assertThat(ex.getErrorCode() == ApiException.INCORRECT_RESPONSE_FIELD_FORMAT);
            assertThat(ex.getMessage()).contains("'" + UserImplProcessor.PHOTO.getFieldName() + "'");
            return;
        }
        fail("Exception expected!");
    }

    @Test
    public void test_fillFromJsonWithBadWorkspaces(){
        JSONObject json = getJsonResponse();
        json.put(UserImplProcessor.WORKSPACES.getFieldName(), new HashMap<String, Object>());
        testInstance().fromJson(json);
        // assert no exception here
    }

    @Test
    public void test_fillFromJsonWithNullWorkspaces(){
        JSONObject json = getJsonResponse();
        json.put(UserImplProcessor.WORKSPACES.getFieldName(),JSONObject.NULL);

        User user = testInstance().fromJson(json);
        assertThat(user.getWorkspaces()).isNull();
    }

    @Test
    public void test_fillWithCorrectJson(){
        JSONObject json = getJsonResponse();
        User user = testInstance().fromJson(json);
        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(354185431L);
        assertThat(user.getName()).isEqualTo("User for test");
        assertThat(user.getEmail()).isEqualTo("test@example.com");
        assertThat(user.getPhotoUrl()).isEqualTo("http://example.com");
        assertThat(user.getWorkspaces()).isNullOrEmpty();
    }

    public void test_fillWithWorkspaces(){
        JSONObject json = getJsonResponse();
        Map<Object, Object> workspace1 = new HashMap<>();
        workspace1.put(WorkspaceImplProcessor.ID.getFieldName(), 4654361);
        workspace1.put(WorkspaceImplProcessor.NAME.getFieldName(), "workspace1");
        Map<Object, Object> workspace2 = new HashMap<>();
        workspace2.put(WorkspaceImplProcessor.ID.getFieldName(), 7694335);
        workspace2.put(WorkspaceImplProcessor.NAME.getFieldName(), "workspace2");
        json.put(UserImplProcessor.WORKSPACES.getFieldName(), Arrays.asList(workspace1, workspace2));
        User user = testInstance().fromJson(json);
        assertThat(user).isNotNull();
        assertThat(user.getWorkspaces()).hasSize(2);
        assertThat(user.getWorkspaces().get(0).getName()).contains("workspace");
    }
}
