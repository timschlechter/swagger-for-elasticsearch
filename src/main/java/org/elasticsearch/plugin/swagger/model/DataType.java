package org.elasticsearch.plugin.swagger.model;

import java.util.List;

/**
 * Base class for Swagger data types.
 */
public class DataType extends SwaggerModel {
    /**
     * The return type of the operation.
     */
    private String type;

    /**
     * The Model to be used.
     */
    private String $ref;

    /**
     * Fine-tuned primitive type definition.
     */
    private String format;

    /**
     * The default value to be used for the field.
     */
    private Object defaultValue;

    /**
     * A fixed list of possible values.
     */
    private List<String> _enum;

    /**
     * The minimum valid value for the type, inclusive.
     */
    private Long minimum;

    /**
     * The maximum valid value for the type, inclusive. If this field is used in conjunction with
     * the defaultValue field, then the default value MUST be lower than or equal to this value.
     */
    private Long maximum;

    /**
     * The type definition of the values in the container.
     */
    private Items items;

    /**
     * A flag to note whether the container allows duplicate values or not. If the value is set to
     * <c>true</c>, then the array acts as a set.
     */
    private Boolean uniqueItems;

    public DataType(String type, String $ref) {
        this(type, $ref, null, null, null, null, null, new Items(), null);
    }
    
    public DataType(String type, String $ref, String format, Object defaultValue, List<String> anEnum, Long minimum, Long maximum, Items items, Boolean uniqueItems) {
        this.type = type;
        this.$ref = $ref;
        this.format = format;
        this.defaultValue = defaultValue;
        _enum = anEnum;
        this.minimum = minimum;
        this.maximum = maximum;
        this.items = items;
        this.uniqueItems = uniqueItems;
    }
}
