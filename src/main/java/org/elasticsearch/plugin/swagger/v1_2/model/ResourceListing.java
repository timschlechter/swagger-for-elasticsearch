package org.elasticsearch.plugin.swagger.v1_2.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Singular;

import java.util.ArrayList;
import java.util.List;

/**
 * The Resource Listing serves as the root document for the API description. It contains general
 * information about the API and an inventory of the available resources.
 */
@Data
@Builder
public class ResourceListing extends SwaggerModel {
    /**
     * Specifies the Swagger Specification version being used. It can be used by the Swagger UI and
     * other clients to interpret the API listing.
     */
    @NonNull
    private String swaggerVersion;

    /**
     * Lists the resources to be described by this specification implementation.
     */
    @NonNull
    @Singular("api")
    private List<Resource> apis = new ArrayList<>();

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
    private List<Authorization> authorizations = new ArrayList<>();
}