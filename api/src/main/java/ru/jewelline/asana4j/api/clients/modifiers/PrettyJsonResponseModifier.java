package ru.jewelline.asana4j.api.clients.modifiers;

import org.json.JSONObject;
import ru.jewelline.request.http.HttpRequestBuilder;

public class PrettyJsonResponseModifier extends ApiOptionModifier {
    private static final String OPTION_PRETTY = "pretty";


    @Override
    protected void appendToQueryParameters(HttpRequestBuilder requestBuilder) {
        requestBuilder.setQueryParameter(GET_API_OPTION_PREFIX + OPTION_PRETTY, String.valueOf(true));
    }

    @Override
    protected void appendToJsonOptions(JSONObject options) {
        options.put(OPTION_PRETTY, true);
    }
}
