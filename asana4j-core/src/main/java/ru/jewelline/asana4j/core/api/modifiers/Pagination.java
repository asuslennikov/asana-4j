package ru.jewelline.asana4j.core.api.modifiers;

import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.HttpRequestBuilder;
import ru.jewelline.request.http.modifiers.ModifiersChain;
import ru.jewelline.request.http.modifiers.RequestModifier;

import java.io.Serializable;

public class Pagination implements RequestModifier, Serializable {
    public static final int DEFAULT_LIMIT = 25;
    public static final Pagination FIRST_PAGE = new Pagination(DEFAULT_LIMIT, null);
    private static final int MIN_LIMIT = 1;
    private static final int MAX_LIMIT = 100;
    private final int limit;
    private final String offsetToken;

    public Pagination(int limit, String offsetToken) {
        if (limit < MIN_LIMIT) {
            this.limit = MIN_LIMIT;
        } else if (limit > MAX_LIMIT) {
            this.limit = MAX_LIMIT;
        } else {
            this.limit = limit;
        }
        this.offsetToken = offsetToken;
    }

    public int getLimit() {
        return this.limit;
    }

    public String getOffsetToken() {
        return this.offsetToken;
    }

    @Override
    public int priority() {
        return RequestModifier.PRIORITY_API_OPTIONS;
    }

    @Override
    public void modify(HttpRequestBuilder requestBuilder, HttpMethod httpMethod, ModifiersChain modifiersChain) {
        requestBuilder.setQueryParameter("limit", String.valueOf(getLimit()));
        if (getOffsetToken() != null) {
            requestBuilder.setQueryParameter("offset", getOffsetToken());
        }
        modifiersChain.next(requestBuilder, httpMethod);
    }
}
