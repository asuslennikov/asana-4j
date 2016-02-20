package ru.jewelline.request.http;


import ru.jewelline.request.http.entity.SerializableEntity;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * This class allows to build HTTP requests with headers, query parameters and payload
 */
public interface HttpRequestBuilder {

    String getUrl();

    /**
     * Sets a base path fot the request
     *
     * @param url base part of future request. It is mandatory and if it was missed during creation, the
     *            {@link NetworkException} will be thrown during execution.
     *            <p>For example:
     *            <code>
     *            http://www.example.com/resource/search
     *            </code>
     *            <p/>
     *            If url doesn't have a <code>http://</code> or <code>https://</code> prefix, the default one
     *            (<code>http://</code>) will be added.
     * @return The request builder
     */
    HttpRequestBuilder setUrl(String url);

    /**
     * Adds query parameter to your request. If you call this method twice for the same parameterKey, the second call
     * will override value for the parameterKey.
     *
     * @param name  name for your query parameter, will be encoded, can not be <code>null</code>
     * @param value value for you query parameter, will be encoded, can be <code>null</code>
     * @return The request builder
     */
    HttpRequestBuilder setQueryParameter(String name, String... value);

    Map<String, List<String>> getQueryParameters();

    /**
     * Adds request headers. If you call this method twice for the same headerKey, the second call
     * will override value for the headerKey.
     *
     * @param name  name for HTTP header, can not be <code>null</code>
     * @param value value for HTTP header, can be <code>null</code>
     * @return The request builder
     */
    HttpRequestBuilder setHeader(String name, String... value);

    Map<String, List<String>> getHeaders();

    /**
     * Adds body for you request. Has no effect if you execute this request as HTTP GET request.
     *
     * @param entity payload for your request.
     * @return The request builder.
     */
    HttpRequestBuilder setEntity(SerializableEntity entity);

    SerializableEntity getEntity();

    /**
     * Adds body for you request. Has no effect if you execute this request as HTTP GET request.
     *
     * @param stream payload for your request.
     * @return The request builder.
     */
    HttpRequestBuilder setEntity(InputStream stream);

    /**
     * Finalize the creation process of HTTP request.
     *
     * @return An instance of HttpRequest with specified headers, query parameters and body.
     */
    HttpRequest buildAs(HttpMethod httpMethod);
}
