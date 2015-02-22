package org.elasticsearch.plugin.swagger.model;

/**
 * An enum representing the type of an operation parameter.
 */
public enum ParameterType {
    /**
     * Denotes a path parameter.
     */
    path,

    /**
     * Denotes a query parameter.
     */
    query,

    /**
     * Denotes a body parameter.
     */
    body,

    /**
     * Denotes a header parameter.
     */
    header,

    /**
     * Denotes a form parameter.
     */
    form
}