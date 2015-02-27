package org.elasticsearch.plugin.swagger.v1_2.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Base class for Swagger data types.
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public abstract class DataType extends SwaggerModel {
    /**
     * The return model of the operation.
     */
    private String type;

    /**
     * The Model to be used.
     */
    @SerializedName("$ref")
    private String ref;

    /**
     * Fine-tuned primitive model definition.
     */
    private String format;

    /**
     * The default value to be used for the field.
     */
    private Object defaultValue;

    /**
     * A fixed list of possible values.
     */
    @SerializedName("enum")
    private List<String> _enum;

    /**
     * The minimum valid value for the model, inclusive.
     */
    private Long minimum;

    /**
     * The maximum valid value for the model, inclusive. If this field is used in conjunction with
     * the defaultValue field, then the default value MUST be lower than or equal to this value.
     */
    private Long maximum;

    /**
     * The model definition of the values in the container.
     */
    private Items items;

    /**
     * A flag to note whether the container allows duplicate values or not. If the value is set to
     * <c>true</c>, then the array acts as a set.
     */
    private Boolean uniqueItems;
    
    public DataType(String type, String ref) {
        this.type = type;
        this.ref = ref;
    }
}
