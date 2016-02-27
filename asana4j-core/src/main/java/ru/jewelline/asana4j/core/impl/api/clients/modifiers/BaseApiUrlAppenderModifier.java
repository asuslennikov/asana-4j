package ru.jewelline.asana4j.core.impl.api.clients.modifiers;

import ru.jewelline.asana4j.utils.StringUtils;
import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.HttpRequestBuilder;
import ru.jewelline.request.http.modifiers.ModifiersChain;
import ru.jewelline.request.http.modifiers.RequestModifier;

public class BaseApiUrlAppenderModifier implements RequestModifier {
    private static final String BASE_API_URL = "https://app.asana.com/api/1.0/";
    private static final String TRANSPORT_MARKER = "://";

    @Override
    public int priority() {
        return RequestModifier.PRIORITY_BASE_MODIFIER;
    }

    @Override
    public void modify(HttpRequestBuilder requestBuilder, HttpMethod httpMethod, ModifiersChain modifiersChain) {
        String url = requestBuilder.getUrl();
        String firstUrlSegment = StringUtils.getSubstring(url, TRANSPORT_MARKER, "/");
        if (!firstUrlSegment.contains("app.asana.com") && !firstUrlSegment.contains(".")) {
            requestBuilder.setUrl(BASE_API_URL + url.substring(url.indexOf(TRANSPORT_MARKER) + TRANSPORT_MARKER.length()));
        }
    }
}
