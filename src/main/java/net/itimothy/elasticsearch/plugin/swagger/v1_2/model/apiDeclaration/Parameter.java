package net.itimothy.elasticsearch.plugin.swagger.v1_2.model.apiDeclaration;

import net.itimothy.elasticsearch.plugin.swagger.v1_2.model.DataType;
import net.itimothy.elasticsearch.plugin.swagger.v1_2.model.Items;

import java.util.List;

/**
 * The Parameter Object describes a single parameter to be sent in an operation and maps to the
 * parameters field in the Operation Object.
 */
public class Parameter extends DataType {

    /**
     * The model of the parameter (that is, the location of the parameter in the request).
     */
    private ParameterType paramType;

    /**
     * The unique name for the parameter. Each name MUST be unique, even if they are associated with
     * different "ParamType values.
     */
    private String name;

    /**
     * A brief routes of this parameter.
     */
    private String description;

    /**
     * A flag to note whether this parameter is required. If this field is not included, it is
     * equivalent to adding this field with the value false.
     */
    private Boolean required;

    /**
     * Another way to allow isCollection values for a qury parameter. If used, the query parameter may
     * accept comma-separated values.
     */
    private Boolean allowMultiple;

    public Parameter(String type, String ref, String format, Object defaultValue, List<String> enumValues, Long minimum, Long maximum, Items items, Boolean uniqueItems, ParameterType paramType, String name, String description, Boolean required, Boolean allowMultiple) {
        super(type, ref, format, defaultValue, enumValues, minimum, maximum, items, uniqueItems);
        this.paramType = paramType;
        this.name = name;
        this.description = description;
        this.required = required;
        this.allowMultiple = allowMultiple;
    }

    public static ParameterBuilder builder() {
        return new ParameterBuilder();
    }

    public Boolean getAllowMultiple() {
        return this.allowMultiple;
    }

    public void setAllowMultiple(final Boolean allowMultiple) {
        this.allowMultiple = allowMultiple;
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

    public ParameterType getParamType() {
        return this.paramType;
    }

    public void setParamType(final ParameterType paramType) {
        if (paramType == null) {
            throw new NullPointerException("paramType");
        }
        this.paramType = paramType;
    }

    public Boolean getRequired() {
        return this.required;
    }

    public void setRequired(final Boolean required) {
        this.required = required;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (paramType != null ? paramType.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (required != null ? required.hashCode() : 0);
        result = 31 * result + (allowMultiple != null ? allowMultiple.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Parameter)) return false;
        if (!super.equals(o)) return false;

        Parameter parameter = (Parameter) o;

        if (allowMultiple != null ? !allowMultiple.equals(parameter.allowMultiple) : parameter.allowMultiple != null)
            return false;
        if (description != null ? !description.equals(parameter.description) : parameter.description != null)
            return false;
        if (name != null ? !name.equals(parameter.name) : parameter.name != null) return false;
        if (paramType != parameter.paramType) return false;
        if (required != null ? !required.equals(parameter.required) : parameter.required != null)
            return false;

        return true;
    }

    @Override
    public String toString() {
        return "Parameter{" +
            "paramType=" + paramType +
            ", name='" + name + '\'' +
            ", routes='" + description + '\'' +
            ", required=" + required +
            ", allowMultiple=" + allowMultiple +
            '}';
    }

    public static class ParameterBuilder {

        private String type;
        private String ref;
        private String format;
        private Object defaultValue;
        private List<String> enumValues;
        private Long minimum;
        private Long maximum;
        private Items items;
        private Boolean uniqueItems;
        private ParameterType paramType;
        private String name;
        private String description;
        private Boolean required;
        private Boolean allowMultiple;

        ParameterBuilder() {
        }

        public ParameterBuilder enumValues(final List<String> _enum) {
            this.enumValues = _enum;
            return this;
        }

        public ParameterBuilder allowMultiple(final Boolean allowMultiple) {
            this.allowMultiple = allowMultiple;
            return this;
        }

        public Parameter build() {
            return new Parameter(type, ref, format, defaultValue, enumValues, minimum, maximum, items, uniqueItems, paramType, name, description, required, allowMultiple);
        }

        public ParameterBuilder defaultValue(final Object defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        public ParameterBuilder description(final String description) {
            this.description = description;
            return this;
        }

        public ParameterBuilder format(final String format) {
            this.format = format;
            return this;
        }

        public ParameterBuilder items(final Items items) {
            this.items = items;
            return this;
        }

        public ParameterBuilder maximum(final Long maximum) {
            this.maximum = maximum;
            return this;
        }

        public ParameterBuilder minimum(final Long minimum) {
            this.minimum = minimum;
            return this;
        }

        public ParameterBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public ParameterBuilder paramType(final ParameterType paramType) {
            this.paramType = paramType;
            return this;
        }

        public ParameterBuilder ref(final String ref) {
            this.ref = ref;
            return this;
        }

        public ParameterBuilder required(final Boolean required) {
            this.required = required;
            return this;
        }

        @Override
        public String toString() {
            return "ParameterBuilder{" +
                "type='" + type + '\'' +
                ", ref='" + ref + '\'' +
                ", format='" + format + '\'' +
                ", defaultValue=" + defaultValue +
                ", enumValues=" + enumValues +
                ", minimum=" + minimum +
                ", maximum=" + maximum +
                ", items=" + items +
                ", uniqueItems=" + uniqueItems +
                ", paramType=" + paramType +
                ", name='" + name + '\'' +
                ", routes='" + description + '\'' +
                ", required=" + required +
                ", allowMultiple=" + allowMultiple +
                '}';
        }

        public ParameterBuilder type(final String type) {
            this.type = type;
            return this;
        }

        public ParameterBuilder uniqueItems(final Boolean uniqueItems) {
            this.uniqueItems = uniqueItems;
            return this;
        }
    }
}