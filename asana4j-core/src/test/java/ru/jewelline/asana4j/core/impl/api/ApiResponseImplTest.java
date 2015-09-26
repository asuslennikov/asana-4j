package ru.jewelline.asana4j.core.impl.api;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import ru.jewelline.asana4j.api.ApiException;
import ru.jewelline.asana4j.core.impl.api.entity.ApiEntity;
import ru.jewelline.asana4j.http.HttpResponse;
import ru.jewelline.asana4j.http.NetworkException;

import java.net.HttpURLConnection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@Ignore
public class ApiResponseImplTest {
/*
    private static abstract class TestApiEntity implements ApiEntity<Object>{
        // Stub class for generics
    }

    private static class TestApiResponseImpl extends ApiResponseImpl<Object, TestApiEntity> {
        public TestApiResponseImpl(HttpResponse httpResponse, ApiEntityInstanceProvider<TestApiEntity> instanceProvider) {
            super(httpResponse, instanceProvider);
        }
    }

    private static ApiEntityInstanceProvider<TestApiEntity> ERROR_PROVIDER = new ApiEntityInstanceProvider<TestApiEntity>(){
        @Override
        public TestApiEntity newInstance() {
            throw new IllegalStateException("This method shouldn't be called!");
        }
    };

    @Test
    public void test_statusPropagation(){
        HttpResponse response = mock(HttpResponse.class);
        int httpStatusCode = HttpURLConnection.HTTP_FORBIDDEN;
        when(response.code()).thenReturn(httpStatusCode);

        TestApiResponseImpl apiResponse = new TestApiResponseImpl(response, ERROR_PROVIDER);
        assertThat(apiResponse.code()).isEqualTo(httpStatusCode);
    }

    @Test
    public void test_asStatusPropagation(){
        HttpResponse response = mock(HttpResponse.class);
        String stringResponse = "Response as string";
        when(response.asString()).thenReturn(stringResponse);

        TestApiResponseImpl apiResponse = new TestApiResponseImpl(response, ERROR_PROVIDER);
        assertThat(apiResponse.asString()).isEqualTo(stringResponse);
    }

    @Test
    public void test_asByteArrayPropagation(){
        HttpResponse response = mock(HttpResponse.class);
        byte[] responseAsBytes = "Response as byte array".getBytes();
        when(response.asByteArray()).thenReturn(responseAsBytes);

        TestApiResponseImpl apiResponse = new TestApiResponseImpl(response, ERROR_PROVIDER);
        assertThat(apiResponse.asByteArray()).isEqualTo(responseAsBytes);
    }

    @Test
    public void test_asJsonObjectPropagation(){
        HttpResponse response = mock(HttpResponse.class);
        JSONObject json = new JSONObject();
        json.put("field", "value");
        when(response.asJsonObject()).thenReturn(json);

        TestApiResponseImpl apiResponse = new TestApiResponseImpl(response, ERROR_PROVIDER);
        JSONObject jsonObject = apiResponse.asJsonObject();
        assertThat(jsonObject.has("field")).isTrue();
        assertThat(jsonObject.getString("field")).isEqualTo("value");
    }

    @Test
    public void test_asJsonObjectWithInnerJsonError(){
        HttpResponse response = mock(HttpResponse.class);
        doThrow(new NetworkException(NetworkException.UNREDABLE_RESPONSE)).when(response).asJsonObject();

        TestApiResponseImpl apiResponse = new TestApiResponseImpl(response, ERROR_PROVIDER);
        try {
            apiResponse.asApiObject();
            fail("ApiException should be raised");
        } catch (ApiException ex){
            assertThat(ex.getErrorCode()).isEqualTo(ApiException.INCORRECT_RESPONSE_FORMAT);
            assertThat(ex.getLocalizedMessage()).startsWith("Unable to extract entity from response");
        }
    }

    @Test
    public void test_asApiObjectSuccess(){
        HttpResponse response = mock(HttpResponse.class);
        // {"data" : {"field" : "value"}}
        JSONObject json = new JSONObject("{\"data\" : {\"field\" : \"value\"}}");
        when(response.asJsonObject()).thenReturn(json);

        TestApiResponseImpl apiResponse = new TestApiResponseImpl(response, new ApiEntityInstanceProvider<TestApiEntity>() {
            @Override
            public TestApiEntity newInstance() {
                return new TestApiEntity() {
                    @Override
                    public Object fromJson(JSONObject object) {
                        assertThat(object.has("field")).isTrue();
                        assertThat(object.get("field")).isEqualTo("value");
                        return new Object();
                    }
                };
            }
        });
        apiResponse.asApiObject();
    }

    @Test
    public void test_asApiObjectOnApiCollection(){
        HttpResponse response = mock(HttpResponse.class);
        // {"data" : [{"field" : "value"}]}
        JSONObject json = new JSONObject("{\"data\" : [{\"field\" : \"value\"}]}");
        when(response.asJsonObject()).thenReturn(json);

        TestApiResponseImpl apiResponse = new TestApiResponseImpl(response, ERROR_PROVIDER);
        try {
            apiResponse.asApiObject();
            fail("ApiException should be raised");
        } catch (ApiException ex){
            assertThat(ex.getErrorCode()).isEqualTo(ApiException.INCORRECT_RESPONSE_FORMAT);
            assertThat(ex.getLocalizedMessage()).startsWith("You are trying to process a collection of objects as a single entity");
        }
    }

    @Test
    public void test_asApiObjectOnBadInnerJson(){
        HttpResponse response = mock(HttpResponse.class);
        // {"data" : {"field" : "value"}}
        JSONObject json = new JSONObject("{\"data\" : {\"field\" : \"value\"}}");
        when(response.asJsonObject()).thenReturn(json);

        TestApiResponseImpl apiResponse = new TestApiResponseImpl(response, new ApiEntityInstanceProvider<TestApiEntity>() {
            @Override
            public TestApiEntity newInstance() {
                return new TestApiEntity() {
                    @Override
                    public Object fromJson(JSONObject object) {
                        throw new JSONException("test");
                    }
                };
            }
        });
        try {
            apiResponse.asApiObject();
            fail("ApiException should be raised");
        } catch (ApiException ex){
            assertThat(ex.getErrorCode()).isEqualTo(ApiException.INCORRECT_RESPONSE_FORMAT);
            assertThat(ex.getLocalizedMessage()).startsWith("Unable to parse response from server:");
        }
    }

    @Test
    public void test_asApiObjectOnJsonWithoutDataRoot(){
        HttpResponse response = mock(HttpResponse.class);
        // {"field" : "value"}
        JSONObject json = new JSONObject("{\"field\" : \"value\"}");
        when(response.asJsonObject()).thenReturn(json);

        TestApiResponseImpl apiResponse = new TestApiResponseImpl(response, ERROR_PROVIDER);
        try {
            apiResponse.asApiObject();
            fail("ApiException should be raised");
        } catch (ApiException ex){
            assertThat(ex.getErrorCode()).isEqualTo(ApiException.INCORRECT_RESPONSE_FORMAT);
            assertThat(ex.getLocalizedMessage()).startsWith("Unable to extract entity from response");
        }
    }

    @Test
    public void test_asApiCollectionSuccess(){
        HttpResponse response = mock(HttpResponse.class);
        // {"data" : [{"field" : "value"}, {"field" : "value"}]}
        JSONObject json = new JSONObject("{\"data\" : [{\"field\" : \"value\"}, {\"field\" : \"value\"}]}");
        when(response.asJsonObject()).thenReturn(json);

        TestApiResponseImpl apiResponse = new TestApiResponseImpl(response, new ApiEntityInstanceProvider<TestApiEntity>() {
            @Override
            public TestApiEntity newInstance() {
                return new TestApiEntity() {
                    @Override
                    public Object fromJson(JSONObject object) {
                        assertThat(object.has("field")).isTrue();
                        assertThat(object.get("field")).isEqualTo("value");
                        return new Object();
                    }
                };
            }
        });
        List<Object> objects = apiResponse.asApiCollection();
        assertThat(objects.size()).isEqualTo(2);
    }

    @Test
    public void test_asApiCollectionOnJsonWithoutDataRoot(){
        HttpResponse response = mock(HttpResponse.class);
        // {"field" : "value"}
        JSONObject json = new JSONObject("{\"field\" : \"value\"}");
        when(response.asJsonObject()).thenReturn(json);

        TestApiResponseImpl apiResponse = new TestApiResponseImpl(response, ERROR_PROVIDER);
        try {
            apiResponse.asApiCollection();
            fail("ApiException should be raised");
        } catch (ApiException ex){
            assertThat(ex.getErrorCode()).isEqualTo(ApiException.INCORRECT_RESPONSE_FORMAT);
            assertThat(ex.getLocalizedMessage()).startsWith("Unable to extract entity from response");
        }
    }

    @Test
    public void test_asApiCollectionOnSingleEntity(){
        HttpResponse response = mock(HttpResponse.class);
        // {"data" : {"field" : "value"}}
        JSONObject json = new JSONObject("{\"data\" : {\"field\" : \"value\"}}");
        when(response.asJsonObject()).thenReturn(json);

        TestApiResponseImpl apiResponse = new TestApiResponseImpl(response, ERROR_PROVIDER);
        try {
            apiResponse.asApiCollection();
            fail("ApiException should be raised");
        } catch (ApiException ex){
            assertThat(ex.getErrorCode()).isEqualTo(ApiException.INCORRECT_RESPONSE_FORMAT);
            assertThat(ex.getLocalizedMessage()).startsWith("You are trying to process a single entity as a collection");
        }
    }


    @Test
    public void test_asApiCollectionOnBadInnerJson(){
        HttpResponse response = mock(HttpResponse.class);
        // {"data" : [{"field" : "value"}]}
        JSONObject json = new JSONObject("{\"data\" : [{\"field\" : \"value\"}]}");
        when(response.asJsonObject()).thenReturn(json);

        TestApiResponseImpl apiResponse = new TestApiResponseImpl(response, new ApiEntityInstanceProvider<TestApiEntity>() {
            @Override
            public TestApiEntity newInstance() {
                return new TestApiEntity() {
                    @Override
                    public Object fromJson(JSONObject object) {
                        throw new JSONException("test");
                    }
                };
            }
        });
        try {
            apiResponse.asApiCollection();
            fail("ApiException should be raised");
        } catch (ApiException ex){
            assertThat(ex.getErrorCode()).isEqualTo(ApiException.INCORRECT_RESPONSE_FORMAT);
            assertThat(ex.getLocalizedMessage()).startsWith("Unable to parse response from server");
        }
    }
    */
}
