package ru.jewelline.asana.common;

import ru.jewelline.request.http.HttpResponseReceiver;

public interface EntityResponseReceiver<T> extends HttpResponseReceiver {

    T asEntity();

    PagedList<T> asEntityList();
}
