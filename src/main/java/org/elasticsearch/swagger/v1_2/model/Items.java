package org.elasticsearch.swagger.v1_2.model;

import lombok.Builder;
import lombok.Data;

/**
 * This object is used to describe the value types used inside an array.
 */
@Data
@Builder
public class Items extends SwaggerModel {
    /**
     * The return model of the operation.
     */
    private String type;

    /**
     * Fine-tuned primitive model definition.
     */
    private String format;

    /**
     * The Model to be used.
     */
    private String $ref;
}
