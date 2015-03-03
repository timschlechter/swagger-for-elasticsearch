package org.elasticsearch.plugin.swagger.v1_2.model.apiDeclaration;

import org.elasticsearch.plugin.swagger.v1_2.model.DataType;

/**
 * A Property Object holds the definition of a new property for a model.
 */
public class ModelProperty extends DataType {

    /**
     * Name of the property
     */
    private String name;

    /**
     * A brief description of this property.
     */
    private String description;

    public ModelProperty(String type, String ref, String name, String description) {
        super(type, ref);
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
            ", description='" + description + '\'' +
            '}';
    }

    public static class ModelPropertyBuilder {

        private String type;
        private String ref;
        private String name;
        private String description;

        ModelPropertyBuilder() {
        }

        public ModelProperty build() {
            return new ModelProperty(type, ref, name, description);
        }

        public ModelPropertyBuilder description(final String description) {
            this.description = description;
            return this;
        }

        public ModelPropertyBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public ModelPropertyBuilder ref(final String ref) {
            this.ref = ref;
            return this;
        }

        @Override
        public String toString() {
            return "ModelPropertyBuilder{" +
                "type='" + type + '\'' +
                ", ref='" + ref + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
        }

        public ModelPropertyBuilder type(final String type) {
            this.type = type;
            return this;
        }
    }
}