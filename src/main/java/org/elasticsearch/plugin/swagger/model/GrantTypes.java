package org.elasticsearch.plugin.swagger.model;

/**
 * Provides details regarding the OAuth2 grant types that are supported by the API.
 */
public class GrantTypes extends SwaggerModel {
    /**
     * The Implicit Grant flow definition.
     */
    private ImplicitGrantFlow implicit;

    /**
     * The Authorization Code Grant flow definition.
     */

    private AuthorizationCodeGrantFlow authorization_code;
}