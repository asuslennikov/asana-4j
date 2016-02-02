package ru.jewelline.request.http;

import java.io.InputStream;

/**
 * This class allows to build HTTP requests with headers, query parameters and payload
 */
public interface HttpRequestBuilder {
    /**
     * Sets a base path fot the request
     *
     * @param url base part of future request. It is mandatory and if it was missed during creation, the
     *            {@link NetworkException} will be thrown in the {@link #buildAs(HttpMethod)} method.
     *            For example: <p/>
     *            <code>
     *            http://www.example.com/resource/search
     *            </code><p/>
     *            If url doesn't have a <code>http://</code> or <code>https://</code> prefix, the default one
     *            (<code>http://</code>) will be added.
     * @return The request builder
     */
    HttpRequestBuilder path(String url);

    /**
     * Adds query parameter to your request. If you call this method twice for the same parameterKey, the second call
     * will override value for the parameterKey.
     *
     * @param parameterKey   name for your query parameter, will be encoded
     * @param parameterValue value for you query parameter, will be encoded
     * @return The request builder
     */
    HttpRequestBuilder setQueryParameter(String parameterKey, String parameterValue);

    /**
     * Adds request headers. If you call this method twice for the same headerKey, the second call
     * will override value for the headerKey.
     *
     * @param headerKey   name for HTTP header
     * @param headerValue value for HTTP header
     * @return The request builder
     */
    HttpRequestBuilder setHeader(String headerKey, String headerValue);

    /**
     * Adds body to you request. It has no effect if you execute this request as GET HTTP request
     *
     * @param requestBody payload for your request
     * @return The request builder
     */
    HttpRequestBuilder entity(byte[] requestBody);

    /**
     * Adds body for you request. Has no effect if you execute this request as HTTP GET request.
     *
     * @param entityStream payload for your request.
     * @return The request builder.
     */
    HttpRequestBuilder entity(InputStream entityStream);

    /**
     * Finalize the creation process of HTTP request.
     *
     * @return An instance of HttpRequest with specified headers, query parameters and body.
     * @throws NetworkException with {@link NetworkException#MALFORMED_URL} code will be thrown if you didn't specify
     *                          an url (see {@link #path(String)})
     */
    HttpRequest buildAs(HttpMethod method) throws NetworkException;
}
