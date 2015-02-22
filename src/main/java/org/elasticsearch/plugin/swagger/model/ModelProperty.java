package org.elasticsearch.plugin.swagger.model;

/**
 * A Property Object holds the definition of a new property for a model.
 */
public class ModelProperty extends DataType {
    /**
     * A brief description of this property.
     */
    public String description;

    public ModelProperty(String type) {
        super(type, null);
    }
}