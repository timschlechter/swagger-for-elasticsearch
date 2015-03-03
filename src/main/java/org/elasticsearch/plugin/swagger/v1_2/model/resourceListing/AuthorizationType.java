package org.elasticsearch.plugin.swagger.v1_2.model.resourceListing;

/**
 * An enum representing the model of the authorization scheme.
 */
public enum AuthorizationType {
    /**
     * Basic HTTP authentication.
     */
    basicAuth,

    /**
     * API-key based authentication.
     */
    apiKey,

    /**
     * OAuth2 based authentication.
     */
    oauth2
}