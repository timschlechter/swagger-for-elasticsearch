package org.elasticsearch.plugin.swagger.model;

/**
 * Provides details regarding the OAuth2's Authorization Code Grant flow type.
 */
public class AuthorizationCodeGrantFlow extends SwaggerModel {
    /**
     * The token request endpoint definition.
     */
    private TokenRequestEndpoint tokenRequestEndpoint;

    /**
     * The token endpoint definition.
     */
    private TokenEndpoint tokenEndpoint;
}
