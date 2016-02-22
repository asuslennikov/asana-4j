package ru.jewelline.asana4j.api.modifiers;

import ru.jewelline.request.http.HttpRequestBuilder;

public class PrettyJsonResponseModifier extends ApiOptionModifier {
    private static final String OPTION_PRETTY = "pretty";


    @Override
    protected void appendToQueryParameters(HttpRequestBuilder requestBuilder) {
        requestBuilder.setQueryParameter(GET_API_OPTION_PREFIX + OPTION_PRETTY, String.valueOf(true));
    }
}
