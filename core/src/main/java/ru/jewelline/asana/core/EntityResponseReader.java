package ru.jewelline.asana.core;

import ru.jewelline.asana.core.utils.PagedList;
import ru.jewelline.request.http.HttpResponseReceiver;

public interface EntityResponseReader<T> extends HttpResponseReceiver {

    T toEntity();

    PagedList<T> toEntityList();
}
