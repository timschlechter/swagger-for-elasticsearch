package org.elasticsearch.plugin.swagger.v1_2.model;

import lombok.*;

/**
 * A Property Object holds the definition of a new property for a model.
 */
@Data
public class ModelProperty extends DataType {
    /**
     * Name of the property
     */
    @NonNull
    private String name;
    
    /**
     * A brief description of this property.
     */
    private String description;

    @Builder
    public ModelProperty(String type, String ref, String name, String description) {
        super(type, ref);
        this.name = name;
        this.description = description;
    }
}