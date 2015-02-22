package org.elasticsearch.plugin.swagger.model;

/**
 * An enum representing the type of the authorization scheme.
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