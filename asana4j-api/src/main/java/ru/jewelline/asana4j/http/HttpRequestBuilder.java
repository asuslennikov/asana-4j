package ru.jewelline.asana4j.http;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * This class allows us to build complex HTTP requests with headers and query parameters
 */
public interface HttpRequestBuilder {
    /**
     * Sets a base path fot the request
     * @param baseUrl base part of future request. It is mandatory part and if it is missed, the {@link NetworkException}
     *                will be thrown during the {@link #buildAs(HttpMethod)} method. Basically it contains server and resource address. <br />
     *                For example: <br />
     *                <code>
     *                http://www.example.com/resource/search
     *                </code><br />
     *                If baseUrl doesn't have a <code>http://</code> or <code>https://</code> prefix, the default one
     *                (<code>http://</code>) will be added.
     * @return The request builder
     */
    HttpRequestBuilder path(String baseUrl);

    /**
     * Adds query parameter to your request. If you call this method twice for the same parameterKey, the second call
     * will override value for the parameterKey.
     * @param parameterKey name for your query parameter, will be encoded
     * @param parameterValue value for you query parameter, will be encoded
     * @return The request builder
     */
    HttpRequestBuilder setQueryParameter(String parameterKey, String parameterValue);

    /**
     * Adds request headers. If you call this method twice for the same headerKey, the second call
     * will override value for the headerKey.
     * @param headerKey name for HTTP header
     * @param headerValue value for HTTP header
     * @return The request builder
     */
    HttpRequestBuilder setHeader(String headerKey, String headerValue);

    /**
     * Adds body for you request. Has no effect if you execute this request as GET HTTP request
     * @param requestBody payload for your request
     * @return The request builder
     */
    HttpRequestBuilder entity(byte[] requestBody);
    /**
     * Adds body for you request. Has no effect if you execute this request as GET HTTP request
     * @param entityStream payload for your request
     * @return The request builder
     */
    HttpRequestBuilder entity(InputStream entityStream);

    /**
     * Creates an instance of HttpRequest
     * @return an instance of HttpRequest with specified headers, query parameters and body
     * @throws NetworkException with {@link NetworkException#MALFORMED_URL} code will be thrown if you didn't specify
     * a base url (see {@link #path(String)})
     */
    <O extends OutputStream> HttpRequest<O> buildAs(HttpMethod method) throws NetworkException;
}
