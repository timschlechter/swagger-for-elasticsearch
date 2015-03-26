package net.itimothy.elasticsearch.routes.model;

import java.util.Arrays;
import java.util.List;

public class Route extends Description {
    private String apiPath;
    private HttpMethod method;
    private String group;
    private Model model;
    private String name;
    private List<Parameter> parameters;
    private String description;
    private String notes;
    private List<Response> responses;
    private List<String> produces;
    private List<String> consumes;

    public Route(String minVersion, String maxVersion, String apiPath, HttpMethod method, String group, Model model, String name, List<Parameter> parameters, String description, String notes, List<Response> responses, List<String> produces, List<String> consumes) {
        super(minVersion, maxVersion);
        this.apiPath = apiPath;
        this.method = method;
        this.group = group;
        this.model = model;
        this.name = name;
        this.parameters = parameters;
        this.description = description;
        this.notes = notes;
        this.responses = responses;
        this.produces = produces;
        this.consumes = consumes;
    }

    public static RouteBuilder builder() {
        return new RouteBuilder();
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Route;
    }

    public String getCategory() {
        String[] parts = group.split(": ");
        return parts.length > 1 ? parts[0] : "misc";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Route)) return false;

        Route route = (Route) o;

        if (apiPath != null ? !apiPath.equals(route.apiPath) : route.apiPath != null) return false;
        if (consumes != null ? !consumes.equals(route.consumes) : route.consumes != null)
            return false;
        if (description != null ? !description.equals(route.description) : route.description != null)
            return false;
        if (group != null ? !group.equals(route.group) : route.group != null) return false;
        if (method != route.method) return false;
        if (model != null ? !model.equals(route.model) : route.model != null) return false;
        if (name != null ? !name.equals(route.name) : route.name != null) return false;
        if (notes != null ? !notes.equals(route.notes) : route.notes != null) return false;
        if (parameters != null ? !parameters.equals(route.parameters) : route.parameters != null)
            return false;
        if (produces != null ? !produces.equals(route.produces) : route.produces != null)
            return false;
        if (responses != null ? !responses.equals(route.responses) : route.responses != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = apiPath != null ? apiPath.hashCode() : 0;
        result = 31 * result + (method != null ? method.hashCode() : 0);
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (parameters != null ? parameters.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        result = 31 * result + (responses != null ? responses.hashCode() : 0);
        result = 31 * result + (produces != null ? produces.hashCode() : 0);
        result = 31 * result + (consumes != null ? consumes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Route{" +
            "apiPath='" + apiPath + '\'' +
            ", method=" + method +
            ", group='" + group + '\'' +
            ", model=" + model +
            ", name='" + name + '\'' +
            ", parameters=" + parameters +
            ", routes='" + description + '\'' +
            ", notes='" + notes + '\'' +
            ", responses=" + responses +
            ", produces=" + produces +
            ", consumes=" + consumes +
            '}';
    }

    public String getApiPath() {
        return this.apiPath;
    }

    public void setApiPath(final String apiPath) {
        this.apiPath = apiPath;
    }

    public List<String> getConsumes() {
        return this.consumes;
    }

    public void setConsumes(final List<String> consumes) {
        this.consumes = consumes;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getGroup() {
        return this.group;
    }

    public void setGroup(final String group) {
        this.group = group;
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public void setMethod(final HttpMethod method) {
        this.method = method;
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

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(final String notes) {
        this.notes = notes;
    }

    public List<Parameter> getParameters() {
        return this.parameters;
    }

    public void setParameters(final List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public List<String> getProduces() {
        return this.produces;
    }

    public void setProduces(final List<String> produces) {
        this.produces = produces;
    }

    public List<Response> getResponses() {
        return this.responses;
    }

    public void setResponses(final List<Response> responses) {
        this.responses = responses;
    }

    public static class RouteBuilder {

        private String minVersion;
        private String maxVersion;
        private String apiPath;
        private HttpMethod method;
        private String group;
        private Model model;
        private String name;
        private List<Parameter> parameters;
        private String description;
        private String notes;
        private List<Response> responses;
        private List<String> produces;
        private List<String> consumes;

        RouteBuilder() {
        }

        public RouteBuilder apiPath(final String apiPath) {
            this.apiPath = apiPath;
            return this;
        }

        public Route build() {
            return new Route(minVersion, maxVersion, apiPath, method, group, model, name, parameters, description, notes, responses, produces, consumes);
        }

        public RouteBuilder consumes(final List<String> consumes) {
            this.consumes = consumes;
            return this;
        }

        public RouteBuilder description(final String description) {
            this.description = description;
            return this;
        }

        public RouteBuilder group(final String group) {
            this.group = group;
            return this;
        }

        public RouteBuilder maxVersion(final String maxVersion) {
            this.maxVersion = maxVersion;
            return this;
        }

        public RouteBuilder method(final HttpMethod method) {
            this.method = method;
            return this;
        }

        public RouteBuilder minVersion(final String minVersion) {
            this.minVersion = minVersion;
            return this;
        }

        public RouteBuilder model(final Model model) {
            this.model = model;
            return this;
        }

        public RouteBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public RouteBuilder notes(final String notes) {
            this.notes = notes;
            return this;
        }

        public RouteBuilder parameters(final List<Parameter> parameters) {
            this.parameters = parameters;
            return this;
        }

        public RouteBuilder parameters(Parameter... parameters) {
            return parameters(Arrays.asList(parameters));
        }

        public RouteBuilder produces(final List<String> produces) {
            this.produces = produces;
            return this;
        }

        public RouteBuilder responses(final List<Response> responses) {
            this.responses = responses;
            return this;
        }

        @Override
        public String toString() {
            return "RouteBuilder{" +
                "minVersion='" + minVersion + '\'' +
                ", maxVersion='" + maxVersion + '\'' +
                ", apiPath='" + apiPath + '\'' +
                ", method=" + method +
                ", group='" + group + '\'' +
                ", model=" + model +
                ", name='" + name + '\'' +
                ", parameters=" + parameters +
                ", routes='" + description + '\'' +
                ", notes='" + notes + '\'' +
                ", responses=" + responses +
                ", produces=" + produces +
                ", consumes=" + consumes +
                '}';
        }
    }
}