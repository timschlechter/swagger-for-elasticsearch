package org.elasticsearch.plugin.swagger.model;

/**
 * Provides details regarding the OAuth2's Authorization Endpoint.
 */
public class TokenRequestEndpoint extends SwaggerModel {
    /**
     * The URL of the authorization endpoint for the authentication code grant flow.
     */
    private String url;

    /**
     * An optional alternative name to standard "client_id" OAuth2 parameter.
     */
    private String clientIdName;

    /**
     * An optional alternative name to standard "client_secret" OAuth2 parameter.
     */
    private String clientSecretName;
}