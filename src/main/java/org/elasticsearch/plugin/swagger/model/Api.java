package org.elasticsearch.plugin.swagger.model;

import java.util.List;

public class Api extends SwaggerModel {
    /**
     * The relative path to the operation, from the basePath, which this operation describes.
     */
    private String path;

    /**
     * A short description of the resource.
     */
    private String description;

    /**
     * A list of the API operations available on this path.
     */
    private List<Operation> operations;

    public Api(String path, String description, List<Operation> operations) {
        this.path = path;
        this.description = description;
        this.operations = operations;
    }
}
