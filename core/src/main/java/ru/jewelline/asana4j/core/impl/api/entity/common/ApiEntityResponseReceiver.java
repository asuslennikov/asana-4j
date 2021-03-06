package ru.jewelline.asana4j.core.impl.api.entity.common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.jewelline.asana4j.api.ApiException;
import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.clients.modifiers.Pagination;
import ru.jewelline.asana4j.api.entity.io.EntityDeserializer;
import ru.jewelline.asana4j.utils.JsonOutputStream;
import ru.jewelline.asana4j.utils.StringUtils;
import ru.jewelline.request.http.StreamBasedResponseReceiver;

public class ApiEntityResponseReceiver extends StreamBasedResponseReceiver {
    public static final String DATA_ROOT = "data";
    public static final String ERRORS_ROOT = "errors";
    public static final String NEXT_PAGE_ROOT = "next_page";

    public ApiEntityResponseReceiver() {
        super(new JsonOutputStream(), new JsonOutputStream());
    }

    @Override
    public JsonOutputStream getResponseStream() {
        return (JsonOutputStream) super.getResponseStream();
    }

    @Override
    public JsonOutputStream getErrorStream() {
        return (JsonOutputStream) super.getErrorStream();
    }


    public <T, R extends T> T asApiObject(EntityDeserializer<R> deserializer) {
        checkForErrors();
        JSONObject jsonObj = getResponseStream().asJson();
        if (jsonObj.has(DATA_ROOT)) {
            try {
                Object dataRoot = jsonObj.get(DATA_ROOT);
                if (dataRoot instanceof JSONObject) {
                    return deserializer.deserialize(dataRoot);
                } else if (dataRoot instanceof JSONArray) {
                    throw new ApiException(ApiException.INCORRECT_RESPONSE_FORMAT, "You are trying to process a " +
                            "collection of objects as a single entity");
                }
            } catch (JSONException e) {
                throw jsonParseException(e);
            }
        }
        throw unableToExtractException();
    }

    public <T, R extends T> PagedList<T> asApiCollection(EntityDeserializer<R> deserializer) {
        checkForErrors();
        JSONObject jsonObj = getResponseStream().asJson();
        if (jsonObj.has(DATA_ROOT)) {
            try {
                Object dataRoot = jsonObj.get(DATA_ROOT);
                if (dataRoot instanceof JSONObject) {
                    throw new ApiException(ApiException.INCORRECT_RESPONSE_FORMAT, "You are trying to process a " +
                            "single entity as a collection");
                } else if (dataRoot instanceof JSONArray) {
                    Pagination pagination = getPagination(jsonObj);
                    JSONArray objects = (JSONArray) dataRoot;
                    PagedList<T> apiCollection = new PagedList<>(pagination);
                    for (int i = 0; i < objects.length(); i++) {
                        apiCollection.add(deserializer.deserialize(objects.getJSONObject(i)));
                    }
                    return apiCollection;
                }
            } catch (JSONException e) {
                throw jsonParseException(e);
            }
        }
        throw unableToExtractException();
    }

    private void checkForErrors() {
        if (getResponseCode() >= 400 && getResponseCode() < 600) {
            JSONObject jsonObj = getErrorStream().asJson();
            if (jsonObj.has(ERRORS_ROOT)) {
                JSONArray errors = jsonObj.getJSONArray(ERRORS_ROOT);
                if (errors.length() > 0) {
                    String message = errors.getJSONObject(0).optString("message");
                    if (getResponseCode() == 500) {
                        message = "Server error: " + message + ", phrase: " + errors.getJSONObject(0).optString("phrase");
                    }
                    throw new ApiException(getResponseCode(), message);
                }
            }

        }
    }

    private Pagination getPagination(JSONObject response) throws JSONException {
        if (response.has(NEXT_PAGE_ROOT)) {
            Object nextPage = response.get(NEXT_PAGE_ROOT);
            if (nextPage instanceof JSONObject) {
                JSONObject nextPageObj = (JSONObject) nextPage;
                int limit = Pagination.DEFAULT_LIMIT;
                String offsetToken = null;
                if (nextPageObj.has("path")) {
                    try {
                        limit = Integer.parseInt(StringUtils.getSubstring(nextPageObj.getString("path"), "limit=", "&"));
                    } catch (NumberFormatException ex) {
                        limit = Pagination.DEFAULT_LIMIT;
                    }
                }
                if (nextPageObj.has("offset")) {
                    offsetToken = nextPageObj.getString("offset");
                }
                return new Pagination(limit, offsetToken);
            }
        }
        return null;
    }

    private ApiException jsonParseException(JSONException e) {
        return new ApiException(ApiException.INCORRECT_RESPONSE_FORMAT, "Unable to parse response from server: " +
                e.getLocalizedMessage());
    }

    private ApiException unableToExtractException() {
        return new ApiException(ApiException.INCORRECT_RESPONSE_FORMAT, "Unable to extract entity from response, maybe the root object '" + DATA_ROOT +
                "' is missed, response: " + getResponseStream().asString());
    }
}
