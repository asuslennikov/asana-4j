package ru.jewelline.asana4j.api.modifiers;

import org.json.JSONObject;
import ru.jewelline.request.api.ApiRequestBuilder;
import ru.jewelline.request.api.modifiers.ModifiersChain;
import ru.jewelline.request.http.HttpMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        sb.append("id");
        sb.setLength(sb.length() - 1); // remove the last comma
        requestBuilder.setQueryParameter(GET_API_OPTION_PREFIX + OPTION_FIELDS, sb.toString());
    }

    @Override
    protected void appendToJsonOptions(JSONObject options) {
        List<String> fielsdsForQuery = new ArrayList<>(Arrays.asList(this.fields));
        fielsdsForQuery.add("id");
        options.put(OPTION_FIELDS, fielsdsForQuery);
    }
}
