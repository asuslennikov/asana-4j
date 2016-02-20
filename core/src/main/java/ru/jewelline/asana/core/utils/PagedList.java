package ru.jewelline.asana.core.utils;

import ru.jewelline.request.http.modifiers.RequestModifier;

import java.util.List;

public interface PagedList<T> extends List<T> {

    boolean hasNextPage();

    RequestModifier getNextPageModifier();
}
