package ru.jewelline.asana4j.core.impl.api.entity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.jewelline.asana4j.api.ApiException;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.api.entity.io.EntityDeserializer;
import ru.jewelline.asana4j.api.entity.io.JsonEntity;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityContext;
import ru.jewelline.asana4j.http.HttpMethod;
import ru.jewelline.asana4j.http.HttpRequest;
import ru.jewelline.asana4j.http.HttpRequestBuilder;
import ru.jewelline.asana4j.http.HttpResponse;

import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserImplTest {

    @Mock
    private ApiEntityContext context;

    private static String getPhotoUrl(User.PhotoSize size) {
        return "http://example.com/" + size.toString() + ".png";
    }

    public static JSONObject getJsonResponse() {
        JSONObject json = new JSONObject();
        json.put(UserImplProcessor.ID.getFieldName(), 354185431);
        json.put(UserImplProcessor.NAME.getFieldName(), "User for test");
        json.put(UserImplProcessor.EMAIL.getFieldName(), "test@example.com");
        JSONObject photoMap = new JSONObject();
        for (User.PhotoSize size : User.PhotoSize.values()) {
            photoMap.put(size.toString(), getPhotoUrl(size));
        }
        json.put(UserImplProcessor.PHOTO.getFieldName(), photoMap);
        json.put(UserImplProcessor.WORKSPACES.getFieldName(), Collections.emptyList());
        return json;
    }

    @Before
    public void before() {
        EntityDeserializer<UserImpl> userDeserializer = mock(EntityDeserializer.class);
        when(userDeserializer.deserialize(Matchers.any(JSONObject.class))).thenReturn(new UserImpl(this.context));
        when(context.getDeserializer(UserImpl.class)).thenReturn(userDeserializer);

        WorkspaceImpl workspace = mock(WorkspaceImpl.class);
        EntityDeserializer<WorkspaceImpl> workspaceDeserializer = mock(EntityDeserializer.class);
        when(workspaceDeserializer.deserialize(Matchers.any(JSONObject.class))).thenReturn(workspace);
        when(context.getDeserializer(WorkspaceImpl.class)).thenReturn(workspaceDeserializer);
    }

    private UserImpl testInstance() {
        return new UserImpl(this.context);
    }

    @Test
    public void test_implementsJsonEntity() {
        assertThat(testInstance()).isInstanceOf(JsonEntity.class);
    }

    @Test
    public void test_tryFillFromNull() {
        UserImpl user = testInstance();
        assertThat(user.fromJson(null)).isNull();
    }

    @Test(expected = ApiException.class)
    public void test_fillFromIncorrectJson() {
        JSONObject json = new JSONObject();
        json.put("data", getJsonResponse());
        testInstance().fromJson(json);
    }

    @Test
    public void test_fillFromJsonWithNullId() {
        JSONObject json = getJsonResponse();
        json.put(UserImplProcessor.ID.getFieldName(), JSONObject.NULL);
        UserImpl user = testInstance().fromJson(json);
        assertThat(user.getId()).isEqualTo(0);
    }

    @Test
    public void test_fillFromJsonWithBadId() {
        JSONObject json = getJsonResponse();
        json.put(UserImplProcessor.ID.getFieldName(), "string_id");
        try {
            testInstance().fromJson(json);
        } catch (ApiException ex) {
            assertThat(ex.getErrorCode() == ApiException.INCORRECT_RESPONSE_FIELD_FORMAT);
            assertThat(ex.getMessage()).contains("'" + UserImplProcessor.ID.getFieldName() + "'");
            return;
        }
        fail("Exception expected!");
    }

    @Test
    public void test_fillFromJsonWithNullName() {
        JSONObject json = getJsonResponse();
        json.put(UserImplProcessor.NAME.getFieldName(), JSONObject.NULL);
        UserImpl user = testInstance().fromJson(json);
        assertThat(user.getName()).isNull();
    }

    @Test
    public void test_fillFromJsonWithBadName() {
        JSONObject json = getJsonResponse();
        json.put(UserImplProcessor.NAME.getFieldName(), 123);
        try {
            testInstance().fromJson(json);
        } catch (ApiException ex) {
            assertThat(ex.getErrorCode() == ApiException.INCORRECT_RESPONSE_FIELD_FORMAT);
            assertThat(ex.getMessage()).contains("'" + UserImplProcessor.NAME.getFieldName() + "'");
            return;
        }
        fail("Exception expected!");
    }

    @Test
    public void test_fillFromJsonWithNullMail() {
        JSONObject json = getJsonResponse();
        json.put(UserImplProcessor.EMAIL.getFieldName(), JSONObject.NULL);
        UserImpl user = testInstance().fromJson(json);
        assertThat(user.getEmail()).isNull();
    }

    @Test
    public void test_fillFromJsonWithBadMail() {
        JSONObject json = getJsonResponse();
        json.put(UserImplProcessor.EMAIL.getFieldName(), 123);
        try {
            testInstance().fromJson(json);
        } catch (ApiException ex) {
            assertThat(ex.getErrorCode() == ApiException.INCORRECT_RESPONSE_FIELD_FORMAT);
            assertThat(ex.getMessage()).contains("'" + UserImplProcessor.EMAIL.getFieldName() + "'");
            return;
        }
        fail("Exception expected!");
    }

    @Test
    public void test_fillFromJsonWithNullPhoto() {
        JSONObject json = getJsonResponse();
        json.put(UserImplProcessor.PHOTO.getFieldName(), JSONObject.NULL);

        User user = testInstance().fromJson(json);
        assertThat(user.getPhotoUrl()).isNull();
    }

    @Test
    public void test_fillFromJsonWithBadPhoto() {
        JSONObject json = getJsonResponse();
        json.put(UserImplProcessor.PHOTO.getFieldName(), 123);
        try {
            testInstance().fromJson(json);
        } catch (ApiException ex) {
            assertThat(ex.getErrorCode() == ApiException.INCORRECT_RESPONSE_FIELD_FORMAT);
            assertThat(ex.getMessage()).contains("'" + UserImplProcessor.PHOTO.getFieldName() + "'");
            return;
        }
        fail("Exception expected!");
    }

    @Test
    public void test_fillFromJsonWithBadWorkspaces() {
        JSONObject json = getJsonResponse();
        json.put(UserImplProcessor.WORKSPACES.getFieldName(), new HashMap<String, Object>());
        testInstance().fromJson(json);
        // assert no exception here
    }

    @Test
    public void test_fillFromJsonWithNullWorkspaces() {
        JSONObject json = getJsonResponse();
        json.put(UserImplProcessor.WORKSPACES.getFieldName(), JSONObject.NULL);

        User user = testInstance().fromJson(json);
        assertThat(user.getWorkspaces()).isNull();
    }

    @Test
    public void test_fillWithCorrectJson() {
        JSONObject json = getJsonResponse();
        User user = testInstance().fromJson(json);
        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(354185431L);
        assertThat(user.getName()).isEqualTo("User for test");
        assertThat(user.getEmail()).isEqualTo("test@example.com");
        assertThat(user.getPhotoUrl()).containsKeys(User.PhotoSize.values());
        assertThat(user.getWorkspaces()).isNullOrEmpty();
    }

    @Test
    public void test_fillWithWorkspaces() {
        JSONObject json = getJsonResponse();
        Map<Object, Object> workspace1 = new HashMap<>();
        workspace1.put(WorkspaceImplProcessor.ID.getFieldName(), 4654361);
        Map<Object, Object> workspace2 = new HashMap<>();
        workspace2.put(WorkspaceImplProcessor.ID.getFieldName(), 7694335);
        json.put(UserImplProcessor.WORKSPACES.getFieldName(), new JSONArray(new Object[]{workspace1, workspace2}));
        User user = testInstance().fromJson(json);
        assertThat(user).isNotNull();
        assertThat(user.getWorkspaces()).hasSize(2);
    }

    @Test
    public void test_downloadPhotoWhileMapIsNull() {
        JSONObject json = getJsonResponse();
        json.put(UserImplProcessor.PHOTO.getFieldName(), (Map) null);
        User user = testInstance().fromJson(json);
        assertThat(user.downloadPhoto(User.PhotoSize.SIZE_21, mock(OutputStream.class))).isFalse();
    }

    @Test
    public void test_downloadPhotoWhileMapDoesNotHaveThisSize() {
        JSONObject json = getJsonResponse();
        json.getJSONObject(UserImplProcessor.PHOTO.getFieldName()).put(User.PhotoSize.SIZE_21.toString(), JSONObject.NULL);
        User user = testInstance().fromJson(json);
        assertThat(user.downloadPhoto(User.PhotoSize.SIZE_21, mock(OutputStream.class))).isFalse();
    }

    @Test
    public void test_downloadPhotoOkServerResponse() {
        HttpResponse httpResponse = mock(HttpResponse.class);
        when(httpResponse.code()).thenReturn(200);
        HttpRequest httpRequest = mock(HttpRequest.class);
        when(httpRequest.sendAndReadResponse(Matchers.any(OutputStream.class))).thenReturn(httpResponse);
        HttpRequestBuilder httpBuilder = mock(HttpRequestBuilder.class);
        when(httpBuilder.path(Matchers.anyString())).thenReturn(httpBuilder);
        when(httpBuilder.buildAs(HttpMethod.GET)).thenReturn(httpRequest);
        when(this.context.httpRequest()).thenReturn(httpBuilder);
        JSONObject json = getJsonResponse();
        User user = testInstance().fromJson(json);
        assertThat(user.downloadPhoto(User.PhotoSize.SIZE_21, mock(OutputStream.class))).isTrue();
        verify(httpBuilder).path(getPhotoUrl(User.PhotoSize.SIZE_21));
    }

    @Test
    public void test_downloadPhotoBadServerResponse() {
        HttpResponse httpResponse = mock(HttpResponse.class);
        when(httpResponse.code()).thenReturn(404);
        HttpRequest httpRequest = mock(HttpRequest.class);
        when(httpRequest.sendAndReadResponse(Matchers.any(OutputStream.class))).thenReturn(httpResponse);
        HttpRequestBuilder httpBuilder = mock(HttpRequestBuilder.class);
        when(httpBuilder.path(Matchers.anyString())).thenReturn(httpBuilder);
        when(httpBuilder.buildAs(HttpMethod.GET)).thenReturn(httpRequest);
        when(this.context.httpRequest()).thenReturn(httpBuilder);
        JSONObject json = getJsonResponse();
        User user = testInstance().fromJson(json);
        assertThat(user.downloadPhoto(User.PhotoSize.SIZE_21, mock(OutputStream.class))).isFalse();
        verify(httpBuilder).path(getPhotoUrl(User.PhotoSize.SIZE_21));
    }
}
