package ru.jewelline.asana4j.se;

import android.net.Uri;
import ru.jewelline.asana4j.utils.URLCreator;

import java.util.HashMap;
import java.util.Map;

public class UrlCreatorAndroidUtil implements URLCreator {

    @Override
    public URLCreator.Builder builder() {
        return new Builder();
    }

    private static class Builder implements URLCreator.Builder {
        private String baseUrl;
        private Map<String, String> queryParameters = new HashMap<>();

        @Override
        public Builder path(String path) {
            this.baseUrl = path;
            return this;
        }

        public Builder addQueryParameter(String key, String value) {
            this.queryParameters.put(key, value);
            return this;
        }

        public String build() {
            Uri.Builder uriBuilder = new Uri.Builder();
            uriBuilder.path(this.baseUrl);
            for (Map.Entry<String, String> queryParameter : queryParameters.entrySet()) {
                uriBuilder.appendQueryParameter(queryParameter.getKey(), queryParameter.getValue());
            }
            return uriBuilder.build().toString();
        }
    }
}
