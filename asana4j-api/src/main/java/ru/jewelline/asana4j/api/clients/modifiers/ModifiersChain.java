package ru.jewelline.asana4j.api.clients.modifiers;

import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.http.HttpMethod;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;

/**
 * This class represents an ordered list of all registered request modifiers. It is immutable class and after its creation
 * it is not possible to add or remove any modifier.
 */
public final class ModifiersChain {
    private static final RequestModifierComparator REQUEST_MODIFIER_COMPARATOR = new RequestModifierComparator();
    private final RequestModifier[] requestModifiers;

    private HttpMethod httpMethod;
    private ApiRequestBuilder requestBuilder;
    private int counter;

    public ModifiersChain(RequestModifier[] requestModifiers) {
        this.requestModifiers = requestModifiers; // in fact here will be better to create a copy of incoming array
        if (this.requestModifiers != null && this.requestModifiers.length > 0) {
            Arrays.sort(this.requestModifiers, REQUEST_MODIFIER_COMPARATOR);
        }
        this.counter = -1;
    }

    /**
     * Controls execution flow for request modifiers.
     * <br /><b>There is no guarantee</b> that next modifier will be called immediately after the method call.
     * For now it is true, but in future this call can just mark the next modifier for execution, i.e. implementation of
     * this method is not a part of public API and can be changed even with minor update.
     *
     * @param requestBuilder never can be <code>null</code>. If RequestModifier implementation passes a <code>null</code>
     *                       object as an argument the {@link IllegalArgumentException} will be raised.
     * @param httpMethod     never can be <code>null</code>. If RequestModifier implementation passes a <code>null</code>
     *                       object as an argument the {@link IllegalArgumentException} will be raised.
     */
    public void next(ApiRequestBuilder<?> requestBuilder, HttpMethod httpMethod) {
        if (requestBuilder == null) {
            throw new IllegalArgumentException("The requestBuilder can not be null.");
        }
        if (httpMethod == null) {
            throw new IllegalArgumentException("The httpMethod can not be null.");
        }
        if (this.requestModifiers == null){
            return;
        }
        doNext(requestBuilder, httpMethod);
    }

    private void doNext(ApiRequestBuilder<?> requestBuilder, HttpMethod httpMethod) {
        this.requestBuilder = requestBuilder;
        this.httpMethod = httpMethod;
        while (this.counter < (this.requestModifiers.length - 1)) {
            this.counter++;
            RequestModifier modifier = this.requestModifiers[this.counter];
            if (modifier != null) {
                modifier.modify(this.requestBuilder, this.httpMethod, this);
                break;
            }
            // if the modifier is null, just pick the next one
        }
    }

    /**
     * @return a {@link HttpMethod} for request
     */
    public HttpMethod getHttpMethod() {
        return this.httpMethod;
    }

    /**
     * @return a builder for API request
     */
    public ApiRequestBuilder getRequestBuilder() {
        return this.requestBuilder;
    }

    private static class RequestModifierComparator implements Comparator<RequestModifier>, Serializable {
        @Override
        public int compare(RequestModifier rm1, RequestModifier rm2) {
            if (rm1 != null && rm2 != null) {
                int p1 = rm1.priority();
                int p2 = rm2.priority();
                return p1 < p2 ? -1 : (p1 == p2 ? 0 : 1);
            }
            return 0;
        }
    }
}
