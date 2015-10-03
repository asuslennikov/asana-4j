package ru.jewelline.asana4j.api.clients.modifiers;

import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.http.HttpMethod;

import java.util.Arrays;
import java.util.Comparator;

/**
 * This class represents an ordered list of all registered request modifiers. It is immutable class and after its creation
 * it is not possible to add or remove any modifier.
 */
public final class ModifiersChain<T> {
    private final RequestModifier[] requestModifiers;
    private final HttpMethod httpMethod;
    private ApiRequestBuilder requestBuilder;
    private int counter;

    public ModifiersChain(RequestModifier[] requestModifiers, HttpMethod httpMethod) {
        this.requestModifiers = requestModifiers;
        this.httpMethod = httpMethod;
        if (this.requestModifiers != null && this.requestModifiers.length > 0) {
            Arrays.sort(requestModifiers, new RequestModifierComparator());
        }
        this.counter = -1;
    }

    /**
     * Controls execution flow for request modifiers.
     * <br /><b>There is no guarantee</b> that next modifier will be called immediately after the method call.
     * For now it is true, but in future this call can just mark the next modifier for execution, i.e. implementation of
     * this method is not a part of public API and can be changed even with minor update.
     * @param requestBuilder never can be <code>null</code>. If RequestModifier implementation passes a <code>null</code>
     *                       object as an argument the {@link IllegalArgumentException} will be raised.
     */
    public ApiRequestBuilder<?> next(ApiRequestBuilder<?> requestBuilder){
        if (requestBuilder == null){
            throw new IllegalArgumentException("The requestBuilder can not be null.");
        }
        this.requestBuilder = requestBuilder;
        while (this.counter < (this.requestModifiers.length - 1)){
            this.counter++;
            RequestModifier modifier = this.requestModifiers[this.counter];
            if (modifier != null) {
                modifier.modify(requestBuilder, this.httpMethod, this);
                break;
            }
            // if the modifier is null, just pick the next one
        }
        return this.requestBuilder;
    }

    private static class RequestModifierComparator implements Comparator<RequestModifier>{
        @Override
        public int compare(RequestModifier rm1, RequestModifier rm2) {
            if (rm1 != null && rm2 != null) {
                return rm1.priority() - rm2.priority(); // TODO fix potential overflow
            }
            return 0;
        }
    }
}
