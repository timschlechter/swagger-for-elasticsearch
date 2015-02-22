package org.elasticsearch.plugin.swagger.model;

/**
 * This object is used to describe the value types used inside an array.
 */
public class Items extends SwaggerModel {
    /**
     * The return type of the operation.
     */
    private String type;

    /**
     * Fine-tuned primitive type definition.
     */
    private String format;

    /**
     * The Model to be used.
     */
    private String $ref;
}
