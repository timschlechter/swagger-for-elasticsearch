package net.itimothy.elasticsearch.plugin.swagger.v1_2.model.resourceListing;

import net.itimothy.elasticsearch.plugin.swagger.v1_2.model.SwaggerModel;

/**
 * The Resource object describes a resource API endpoint in the application.
 */
public class Resource extends SwaggerModel {

    /**
     * A relative path to the API declaration from the path used to retrieve this Resource Listing.
     * This path does not necessarily have to correspond to the URL which actually serves this
     * resource in the API but rather where the resource listing itself is served.
     */
    private String path;

    /**
     * A short routes of the resource.
     */
    private String description;

    public Resource() {
    }

    public Resource(final String path, final String description) {
        if (path == null) {
            throw new NullPointerException("path");
        }
        this.path = path;
        this.description = description;
    }

    public static ResourceBuilder builder() {
        return new ResourceBuilder();
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Resource;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(final String path) {
        if (path == null) {
            throw new NullPointerException("path");
        }
        this.path = path;
    }

    @Override
    public int hashCode() {
        int result = path != null ? path.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resource)) return false;

        Resource resource = (Resource) o;

        if (description != null ? !description.equals(resource.description) : resource.description != null)
            return false;
        if (path != null ? !path.equals(resource.path) : resource.path != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return "Resource{" +
            "path='" + path + '\'' +
            ", routes='" + description + '\'' +
            '}';
    }

    public static class ResourceBuilder {

        private String path;
        private String description;

        ResourceBuilder() {
        }

        public Resource build() {
            return new Resource(path, description);
        }

        public ResourceBuilder description(final String description) {
            this.description = description;
            return this;
        }

        public ResourceBuilder path(final String path) {
            this.path = path;
            return this;
        }

        @Override
        public String toString() {
            return "ResourceBuilder{" +
                "path='" + path + '\'' +
                ", routes='" + description + '\'' +
                '}';
        }
    }

}