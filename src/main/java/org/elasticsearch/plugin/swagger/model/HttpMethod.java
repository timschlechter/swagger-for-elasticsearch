package org.elasticsearch.plugin.swagger.model;

/**
 * An enum representing standard HTTP methods.
 */
public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    PATCH("PATCH"),
    DELETE("DELETE"),
    OPTIONS("OPTIONS");

    private final String value;

    HttpMethod(String method) {
        this.value = method;
    }

    public String value() {
        return this.value;
    }
}