package ru.jewelline.asana.common;

import ru.jewelline.request.http.modifiers.RequestModifier;

import java.util.List;

public interface PagedList<T> extends List<T> {

    boolean hasNextPage();

    RequestModifier getNextPageModifier();
}
