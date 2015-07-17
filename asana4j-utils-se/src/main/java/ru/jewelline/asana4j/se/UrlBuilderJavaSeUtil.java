package ru.jewelline.asana4j.se;

import ru.jewelline.asana4j.utils.URLBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UrlBuilderJavaSeUtil implements URLBuilder {
    private String path;
    private Map<String, String>  queryParameters = new HashMap<>();

    @Override
    public URLBuilder path(String path) {
        this.path = path;
        return this;
    }

    @Override
    public URLBuilder addQueryParameter(String key, String value) {
        this.queryParameters.put(key, value);
        return this;
    }

    @Override
    public String build() {
        StringBuilder pathBuilder = new StringBuilder(this.path != null ? this.path : "");
        if (!this.queryParameters.isEmpty()){
            StringBuilder queryStringBuilder = new StringBuilder();
            Iterator<Map.Entry<String, String>> paramsItr = this.queryParameters.entrySet().iterator();
            while (paramsItr.hasNext()){
                Map.Entry<String, String> param = paramsItr.next();
                queryStringBuilder.append(encode(param.getKey())).append("=").append(encode(param.getValue()));
                if (paramsItr.hasNext()){
                    queryStringBuilder.append("&");
                }
            }
            pathBuilder.append("?").append(queryStringBuilder.toString());
        }
        return pathBuilder.toString();
    }

    private String encode(String str){
        String result = str;
        if (str != null) {
            try {
                result = URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                // try with default charset
                try {
                    result = URLEncoder.encode(str, Charset.defaultCharset().displayName());
                } catch (UnsupportedEncodingException e2){
                    // this shouldn't happen because we use a default charset
                    result = str;
                }
            }
        } else {
            result = "";
        }
        return result;
    }
}
