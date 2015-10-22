package ru.jewelline.asana4j.api.clients.modifiers;

import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.http.HttpMethod;

/**
 * This interface describes request modifiers. These objects can change any value in request corresponding to their logic.
 * Implementation of this interface can add additional query parameters to request (for example for pagination),
 * modify entity body (for example attach API options, such as JSON formatting, selected fields and etc), add additional
 * headers (for example authorization header), enable GZIP compression and so on.
 */
public interface RequestModifier {

    int PRIORITY_VALIDATOR = 100;
    int PRIORITY_BASE_MODIFIER = 1000;
    int PRIORITY_API_OPTIONS = 10000;
    int PRIORITY_ENCODER = 100000;

    /**
     * Return priority for request modifier. All modifiers which were assigned for a specific request will be sorted
     * by value of this property and called one-by-one starting from modifier with the smaller priority value
     *
     * @return priority value
     */
    int priority();

    /**
     * In this method request modifier can change any value in the request builder (and even create a new one and send it
     * to other modifiers). Basically all implementation will call {@link ModifiersChain#next(ApiRequestBuilder, HttpMethod)},
     * but if it required by logic of your modifier, it is possible to stop further processing. There is no guarantee
     * that next modifier will be called immediately after the {@link ModifiersChain#next(ApiRequestBuilder, HttpMethod)} call.
     * For now it is true, but in future this call can just mark the next modifier for execution.
     *
     * @param requestBuilder a builder which contains actual information about request. It is never can be <code>null</code>.
     * @param httpMethod     a HTTP method (one of values from {@link HttpMethod} enum) which will be used for sending this request
     * @param modifiersChain ordered list which holds all registered request modifiers
     */
    <T> void modify(ApiRequestBuilder<T> requestBuilder, HttpMethod httpMethod, ModifiersChain modifiersChain);
}
