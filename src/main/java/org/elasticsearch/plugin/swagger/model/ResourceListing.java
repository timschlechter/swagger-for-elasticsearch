package org.elasticsearch.plugin.swagger.model;

import java.util.List;
import java.util.Map;

/**
 * The Resource Listing serves as the root document for the API description. It contains general
 * information about the API and an inventory of the available resources.
 */
public class ResourceListing extends SwaggerModel {
    /**
     * Specifies the Swagger Specification version being used. It can be used by the Swagger UI and
     * other clients to interpret the API listing.
     */
    private String swaggerVersion;

    /**
     * Lists the resources to be described by this specification implementation.
     */
    private List<Resource> apis;

    /**
     * Provides the version of the application API.
     */
    private String apiVersion;

    /**
     * Provides metadata about the API. The metadata can be used by the clients if needed, and can
     * be presented in the Swagger-UI for convenience.
     */
    private Info info;

    /**
     * Provides information about the the authorization schemes allowed on his API.
     */
    private Map<String, Authorization> authorizations;

    public ResourceListing(String swaggerVersion, List<Resource> apis, String apiVersion, Info info, Map<String, Authorization> authorizations) {
        this.swaggerVersion = swaggerVersion;
        this.apis = apis;
        this.apiVersion = apiVersion;
        this.info = info;
        this.authorizations = authorizations;
    }
}