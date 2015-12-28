package ru.jewelline.asana4j.api.clients.modifiers;

import org.json.JSONObject;
import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.api.entity.io.JsonEntity;
import ru.jewelline.asana4j.api.entity.io.SerializableEntity;
import ru.jewelline.asana4j.http.HttpMethod;

public class Fields implements RequestModifier {
    private static final String OPTION_FIELDS = "fields";
    private static final String GET_API_OPTION_PREFIX = "opt_";
    private final String[] fields;

    public Fields(String... fields) {
        this.fields = fields;
    }

    @Override
    public int priority() {
        return RequestModifier.PRIORITY_API_OPTIONS;
    }

    @Override
    public void modify(ApiRequestBuilder requestBuilder, HttpMethod httpMethod, ModifiersChain modifiersChain) {
        if (this.fields != null && this.fields.length > 0) {
            if (HttpMethod.GET == httpMethod) {
                doModifyForGet(requestBuilder);
            } else if (HttpMethod.DELETE != httpMethod){
                doModifyForOthers(requestBuilder);
            }
        }
        modifiersChain.next(requestBuilder, httpMethod);
    }

    private void doModifyForGet(ApiRequestBuilder requestBuilder) {
        StringBuilder sb = new StringBuilder();
        for (String field : this.fields) {
            sb.append(field).append(',');
        }
        sb.setLength(sb.length() - 1); // remove the last comma
        requestBuilder.setQueryParameter(GET_API_OPTION_PREFIX + OPTION_FIELDS, sb.toString());
    }

    private void doModifyForOthers(ApiRequestBuilder requestBuilder) {
        SerializableEntity entity = requestBuilder.getEntity();
        if (entity != null && entity instanceof JsonEntity){
            // modify entity: add options parameter
            JSONObject json = ((JsonEntity) entity).asJson();
            if (json != null) {

            }
        }
    }
}
