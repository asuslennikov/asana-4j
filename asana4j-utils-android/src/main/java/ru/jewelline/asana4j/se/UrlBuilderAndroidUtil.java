package ru.jewelline.asana4j.se;

import android.net.Uri;
import ru.jewelline.asana4j.utils.URLBuilder;

import java.util.HashMap;
import java.util.Map;

public class UrlBuilderAndroidUtil implements URLBuilder {
    private String baseUrl;
    private Map<String, String> queryParameters;

    public UrlBuilderAndroidUtil() {
        this.queryParameters = new HashMap<>();
    }

    @Override
    public URLBuilder path(String path) {
        this.baseUrl = path;
        return this;
    }

    public URLBuilder addQueryParameter(String key, String value){
        this.queryParameters.put(key, value);
        return this;
    }

    public String build(){
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.path(this.baseUrl);
        for (Map.Entry<String, String> queryParameter : queryParameters.entrySet()) {
            uriBuilder.appendQueryParameter(queryParameter.getKey(), queryParameter.getValue());
        }
        return uriBuilder.build().toString();
    }
}
