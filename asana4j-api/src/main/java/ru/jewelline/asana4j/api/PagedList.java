package ru.jewelline.asana4j.api;

import ru.jewelline.asana4j.api.params.Pagination;
import ru.jewelline.asana4j.api.params.QueryParameter;

import java.util.ArrayList;

public class PagedList<T> extends ArrayList<T> {
    private final Pagination pagination;

    public PagedList(Pagination pagination) {
        super(pagination != null ? pagination.getLimit() : Pagination.DEFAULT_LIMIT);
        this.pagination = pagination;
    }

    public boolean hasNextPage(){
        return this.pagination != null && this.pagination.getOffsetToken() != null;
    }

    public QueryParameter getNextPageQueryParameter(){
        if (!hasNextPage()) {
            return null;
        }
        return this.pagination;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName());
        if (this.pagination != null){
            sb.append(", limit = ").append(this.pagination.getLimit());
        }
        if (hasNextPage()) {
            sb.append(", has next");
        }
        sb.append(" ").append(super.toString());
        return sb.toString();
    }
}
