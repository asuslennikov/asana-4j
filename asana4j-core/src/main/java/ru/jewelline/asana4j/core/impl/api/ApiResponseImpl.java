package ru.jewelline.asana4j.core.impl.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.asana4j.api.ApiException;
import ru.jewelline.asana4j.api.ApiResponse;
import ru.jewelline.asana4j.core.impl.api.entity.ApiEntity;
import ru.jewelline.asana4j.http.HttpResponse;
import ru.jewelline.asana4j.http.NetworkException;

import java.util.ArrayList;
import java.util.List;

public class ApiResponseImpl<AT, T extends ApiEntity<AT>> implements ApiResponse<AT> {

    public static final String DATA_ROOT = "data";
    private final HttpResponse httpResponse;
    private final ApiEntityInstanceProvider<T> instanceProvider;

    public ApiResponseImpl(HttpResponse httpResponse, ApiEntityInstanceProvider<T> instanceProvider) {
        this.httpResponse = httpResponse;
        this.instanceProvider = instanceProvider;
    }

    @Override
    public AT asApiObject() {
        JSONObject jsonObj = asJsonObject();
        if (jsonObj.has(DATA_ROOT)){
            try {
                Object dataRoot = jsonObj.get(DATA_ROOT);
                if (dataRoot instanceof JSONObject){
                    return this.instanceProvider.newInstance().fromJson((JSONObject) dataRoot);
                } else if (dataRoot instanceof JSONArray){
                    throw new ApiException(ApiException.INCORRECT_RESPONSE_FORMAT, "You are trying to process a " +
                            "collection of objects as a single entity");
                }
            } catch (JSONException e) {
                throw jsonParseException(e);
            }
        }
        throw unableToExtractException();
    }

    @Override
    public List<AT> asApiCollection() {
        JSONObject jsonObj = asJsonObject();
        if (jsonObj.has(DATA_ROOT)){
            try {
                Object dataRoot = jsonObj.get(DATA_ROOT);
                if (dataRoot instanceof JSONArray){
                    JSONArray objects = (JSONArray) dataRoot;
                    List<AT> apiCollection = new ArrayList<>(objects.length());
                    for (int i = 0; i < objects.length(); i++) {
                        apiCollection.add(this.instanceProvider.newInstance().fromJson(objects.getJSONObject(i)));
                    }
                    return apiCollection;
                } else if (dataRoot instanceof JSONObject){
                    throw new ApiException(ApiException.INCORRECT_RESPONSE_FORMAT, "You are trying to process a " +
                            "single entity as a collection");
                }
            } catch (JSONException e) {
                throw jsonParseException(e);
            }
        }
        throw unableToExtractException();
    }

    private ApiException jsonParseException(JSONException e) {
        return new ApiException(ApiException.INCORRECT_RESPONSE_FORMAT, "Unable to parse response from server: " +
                e.getLocalizedMessage());
    }

    private ApiException unableToExtractException() {
        return new ApiException(ApiException.INCORRECT_RESPONSE_FORMAT, "Unable to extract entity from response, maybe the root object '" + DATA_ROOT +
                "' is missed, response: " + asString());
    }


    @Override
    public int status() {
        return this.httpResponse.status();
    }

    @Override
    public String asString() {
        return this.httpResponse.asString();
    }

    @Override
    public byte[] asByteArray() {
        return this.httpResponse.asByteArray();
    }

    @Override
    public JSONObject asJsonObject() {
        try {
            return this.httpResponse.asJsonObject();
        } catch (NetworkException ex){
            throw unableToExtractException();
        }
    }
}
