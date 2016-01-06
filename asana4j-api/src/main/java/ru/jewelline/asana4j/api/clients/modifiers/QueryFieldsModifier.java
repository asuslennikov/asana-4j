package ru.jewelline.asana4j.api.clients.modifiers;

import org.json.JSONObject;
import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.http.HttpMethod;

import java.util.Arrays;

public class QueryFieldsModifier extends ApiOptionModifier {
    private static final String OPTION_FIELDS = "fields";
    private final String[] fields;

    public QueryFieldsModifier(String... fields) {
        this.fields = fields;
    }

    @Override
    public void modify(ApiRequestBuilder requestBuilder, HttpMethod httpMethod, ModifiersChain modifiersChain) {
        if (this.fields != null && this.fields.length > 0) {
            super.modify(requestBuilder, httpMethod, modifiersChain);
        } else {
            modifiersChain.next(requestBuilder, httpMethod);
        }
    }

    @Override
    protected void appendToQueryParameters(ApiRequestBuilder requestBuilder) {
        StringBuilder sb = new StringBuilder();
        for (String field : this.fields) {
            sb.append(field).append(',');
        }
        sb.setLength(sb.length() - 1); // remove the last comma
        requestBuilder.setQueryParameter(GET_API_OPTION_PREFIX + OPTION_FIELDS, sb.toString());
    }

    @Override
    protected void appendToJsonOptions(JSONObject options) {
        options.put(OPTION_FIELDS, Arrays.asList(this.fields));
    }
}
