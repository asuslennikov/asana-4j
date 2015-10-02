package ru.jewelline.asana4j.api.options;

import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.http.HttpMethod;

public class Pagination implements RequestOption {
    public static final int DEFAULT_LIMIT = 25;
    private static final int MIN_LIMIT = 1;
    private static final int MAX_LIMIT = 100;

    public static final Pagination FIRST_PAGE = new Pagination(DEFAULT_LIMIT, null);

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

    public Pagination withOffsetToken(String offsetToken) {
        return new Pagination(this.limit, offsetToken);
    }

    public Pagination withLimit(int limit) {
        return new Pagination(limit, this.offsetToken);
    }

    public int getLimit() {
        return limit;
    }

    public String getOffsetToken() {
        return offsetToken;
    }

    @Override
    public <T> ApiRequestBuilder<T> applyTo(ApiRequestBuilder<T> requestBuilder, HttpMethod httpMethod) {
        requestBuilder.setQueryParameter("limit", String.valueOf(getLimit()));
        if (getOffsetToken() != null) {
            requestBuilder.setQueryParameter("offset", getOffsetToken());
        }
        return requestBuilder;
    }
}
