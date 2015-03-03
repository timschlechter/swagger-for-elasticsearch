package org.elasticsearch.plugin.swagger.v1_2.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Base class for Swagger data types.
 */
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
    private List<String> enumValues;

    /**
     * The minimum valid value for the model, inclusive.
     */
    private Long minimum;

    /**
     * The maximum valid value for the model, inclusive. If this field is used in conjunction with
     * <p/>
     * the defaultValue field, then the default value MUST be lower than or equal to this value.
     */
    private Long maximum;

    /**
     * The model definition of the values in the container.
     */
    private Items items;

    /**
     * A flag to note whether the container allows duplicate values or not. If the value is set to
     * <p/>
     * <c>true</c>, then the array acts as a set.
     */
    private Boolean uniqueItems;

    public DataType(String type, String ref) {
        this.type = type;
        this.ref = ref;
    }

    public DataType() {
    }

    public DataType(final String type, final String ref, final String format, final Object defaultValue, final List<String> enumValues, final Long minimum, final Long maximum, final Items items, final Boolean uniqueItems) {
        this.type = type;
        this.ref = ref;
        this.format = format;
        this.defaultValue = defaultValue;
        this.enumValues = enumValues;
        this.minimum = minimum;
        this.maximum = maximum;
        this.items = items;
        this.uniqueItems = uniqueItems;
    }

    public Object getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(final Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(final String format) {
        this.format = format;
    }

    public Items getItems() {
        return this.items;
    }

    public void setItems(final Items items) {
        this.items = items;
    }

    public Long getMaximum() {
        return this.maximum;
    }

    public void setMaximum(final Long maximum) {
        this.maximum = maximum;
    }

    public Long getMinimum() {
        return this.minimum;
    }

    public void setMinimum(final Long minimum) {
        this.minimum = minimum;
    }

    public String getRef() {
        return this.ref;
    }

    public void setRef(final String ref) {
        this.ref = ref;
    }

    public String getType() {
        return this.type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public Boolean getUniqueItems() {
        return this.uniqueItems;
    }

    public void setUniqueItems(final Boolean uniqueItems) {
        this.uniqueItems = uniqueItems;
    }

    public List<String> getEnumValues() {
        return this.enumValues;
    }

    public void setEnumValues(final List<String> enumValues) {
        this.enumValues = enumValues;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (ref != null ? ref.hashCode() : 0);
        result = 31 * result + (format != null ? format.hashCode() : 0);
        result = 31 * result + (defaultValue != null ? defaultValue.hashCode() : 0);
        result = 31 * result + (enumValues != null ? enumValues.hashCode() : 0);
        result = 31 * result + (minimum != null ? minimum.hashCode() : 0);
        result = 31 * result + (maximum != null ? maximum.hashCode() : 0);
        result = 31 * result + (items != null ? items.hashCode() : 0);
        result = 31 * result + (uniqueItems != null ? uniqueItems.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataType)) return false;

        DataType dataType = (DataType) o;

        if (enumValues != null ? !enumValues.equals(dataType.enumValues) : dataType.enumValues != null) return false;
        if (defaultValue != null ? !defaultValue.equals(dataType.defaultValue) : dataType.defaultValue != null)
            return false;
        if (format != null ? !format.equals(dataType.format) : dataType.format != null)
            return false;
        if (items != null ? !items.equals(dataType.items) : dataType.items != null) return false;
        if (maximum != null ? !maximum.equals(dataType.maximum) : dataType.maximum != null)
            return false;
        if (minimum != null ? !minimum.equals(dataType.minimum) : dataType.minimum != null)
            return false;
        if (ref != null ? !ref.equals(dataType.ref) : dataType.ref != null) return false;
        if (type != null ? !type.equals(dataType.type) : dataType.type != null) return false;
        if (uniqueItems != null ? !uniqueItems.equals(dataType.uniqueItems) : dataType.uniqueItems != null)
            return false;

        return true;
    }

    @Override
    public String toString() {
        return "DataType{" +
            "type='" + type + '\'' +
            ", ref='" + ref + '\'' +
            ", format='" + format + '\'' +
            ", defaultValue=" + defaultValue +
            ", enumValues=" + enumValues +
            ", minimum=" + minimum +
            ", maximum=" + maximum +
            ", items=" + items +
            ", uniqueItems=" + uniqueItems +
            '}';
    }
}