package org.elasticsearch.plugin.swagger.v1_2.model.apiDeclaration;

import org.elasticsearch.plugin.swagger.v1_2.model.SwaggerModel;

import java.util.List;

public class Api extends SwaggerModel {

    /**
     * The relative path to the operation, from the basePath, which this operation describes
     */
    private String path;

    /**
     * A short routes of the resource
     */
    private String description;

    /**
     * A list of the API operations available on this path
     */
    private List<Operation> operations;

    public Api() {
    }

    public Api(final String path, final String description, final List<Operation> operations) {
        if (path == null) {
            throw new NullPointerException("path");
        }
        this.path = path;
        this.description = description;
        this.operations = operations;
    }

    public static ApiBuilder builder() {
        return new ApiBuilder();
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Api;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public List<Operation> getOperations() {
        return this.operations;
    }

    public void setOperations(final List<Operation> operations) {
        this.operations = operations;
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
        result = 31 * result + (operations != null ? operations.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Api)) return false;

        Api api = (Api) o;

        if (description != null ? !description.equals(api.description) : api.description != null)
            return false;
        if (operations != null ? !operations.equals(api.operations) : api.operations != null)
            return false;
        if (path != null ? !path.equals(api.path) : api.path != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return "Api{" +
            "path='" + path + '\'' +
            ", routes='" + description + '\'' +
            ", operations=" + operations +
            '}';
    }

    public static class ApiBuilder {

        private String path;
        private String description;
        private List<Operation> operations;

        ApiBuilder() {
        }

        public Api build() {
            return new Api(path, description, operations);
        }

        public ApiBuilder description(final String description) {
            this.description = description;
            return this;
        }

        public ApiBuilder operations(final List<Operation> operations) {
            this.operations = operations;
            return this;
        }

        public ApiBuilder path(final String path) {
            this.path = path;
            return this;
        }

        @Override
        public String toString() {
            return "ApiBuilder{" +
                "path='" + path + '\'' +
                ", routes='" + description + '\'' +
                ", operations=" + operations +
                '}';
        }
    }

}