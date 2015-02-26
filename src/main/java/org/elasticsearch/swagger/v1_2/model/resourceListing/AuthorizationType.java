package org.elasticsearch.swagger.v1_2.model.resourceListing;

/**
 * An enum representing the model of the authorization scheme.
 */
enum AuthorizationType {
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