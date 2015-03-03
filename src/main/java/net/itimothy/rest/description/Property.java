package net.itimothy.rest.description;

import java.util.List;

public class Property extends Description {
    private Object defaultValue;
    private String description;
    private List<String> enumValues;
    private Long maximum;
    private Long minimum;
    private String name;
    private Boolean required;
    private Model model;
    private Boolean uniqueItems;

    public Property(String minVersion, String maxVersion, Object defaultValue, String description, List<String> enumValues, Long maximum, Long minimum, String name, Boolean required, Model model, Boolean uniqueItems) {
        super(minVersion, maxVersion);
        this.defaultValue = defaultValue;
        this.description = description;
        this.enumValues = enumValues;
        this.maximum = maximum;
        this.minimum = minimum;
        this.name = name;
        this.required = required;
        this.model = model;
        this.uniqueItems = uniqueItems;
    }

    public static PropertyBuilder builder() {
        return new PropertyBuilder();
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Property;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Property)) return false;

        Property property = (Property) o;

        if (defaultValue != null ? !defaultValue.equals(property.defaultValue) : property.defaultValue != null)
            return false;
        if (description != null ? !description.equals(property.description) : property.description != null)
            return false;
        if (enumValues != null ? !enumValues.equals(property.enumValues) : property.enumValues != null)
            return false;
        if (maximum != null ? !maximum.equals(property.maximum) : property.maximum != null)
            return false;
        if (minimum != null ? !minimum.equals(property.minimum) : property.minimum != null)
            return false;
        if (model != null ? !model.equals(property.model) : property.model != null) return false;
        if (name != null ? !name.equals(property.name) : property.name != null) return false;
        if (required != null ? !required.equals(property.required) : property.required != null)
            return false;
        if (uniqueItems != null ? !uniqueItems.equals(property.uniqueItems) : property.uniqueItems != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = defaultValue != null ? defaultValue.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (enumValues != null ? enumValues.hashCode() : 0);
        result = 31 * result + (maximum != null ? maximum.hashCode() : 0);
        result = 31 * result + (minimum != null ? minimum.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (required != null ? required.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (uniqueItems != null ? uniqueItems.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Property{" +
            "defaultValue=" + defaultValue +
            ", description='" + description + '\'' +
            ", enumValues=" + enumValues +
            ", maximum=" + maximum +
            ", minimum=" + minimum +
            ", name='" + name + '\'' +
            ", required=" + required +
            ", model=" + model +
            ", uniqueItems=" + uniqueItems +
            '}';
    }

    public Object getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(final Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public List<String> getEnumValues() {
        return this.enumValues;
    }

    public void setEnumValues(final List<String> enumValues) {
        this.enumValues = enumValues;
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

    public Model getModel() {
        return this.model;
    }

    public void setModel(final Model model) {
        this.model = model;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Boolean getRequired() {
        return this.required;
    }

    public void setRequired(final Boolean required) {
        this.required = required;
    }

    public Boolean getUniqueItems() {
        return this.uniqueItems;
    }

    public void setUniqueItems(final Boolean uniqueItems) {
        this.uniqueItems = uniqueItems;
    }

    public static class PropertyBuilder {

        private String minVersion;
        private String maxVersion;
        private Object defaultValue;
        private String description;
        private List<String> enumValues;
        private Long maximum;
        private Long minimum;
        private String name;
        private Boolean required;
        private Model model;
        private Boolean uniqueItems;

        PropertyBuilder() {
        }

        public Property build() {
            return new Property(minVersion, maxVersion, defaultValue, description, enumValues, maximum, minimum, name, required, model, uniqueItems);
        }

        public PropertyBuilder defaultValue(final Object defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        public PropertyBuilder description(final String description) {
            this.description = description;
            return this;
        }

        public PropertyBuilder enumValues(final List<String> enumValues) {
            this.enumValues = enumValues;
            return this;
        }

        public PropertyBuilder maxVersion(final String maxVersion) {
            this.maxVersion = maxVersion;
            return this;
        }

        public PropertyBuilder maximum(final Long maximum) {
            this.maximum = maximum;
            return this;
        }

        public PropertyBuilder minVersion(final String minVersion) {
            this.minVersion = minVersion;
            return this;
        }

        public PropertyBuilder minimum(final Long minimum) {
            this.minimum = minimum;
            return this;
        }

        public PropertyBuilder model(final Model model) {
            this.model = model;
            return this;
        }

        public PropertyBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public PropertyBuilder required(final Boolean required) {
            this.required = required;
            return this;
        }

        @Override
        public String toString() {
            return "PropertyBuilder{" +
                "minVersion='" + minVersion + '\'' +
                ", maxVersion='" + maxVersion + '\'' +
                ", defaultValue=" + defaultValue +
                ", description='" + description + '\'' +
                ", enumValues=" + enumValues +
                ", maximum=" + maximum +
                ", minimum=" + minimum +
                ", name='" + name + '\'' +
                ", required=" + required +
                ", model=" + model +
                ", uniqueItems=" + uniqueItems +
                '}';
        }

        public PropertyBuilder uniqueItems(final Boolean uniqueItems) {
            this.uniqueItems = uniqueItems;
            return this;
        }
    }
}