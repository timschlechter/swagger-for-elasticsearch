package net.itimothy.rest.description;

import java.util.Arrays;
import java.util.List;

public class Parameter extends Description {

    public Boolean allowMultiple;
    private ParamType paramType;
    private String name;
    private String description;
    private Boolean required;
    private Object defaultValue;
    private Model model;
    private List<String> enumValues;

    public Parameter(String minVersion, String maxVersion, ParamType paramType, String name, String description, Boolean required, Object defaultValue, Model model, Boolean allowMultiple, List<String> enumValues) {
        super(minVersion, maxVersion);
        this.paramType = paramType;
        this.name = name;
        this.description = description;
        this.required = required;
        this.defaultValue = defaultValue;
        this.model = model;
        this.allowMultiple = allowMultiple;
        this.enumValues = enumValues;
    }

    public static ParameterBuilder builder() {
        return new ParameterBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Parameter)) return false;

        Parameter parameter = (Parameter) o;

        if (enumValues != null ? !enumValues.equals(parameter.enumValues) : parameter.enumValues != null) return false;
        if (allowMultiple != null ? !allowMultiple.equals(parameter.allowMultiple) : parameter.allowMultiple != null)
            return false;
        if (defaultValue != null ? !defaultValue.equals(parameter.defaultValue) : parameter.defaultValue != null)
            return false;
        if (description != null ? !description.equals(parameter.description) : parameter.description != null)
            return false;
        if (model != null ? !model.equals(parameter.model) : parameter.model != null) return false;
        if (name != null ? !name.equals(parameter.name) : parameter.name != null) return false;
        if (paramType != parameter.paramType) return false;
        if (required != null ? !required.equals(parameter.required) : parameter.required != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = allowMultiple != null ? allowMultiple.hashCode() : 0;
        result = 31 * result + (paramType != null ? paramType.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (required != null ? required.hashCode() : 0);
        result = 31 * result + (defaultValue != null ? defaultValue.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (enumValues != null ? enumValues.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Parameter{" +
            "allowMultiple=" + allowMultiple +
            ", paramType=" + paramType +
            ", name='" + name + '\'' +
            ", routes='" + description + '\'' +
            ", required=" + required +
            ", defaultValue=" + defaultValue +
            ", model=" + model +
            ", enumValues=" + enumValues +
            '}';
    }

    public Boolean getAllowMultiple() {
        return this.allowMultiple;
    }

    public void setAllowMultiple(final Boolean allowMultiple) {
        this.allowMultiple = allowMultiple;
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

    public ParamType getParamType() {
        return this.paramType;
    }

    public void setParamType(final ParamType paramType) {
        this.paramType = paramType;
    }

    public Boolean getRequired() {
        return this.required;
    }

    public void setRequired(final Boolean required) {
        this.required = required;
    }

    public List<String> getEnumValues() {
        return this.enumValues;
    }

    public void setEnumValues(final List<String> enumValues) {
        this.enumValues = enumValues;
    }

    public static class ParameterBuilder {

        private String minVersion;
        private String maxVersion;
        private ParamType paramType;
        private String name;
        private String description;
        private Boolean required;
        private Object defaultValue;
        private Model model;
        private Boolean allowMultiple;
        private List<String> enumValues;

        ParameterBuilder() {
        }

        public ParameterBuilder enumValues(final List<String> enumValues) {
            this.enumValues = enumValues;
            return this;
        }

        public ParameterBuilder enumValues(final String... enumValues) {
            return enumValues(Arrays.asList(enumValues));
        }

        public ParameterBuilder allowMultiple(final Boolean allowMultiple) {
            this.allowMultiple = allowMultiple;
            return this;
        }

        public Parameter build() {
            return new Parameter(minVersion, maxVersion, paramType, name, description, required, defaultValue, model, allowMultiple, enumValues);
        }

        public ParameterBuilder defaultValue(final Object defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        public ParameterBuilder description(final String description) {
            this.description = description;
            return this;
        }

        public ParameterBuilder maxVersion(final String maxVersion) {
            this.maxVersion = maxVersion;
            return this;
        }

        public ParameterBuilder minVersion(final String minVersion) {
            this.minVersion = minVersion;
            return this;
        }

        public ParameterBuilder model(final Model model) {
            this.model = model;
            return this;
        }

        public ParameterBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public ParameterBuilder paramType(final ParamType paramType) {
            this.paramType = paramType;
            return this;
        }

        public ParameterBuilder required(final Boolean required) {
            this.required = required;
            return this;
        }

        @Override
        public String toString() {
            return "ParameterBuilder{" +
                "minVersion='" + minVersion + '\'' +
                ", maxVersion='" + maxVersion + '\'' +
                ", paramType=" + paramType +
                ", name='" + name + '\'' +
                ", routes='" + description + '\'' +
                ", required=" + required +
                ", defaultValue=" + defaultValue +
                ", model=" + model +
                ", allowMultiple=" + allowMultiple +
                ", enumValues=" + enumValues +
                '}';
        }
    }
}