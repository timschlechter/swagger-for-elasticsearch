package org.elasticsearch.plugin.swagger.model;

/**
 * Provides details regarding the OAuth2's Token Endpoint.
 */
public class TokenEndpoint extends SwaggerModel {
    /**
     * The URL of the token endpoint for the authentication code grant flow.
     */
    private String url;

    /**
     * An optional alternative name to standard "access_token" OAuth2 parameter.
     */
    private String tokenName;
}