package ru.jewelline.asana4j.api.modifiers;

import ru.jewelline.asana.core.utils.PagedList;
import ru.jewelline.request.http.modifiers.RequestModifier;

import java.util.ArrayList;

public class PaginatedList<T> extends ArrayList<T> implements PagedList<T> {
    private final Pagination pagination;

    public PaginatedList(Pagination pagination) {
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
            sb.append(", limit=").append(this.pagination.getLimit());
        }
        if (hasNextPage()) {
            sb.append(", has next");
        }
        sb.append(' ').append(super.toString());
        return sb.toString();
    }
}
