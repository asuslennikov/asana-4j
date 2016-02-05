package ru.jewelline.request.http.impl;

import ru.jewelline.request.http.UrlBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

final class UrlBuilderImpl implements UrlBuilder {
    private final Charset urlCharset;
    private String path;
    private final Map<String, String> queryParameters = new HashMap<>();

    UrlBuilderImpl(Charset urlCharset) {
        this.urlCharset = urlCharset;
    }

    @Override
    public UrlBuilder path(String path) {
        this.path = path;
        return this;
    }

    @Override
    public UrlBuilder addQueryParameter(String key, String value) {
        this.queryParameters.put(key, value);
        return this;
    }

    @Override
    public String build() {
        StringBuilder pathBuilder = new StringBuilder(this.path != null ? this.path : "");
        if (!this.queryParameters.isEmpty()) {
            StringBuilder queryStringBuilder = new StringBuilder();
            Iterator<Map.Entry<String, String>> paramsItr = this.queryParameters.entrySet().iterator();
            while (paramsItr.hasNext()) {
                Map.Entry<String, String> param = paramsItr.next();
                queryStringBuilder.append(encode(param.getKey())).append('=').append(encode(param.getValue()));
                if (paramsItr.hasNext()) {
                    queryStringBuilder.append('&');
                }
            }
            pathBuilder.append('?').append(queryStringBuilder.toString());
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
