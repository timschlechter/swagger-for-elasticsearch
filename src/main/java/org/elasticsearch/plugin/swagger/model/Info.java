package org.elasticsearch.plugin.swagger.model;

/**
 * The Info object provides metadata about the API. The metadata can be used by the clients if
 * needed, and can be presented in the Swagger-UI for convenience.
 */
public class Info extends SwaggerModel {
    /**
     * The title of the application.
     */
    private String title;

    /**
     * A short description of the application.
     */
    private String description;

    /**
     * A URL to the Terms of Service of the API.
     */
    private String termsOfServiceUrl;

    /**
     * An email to be used for API-related correspondence.
     */
    private String contact;

    /**
     * The license name used for the API.
     */
    private String license;

    /**
     * A URL to the license used for the API.
     */
    private String licenseUrl;
}