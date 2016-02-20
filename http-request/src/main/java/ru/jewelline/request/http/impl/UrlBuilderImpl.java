package ru.jewelline.request.http.impl;

import ru.jewelline.request.http.UrlBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class UrlBuilderImpl implements UrlBuilder {
    private final Charset urlCharset;
    private Map<String, List<String>> queryParameters = new HashMap<>();
    private String path;

    UrlBuilderImpl(Charset urlCharset) {
        this.urlCharset = urlCharset;
    }

    @Override
    public UrlBuilder path(String path) {
        this.path = path;
        return this;
    }

    @Override
    public UrlBuilder setQueryParameter(String key, String... values) {
        if (key != null) {
            if (values == null) {
                this.queryParameters.remove(key);
            } else {
                this.queryParameters.put(key, Collections.unmodifiableList(Arrays.asList(values.clone())));
            }
        }
        return this;
    }

    void setQueryParameter(Map<String, List<String>> queryParameters) {
        this.queryParameters = queryParameters;
    }

    @Override
    public String build() {
        StringBuilder pathBuilder = new StringBuilder(this.path != null ? this.path : "");
        if (!this.queryParameters.isEmpty()) {
            StringBuilder queryStringBuilder = new StringBuilder();
            for (Map.Entry<String, List<String>> queryParameter : queryParameters.entrySet()) {
                String encodedQueryParameterKey = encode(queryParameter.getKey());
                for (String queryParameterValue : queryParameter.getValue()) {
                    queryStringBuilder
                            .append(encodedQueryParameterKey)
                            .append('=')
                            .append(encode(queryParameterValue))
                            .append('&');
                }
            }
            if (queryStringBuilder.length() > 0) {
                queryStringBuilder.setLength(queryStringBuilder.length() - 1); // Trim the leading '&'
                pathBuilder.append('?').append(queryStringBuilder);
            }
        }
        return pathBuilder.toString();
    }

    private String encode(String str) {
        String result;
        if (str != null) {
            try {
                result = URLEncoder.encode(str, this.urlCharset.displayName()).replace("+", "%20");
            } catch (UnsupportedEncodingException e) {
                    /*
                     * can happen only if charset doesn't exist for the given name,
                     * but we use the charset instance, so it always exists
                     */
                result = str;
            }
        } else {
            result = "";
        }
        return result;
    }
}
