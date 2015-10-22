package ru.jewelline.asana4j.api.clients.modifiers;

import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.http.HttpMethod;

public class Fields implements RequestModifier {
    protected static final String GET_API_OPTION_PREFIX = "opt_";
    private final String[] fields;

    public Fields(String... fields) {
        this.fields = fields;
    }

    @Override
    public int priority() {
        return RequestModifier.PRIORITY_BASE_MODIFIER;
    }

    @Override
    public void modify(ApiRequestBuilder requestBuilder, HttpMethod httpMethod, ModifiersChain modifiersChain) {
        if (this.fields != null && this.fields.length > 0) {
            if (HttpMethod.GET == httpMethod) {
                StringBuilder sb = new StringBuilder();
                for (String field : this.fields) {
                    sb.append(field).append(",");
                }
                sb.setLength(sb.length() - 1); // remove the last comma
                requestBuilder.setQueryParameter(GET_API_OPTION_PREFIX + ApiRequestBuilder.OPTION_FIELDS, sb.toString());
            } else {

            }
        }
        modifiersChain.next(requestBuilder, httpMethod);
    }
}
