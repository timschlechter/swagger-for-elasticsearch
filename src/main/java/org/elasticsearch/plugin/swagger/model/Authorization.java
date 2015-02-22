package org.elasticsearch.plugin.swagger.model;

import java.util.List;

/**
 * The Authorization object provides information about a specific authorization scheme.
 */
public class Authorization extends SwaggerModel {
    /**
     * The type of the authorization scheme.
     */
    private AuthorizationType type;

    /**
     * Denotes how the API key must be passed.
     */
    private PassType passAs;

    /**
     * The name of the header or query parameter to be used when passing the API key.
     */
    private String keyname;

    /**
     * A list of supported OAuth2 scopes.
     */
    private List<OAuth2Scope> scopes;

    /**
     * Detailed information about the grant types supported by the oauth2 authorization scheme.
     */
    private GrantTypes grantTypes;
}