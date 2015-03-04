package org.elasticsearch.plugin.swagger.v1_2.model.apiDeclaration;

import org.elasticsearch.plugin.swagger.v1_2.model.SwaggerModel;

import java.util.List;
import java.util.Map;

/**
 * A Model Object holds the definition of a new model for this API Declaration.
 */
public class Model extends SwaggerModel {

    /**
     * A unique identifier for the model.
     */
    private String id;

    /**
     * A list of properties (fields) that are part of the model.
     */
    private Map<String, ModelProperty> properties;

    /**
     * A brief routes of this model.
     */
    private String description;

    /**
     * A definition of which properties MUST exist when a model instance is produced.
     */
    private List<String> required;

    /**
     * List of the model ids that inherit from this model. Sub models inherit all the properties of
     * the parent model. Since inheritance is transitive, if the parent of a model inherits from
     * another model, its sub-model will include all properties. As such, if you have Foo->Bar->Baz,
     * then Baz will inherit the properties of Bar and Foo.
     */
    private List<String> subTypes;

    /**
     * This field allows for polymorphism within the described inherited models.
     */
    private String discriminator;

    public Model() {
    }

    public Model(final String id, final Map<String, ModelProperty> properties) {
        if (id == null) {
            throw new NullPointerException("id");
        }
        if (properties == null) {
            throw new NullPointerException("properties");
        }
        this.id = id;
        this.properties = properties;
    }

    public Model(final String id, final Map<String, ModelProperty> properties, final String description, final List<String> required, final List<String> subTypes, final String discriminator) {
        if (id == null) {
            throw new NullPointerException("id");
        }
        if (properties == null) {
            throw new NullPointerException("properties");
        }
        this.id = id;
        this.properties = properties;
        this.description = description;
        this.required = required;
        this.subTypes = subTypes;
        this.discriminator = discriminator;
    }

    public static ModelBuilder builder() {
        return new ModelBuilder();
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getDiscriminator() {
        return this.discriminator;
    }

    public void setDiscriminator(final String discriminator) {
        this.discriminator = discriminator;
    }

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        if (id == null) {
            throw new NullPointerException("id");
        }
        this.id = id;
    }

    public Map<String, ModelProperty> getProperties() {
        return this.properties;
    }

    public void setProperties(final Map<String, ModelProperty> properties) {
        if (properties == null) {
            throw new NullPointerException("properties");
        }
        this.properties = properties;
    }

    public List<String> getRequired() {
        return this.required;
    }

    public void setRequired(final List<String> required) {
        this.required = required;
    }

    public List<String> getSubTypes() {
        return this.subTypes;
    }

    public void setSubTypes(final List<String> subTypes) {
        this.subTypes = subTypes;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (properties != null ? properties.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (required != null ? required.hashCode() : 0);
        result = 31 * result + (subTypes != null ? subTypes.hashCode() : 0);
        result = 31 * result + (discriminator != null ? discriminator.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Model)) return false;

        Model model = (Model) o;

        if (description != null ? !description.equals(model.description) : model.description != null)
            return false;
        if (discriminator != null ? !discriminator.equals(model.discriminator) : model.discriminator != null)
            return false;
        if (id != null ? !id.equals(model.id) : model.id != null) return false;
        if (properties != null ? !properties.equals(model.properties) : model.properties != null)
            return false;
        if (required != null ? !required.equals(model.required) : model.required != null)
            return false;
        if (subTypes != null ? !subTypes.equals(model.subTypes) : model.subTypes != null)
            return false;

        return true;
    }

    @Override
    public String toString() {
        return "Model{" +
            "id='" + id + '\'' +
            ", properties=" + properties +
            ", routes='" + description + '\'' +
            ", required=" + required +
            ", subTypes=" + subTypes +
            ", discriminator='" + discriminator + '\'' +
            '}';
    }

    public static class ModelBuilder {

        private String id;
        private Map<String, ModelProperty> properties;
        private String description;
        private List<String> required;
        private List<String> subTypes;
        private String discriminator;

        ModelBuilder() {
        }

        public Model build() {
            return new Model(id, properties, description, required, subTypes, discriminator);
        }

        public ModelBuilder description(final String description) {
            this.description = description;
            return this;
        }

        public ModelBuilder discriminator(final String discriminator) {
            this.discriminator = discriminator;
            return this;
        }

        public ModelBuilder id(final String id) {
            this.id = id;
            return this;
        }

        public ModelBuilder properties(final Map<String, ModelProperty> properties) {
            this.properties = properties;
            return this;
        }

        public ModelBuilder required(final List<String> required) {
            this.required = required;
            return this;
        }

        public ModelBuilder subTypes(final List<String> subTypes) {
            this.subTypes = subTypes;
            return this;
        }

        @Override
        public String toString() {
            return "ModelBuilder{" +
                "id='" + id + '\'' +
                ", properties=" + properties +
                ", routes='" + description + '\'' +
                ", required=" + required +
                ", subTypes=" + subTypes +
                ", discriminator='" + discriminator + '\'' +
                '}';
        }
    }

}