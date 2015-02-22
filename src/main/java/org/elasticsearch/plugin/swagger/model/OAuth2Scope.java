package org.elasticsearch.plugin.swagger.model;

/**
 * Describes an OAuth2 authorization scope.
 */
public class OAuth2Scope extends SwaggerModel {
    /**
     * The name of the scope.
     */
    private String scope;

    /**
     * A short description of the scope.
     */
    private String description;
}