package ru.jewelline.request.api.modifiers;

import ru.jewelline.request.api.ApiRequestBuilder;
import ru.jewelline.request.http.HttpMethod;

/**
 * This interface describes request modifiers. These objects can change any value in request builder corresponding to
 * their logic. Implementation of this interface can add additional query parameters to request (for example for pagination),
 * modify entity body (for example attach API options, such as JSON formatting, selected fields and etc), add additional
 * headers (for example authorization header), enable GZIP compression and so on.
 */
public interface RequestModifier {

    int PRIORITY_VALIDATOR = 100;
    int PRIORITY_BASE_MODIFIER = 1000;
    int PRIORITY_API_OPTIONS = 10000;
    int PRIORITY_ENCODER = 100000;

    /**
     * Returns priority for request modifier. All modifiers which were assigned for a specific request will be sorted
     * by value of this property and called one-by-one starting from modifier with the smaller priority value.
     *
     * @return A priority value.
     */
    int priority();

    /**
     * In this method request modifier can change any value in the request builder (and even create a new one and
     * send it to other modifiers). Basically all implementation will call
     * {@link ModifiersChain#next(ApiRequestBuilder, HttpMethod)}, but if it is required by logic of your modifier,
     * you can stop further processing. There is no guarantee that next modifier will be called immediately after the
     * {@link ModifiersChain#next(ApiRequestBuilder, HttpMethod)} call. For now it is true, but in future this call
     * can just mark the next modifier for execution.
     *
     * @param requestBuilder a builder which contains actual information about request. It is never can be <code>null</code>.
     * @param httpMethod     a HTTP method (one of values from {@link HttpMethod} enum) which will be used for
     *                       sending this request. It is never can be <code>null</code>.
     * @param modifiersChain ordered list which holds all registered request modifiers.
     */
    void modify(ApiRequestBuilder requestBuilder, HttpMethod httpMethod, ModifiersChain modifiersChain);
}
