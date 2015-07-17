package ru.jewelline.asana4j.core.impl.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.asana4j.api.ApiResponse;
import ru.jewelline.asana4j.core.impl.api.entity.ApiEntity;
import ru.jewelline.asana4j.http.HttpResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ApiResponseImpl<AT, T extends ApiEntity<AT>> implements ApiResponse<AT> {

    private final HttpResponse httpResponse;
    private final ApiEntityInstanceProvider<T> instanceProvider;

    public ApiResponseImpl(HttpResponse httpResponse, ApiEntityInstanceProvider<T> instanceProvider) {
        this.httpResponse = httpResponse;
        this.instanceProvider = instanceProvider;
    }

    @Override
    public AT asApiObject() {
        return this.instanceProvider.newInstance().fromJson(asJsonObject());
    }

    @Override
    public List<AT> asApiCollection() {
        JSONObject jsonObj = asJsonObject();
        if (jsonObj.has("data")){
            try {
                Object dataRoot = jsonObj.get("data");
                if (dataRoot instanceof JSONArray){
                    JSONArray objects = (JSONArray) dataRoot;
                    List<AT> apiCollection = new ArrayList<>(objects.length());
                    for (int i = 0; i < objects.length(); i++) {
                        apiCollection.add(this.instanceProvider.newInstance().fromJson(objects.getJSONObject(i)));
                    }
                    return apiCollection;
                }
            } catch (JSONException e) {
                //TODO notify somehow
            }
        }
        return Collections.singletonList(asApiObject());
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
        return this.httpResponse.asJsonObject();
    }
}
