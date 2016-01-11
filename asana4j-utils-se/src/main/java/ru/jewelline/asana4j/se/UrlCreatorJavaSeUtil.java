package ru.jewelline.asana4j.se;

import ru.jewelline.asana4j.utils.StringUtils;
import ru.jewelline.asana4j.utils.URLCreator;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UrlCreatorJavaSeUtil implements URLCreator {

    @Override
    public URLCreator.Builder builder() {
        return new Builder();
    }

    private static class Builder implements URLCreator.Builder {
        private String path;
        private Map<String, String> queryParameters = new HashMap<>();

        @Override
        public Builder path(String path) {
            this.path = path;
            return this;
        }

        @Override
        public Builder addQueryParameter(String key, String value) {
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
            String result = str;
            if (str != null) {
                try {
                    result = URLEncoder.encode(str, StringUtils.getCharset().displayName());
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
}
