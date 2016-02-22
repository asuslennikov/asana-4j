package ru.jewelline.asana.core;

import ru.jewelline.asana.core.utils.PagedList;
import ru.jewelline.request.http.HttpResponseReceiver;

public interface EntityResponseReader<T, E> extends HttpResponseReceiver {

    T toEntity();

    PagedList<T> toEntityList();

    boolean hasError();

    E getError();
}
