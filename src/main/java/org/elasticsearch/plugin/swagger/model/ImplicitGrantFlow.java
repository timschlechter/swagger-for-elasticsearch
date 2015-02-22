package org.elasticsearch.plugin.swagger.model;

/**
 * Provides details regarding the OAuth2's Implicit Grant flow type.
 */
public class ImplicitGrantFlow extends SwaggerModel {
    /**
     * The login endpoint definition.
     */
    private LoginEndpoint loginEndpoint;

    /**
     * An optional alternative name to standard "access_token" OAuth2 parameter.
     */
    private String tokenName;
}