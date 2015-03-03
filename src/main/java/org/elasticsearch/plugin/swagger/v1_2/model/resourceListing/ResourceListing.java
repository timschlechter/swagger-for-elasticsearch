package org.elasticsearch.plugin.swagger.v1_2.model.resourceListing;

import org.elasticsearch.plugin.swagger.v1_2.model.SwaggerModel;

import java.util.ArrayList;
import java.util.List;

/**
 * The Resource Listing serves as the root document for the API description. It contains general
 * information about the API and an inventory of the available resources.
 */
public class ResourceListing extends SwaggerModel {

    /**
     * Specifies the Swagger Specification version being used. It can be used by the Swagger UI and
     * other clients to interpret the API listing.
     */
    private String swaggerVersion;

    /**
     * Lists the resources to be described by this specification implementation.
     */
    private List<Resource> apis = new ArrayList<>();

    /**
     * Provides the version of the application API.
     */
    private String apiVersion;

    /**
     * Provides metadata about the API. The metadata can be used by the clients if needed, and can
     * be presented in the Swagger-UI for convenience.
     */
    private Info info;

    /**
     * Provides information about the the authorization schemes allowed on his API.
     */
    private List<Authorization> authorizations = new ArrayList<>();

    ResourceListing(final String swaggerVersion, final List<Resource> apis, final String apiVersion, final Info info, final List<Authorization> authorizations) {
        if (swaggerVersion == null) {
            throw new NullPointerException("swaggerVersion");
        }
        if (apis == null) {
            throw new NullPointerException("apis");
        }
        this.swaggerVersion = swaggerVersion;
        this.apis = apis;
        this.apiVersion = apiVersion;
        this.info = info;
        this.authorizations = authorizations;
    }

    public static ResourceListingBuilder builder() {
        return new ResourceListingBuilder();
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ResourceListing;
    }

    public String getApiVersion() {
        return this.apiVersion;
    }

    public void setApiVersion(final String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public List<Resource> getApis() {
        return this.apis;
    }

    public void setApis(final List<Resource> apis) {
        if (apis == null) {
            throw new NullPointerException("apis");
        }
        this.apis = apis;
    }

    public List<Authorization> getAuthorizations() {
        return this.authorizations;
    }

    public void setAuthorizations(final List<Authorization> authorizations) {
        this.authorizations = authorizations;
    }

    public Info getInfo() {
        return this.info;
    }

    public void setInfo(final Info info) {
        this.info = info;
    }

    public String getSwaggerVersion() {
        return this.swaggerVersion;
    }

    public void setSwaggerVersion(final String swaggerVersion) {
        if (swaggerVersion == null) {
            throw new NullPointerException("swaggerVersion");
        }
        this.swaggerVersion = swaggerVersion;
    }

    @Override
    public int hashCode() {
        int result = swaggerVersion != null ? swaggerVersion.hashCode() : 0;
        result = 31 * result + (apis != null ? apis.hashCode() : 0);
        result = 31 * result + (apiVersion != null ? apiVersion.hashCode() : 0);
        result = 31 * result + (info != null ? info.hashCode() : 0);
        result = 31 * result + (authorizations != null ? authorizations.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResourceListing)) return false;

        ResourceListing that = (ResourceListing) o;

        if (apiVersion != null ? !apiVersion.equals(that.apiVersion) : that.apiVersion != null)
            return false;
        if (apis != null ? !apis.equals(that.apis) : that.apis != null) return false;
        if (authorizations != null ? !authorizations.equals(that.authorizations) : that.authorizations != null)
            return false;
        if (info != null ? !info.equals(that.info) : that.info != null) return false;
        if (swaggerVersion != null ? !swaggerVersion.equals(that.swaggerVersion) : that.swaggerVersion != null)
            return false;

        return true;
    }

    @Override
    public String toString() {
        return "ResourceListing{" +
            "swaggerVersion='" + swaggerVersion + '\'' +
            ", apis=" + apis +
            ", apiVersion='" + apiVersion + '\'' +
            ", info=" + info +
            ", authorizations=" + authorizations +
            '}';
    }

    public static class ResourceListingBuilder {

        private String swaggerVersion;
        private ArrayList<Resource> apis;
        private String apiVersion;
        private Info info;
        private List<Authorization> authorizations;

        ResourceListingBuilder() {
        }

        public ResourceListingBuilder api(final Resource api) {
            if (this.apis == null) this.apis = new ArrayList<Resource>();
            this.apis.add(api);
            return this;
        }

        public ResourceListingBuilder apiVersion(final String apiVersion) {
            this.apiVersion = apiVersion;
            return this;
        }

        public ResourceListingBuilder apis(final java.util.Collection<? extends Resource> apis) {
            if (this.apis == null) this.apis = new ArrayList<Resource>();
            this.apis.addAll(apis);
            return this;
        }

        public ResourceListingBuilder authorizations(final List<Authorization> authorizations) {
            this.authorizations = authorizations;
            return this;
        }

        public ResourceListing build() {
            List<Resource> apis;
            switch (this.apis == null ? 0 : this.apis.size()) {
                case 0:
                    apis = java.util.Collections.emptyList();
                    break;

                case 1:
                    apis = java.util.Collections.singletonList(this.apis.get(0));
                    break;

                default:
                    apis = java.util.Collections.unmodifiableList(new ArrayList<Resource>(this.apis));

            }
            return new ResourceListing(swaggerVersion, apis, apiVersion, info, authorizations);
        }

        public ResourceListingBuilder info(final Info info) {
            this.info = info;
            return this;
        }

        public ResourceListingBuilder swaggerVersion(final String swaggerVersion) {
            this.swaggerVersion = swaggerVersion;
            return this;
        }

        @Override
        public String toString() {
            return "ResourceListingBuilder{" +
                "swaggerVersion='" + swaggerVersion + '\'' +
                ", apis=" + apis +
                ", apiVersion='" + apiVersion + '\'' +
                ", info=" + info +
                ", authorizations=" + authorizations +
                '}';
        }
    }
}