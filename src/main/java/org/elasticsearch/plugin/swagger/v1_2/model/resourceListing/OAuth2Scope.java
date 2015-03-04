package org.elasticsearch.plugin.swagger.v1_2.model.resourceListing;

import org.elasticsearch.plugin.swagger.v1_2.model.SwaggerModel;

/**
 * Describes an OAuth2 authorization scope.
 */
public class OAuth2Scope extends SwaggerModel {

    /**
     * The name of the scope.
     */
    private String scope;

    /**
     * A short routes of the scope.
     */
    private String description;

    public OAuth2Scope() {
    }

    public OAuth2Scope(final String scope, final String description) {
        if (scope == null) {
            throw new NullPointerException("scope");
        }
        this.scope = scope;
        this.description = description;
    }

    public static OAuth2ScopeBuilder builder() {
        return new OAuth2ScopeBuilder();
    }

    protected boolean canEqual(final Object other) {
        return other instanceof OAuth2Scope;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getScope() {
        return this.scope;
    }

    public void setScope(final String scope) {
        if (scope == null) {
            throw new NullPointerException("scope");
        }
        this.scope = scope;
    }

    @Override
    public int hashCode() {
        int result = scope != null ? scope.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OAuth2Scope)) return false;

        OAuth2Scope that = (OAuth2Scope) o;

        if (description != null ? !description.equals(that.description) : that.description != null)
            return false;
        if (scope != null ? !scope.equals(that.scope) : that.scope != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return "OAuth2Scope{" +
            "scope='" + scope + '\'' +
            ", routes='" + description + '\'' +
            '}';
    }

    public static class OAuth2ScopeBuilder {

        private String scope;

        private String description;

        OAuth2ScopeBuilder() {
        }

        public OAuth2Scope build() {
            return new OAuth2Scope(scope, description);
        }

        public OAuth2ScopeBuilder description(final String description) {
            this.description = description;
            return this;
        }

        public OAuth2ScopeBuilder scope(final String scope) {
            this.scope = scope;
            return this;
        }

        @Override
        public String toString() {
            return "OAuth2ScopeBuilder{" +
                "scope='" + scope + '\'' +
                ", routes='" + description + '\'' +
                '}';
        }
    }
}