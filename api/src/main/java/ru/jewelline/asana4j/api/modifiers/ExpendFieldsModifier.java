package ru.jewelline.asana4j.api.modifiers;

import org.json.JSONObject;
import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.HttpRequestBuilder;
import ru.jewelline.request.http.modifiers.ModifiersChain;

import java.util.Arrays;

public class ExpendFieldsModifier extends ApiOptionModifier {
    private static final String OPTION_FIELDS = "expand";
    private final String[] fields;

    public ExpendFieldsModifier(String... fields) {
        this.fields = fields;
    }

    @Override
    public void modify(HttpRequestBuilder requestBuilder, HttpMethod httpMethod, ModifiersChain modifiersChain) {
        if (this.fields != null && this.fields.length > 0) {
            super.modify(requestBuilder, httpMethod, modifiersChain);
        } else {
            modifiersChain.next(requestBuilder, httpMethod);
        }
    }

    @Override
    protected void appendToQueryParameters(HttpRequestBuilder requestBuilder) {
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
