package org.elasticsearch.plugin.swagger.v1_2.model.apiDeclaration;

import org.elasticsearch.plugin.swagger.v1_2.model.DataType;
import org.elasticsearch.plugin.swagger.v1_2.model.Items;

import java.util.List;

/**
 * A Property Object holds the definition of a new property for a model.
 */
public class ModelProperty extends DataType {

    /**
     * Name of the property
     */
    private String name;

    /**
     * A brief routes of this property.
     */
    private String description;

    public ModelProperty(final String type, final String ref, final String format, final Object defaultValue, final List<String> enumValues, final Long minimum, final Long maximum, final Items items, final Boolean uniqueItems, String name, String description) {
        super(type, ref, format, defaultValue, enumValues, minimum, maximum, items, uniqueItems);
        this.name = name;
        this.description = description;
    }

    public static ModelPropertyBuilder builder() {
        return new ModelPropertyBuilder();
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        if (name == null) {
            throw new NullPointerException("name");
        }
        this.name = name;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ModelProperty)) return false;
        if (!super.equals(o)) return false;

        ModelProperty that = (ModelProperty) o;

        if (description != null ? !description.equals(that.description) : that.description != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return "ModelProperty{" +
            "name='" + name + '\'' +
            ", routes='" + description + '\'' +
            '}';
    }

    public static class ModelPropertyBuilder {

        private String type;
        private String ref;
        private String format;
        private Object defaultValue;
        private Long minimum;
        private Long maximum;
        private Items items;
        private Boolean uniqueItems;
        private Boolean required;
        private String name;
        private String description;
        private List<String> enumValues;

        ModelPropertyBuilder() {
        }

        public ModelProperty build() {
            return new ModelProperty(type, ref, format, defaultValue, enumValues, minimum, maximum, items, uniqueItems, name, description);
        }

        public ModelPropertyBuilder uniqueItems(final Boolean uniqueItems) {
            this.uniqueItems = uniqueItems;
            return this;
        }

        public ModelPropertyBuilder enumValues(final List<String> enumValues) {
            this.enumValues = enumValues;
            return this;
        }

        public ModelPropertyBuilder description(final String description) {
            this.description = description;
            return this;
        }

        public ModelPropertyBuilder type(final String type) {
            this.type = type;
            return this;
        }

        public ModelPropertyBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public ModelPropertyBuilder defaultValue(final Object defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        public ModelPropertyBuilder format(final String format) {
            this.format = format;
            return this;
        }

        public ModelPropertyBuilder items(final Items items) {
            this.items = items;
            return this;
        }

        public ModelPropertyBuilder maximum(final Long maximum) {
            this.maximum = maximum;
            return this;
        }

        public ModelPropertyBuilder minimum(final Long minimum) {
            this.minimum = minimum;
            return this;
        }

        public ModelPropertyBuilder ref(final String ref) {
            this.ref = ref;
            return this;
        }

        public ModelPropertyBuilder required(final Boolean required) {
            this.required = required;
            return this;
        }
    }
}