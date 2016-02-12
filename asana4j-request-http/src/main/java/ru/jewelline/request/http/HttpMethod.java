package ru.jewelline.request.http;

/**
 * This enumeration defines supported HTTP request methods.
 */
public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    ;

    private String httpMethod;

    HttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String method(){
        return this.httpMethod;
    }
}
