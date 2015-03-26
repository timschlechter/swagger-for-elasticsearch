package net.itimothy.elasticsearch.routes.model;

import java.util.Arrays;
import java.util.List;

public class Model extends Description {
    private String id;
    private List<Property> properties;
    private String name;
    private String description;

    public Model(String minVersion, String maxVersion, String id, List<Property> properties, String name, String description) {
        super(minVersion, maxVersion);
        this.id = id;
        this.properties = properties;
        this.name = name;
        this.description = description;
    }

    public static ModelBuilder builder() {
        return new ModelBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Model)) return false;

        Model model = (Model) o;

        if (description != null ? !description.equals(model.description) : model.description != null)
            return false;
        if (id != null ? !id.equals(model.id) : model.id != null) return false;
        if (name != null ? !name.equals(model.name) : model.name != null) return false;
        if (properties != null ? !properties.equals(model.properties) : model.properties != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (properties != null ? properties.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Model{" +
            "id='" + id + '\'' +
            ", properties=" + properties +
            ", name='" + name + '\'' +
            ", routes='" + description + '\'' +
            '}';
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<Property> getProperties() {
        return this.properties;
    }

    public void setProperties(final List<Property> properties) {
        this.properties = properties;
    }

    public boolean isPrimitive() {
        return false;
    }

    public static class ModelBuilder {
        private String minVersion;
        private String maxVersion;
        private String id;
        private List<Property> properties;
        private String name;
        private String description;

        ModelBuilder() {
        }

        public Model build() {
            return new Model(minVersion, maxVersion, id, properties, name, description);
        }

        public ModelBuilder description(final String description) {
            this.description = description;
            return this;
        }

        public ModelBuilder id(final String id) {
            this.id = id;
            return this;
        }

        public ModelBuilder maxVersion(final String maxVersion) {
            this.maxVersion = maxVersion;
            return this;
        }

        public ModelBuilder minVersion(final String minVersion) {
            this.minVersion = minVersion;
            return this;
        }

        public ModelBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public ModelBuilder properties(final List<Property> properties) {
            this.properties = properties;
            return this;
        }

        public ModelBuilder properties(final Property... properties) {
            return properties(Arrays.asList(properties));
        }

        @Override
        public String toString() {
            return "ModelBuilder{" +
                "minVersion='" + minVersion + '\'' +
                ", maxVersion='" + maxVersion + '\'' +
                ", id='" + id + '\'' +
                ", properties=" + properties +
                ", name='" + name + '\'' +
                ", routes='" + description + '\'' +
                '}';
        }
    }
}