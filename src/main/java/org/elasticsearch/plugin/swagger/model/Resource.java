package org.elasticsearch.plugin.swagger.model;

/**
 * The Resource object describes a resource API endpoint in the application.
 */
public class Resource extends SwaggerModel {
    /**
     * A relative path to the API declaration from the path used to retrieve this Resource Listing.
     * This path does not necessarily have to correspond to the URL which actually serves this
     * resource in the API but rather where the resource listing itself is served.
     */
    private String path;

    /**
     * A short description of the resource.
     */
    private String description;

    public Resource(String path, String description) {
        this.path = path;
        this.description = description;
    }
}