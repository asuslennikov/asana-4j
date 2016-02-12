package ru.jewelline.asana4j.core.impl;

import ru.jewelline.asana.common.PagedList;
import ru.jewelline.asana4j.core.api.modifiers.Pagination;
import ru.jewelline.request.http.modifiers.RequestModifier;

import java.util.ArrayList;

public class PagedListImpl<T> extends ArrayList<T> implements PagedList<T> {
    private final Pagination pagination;

    public PagedListImpl(Pagination pagination) {
        super(pagination != null ? pagination.getLimit() : Pagination.DEFAULT_LIMIT);
        this.pagination = pagination;
    }

    public boolean hasNextPage() {
        return this.pagination != null && this.pagination.getOffsetToken() != null;
    }

    public RequestModifier getNextPageModifier() {
        if (!hasNextPage()) {
            return null;
        }
        return this.pagination;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName());
        if (this.pagination != null) {
            sb.append(", limit = ").append(this.pagination.getLimit());
        }
        if (hasNextPage()) {
            sb.append(", has next");
        }
        sb.append(' ').append(super.toString());
        return sb.toString();
    }
}
