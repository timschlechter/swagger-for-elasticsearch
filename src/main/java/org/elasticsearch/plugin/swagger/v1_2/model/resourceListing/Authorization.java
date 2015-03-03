package org.elasticsearch.plugin.swagger.v1_2.model.resourceListing;

import org.elasticsearch.plugin.swagger.v1_2.model.SwaggerModel;

import java.util.List;

/**
 * The Authorization object provides information about a specific authorization scheme.
 */
public class Authorization extends SwaggerModel {

    /**
     * The model of the authorization scheme.
     */
    private AuthorizationType type;

    /**
     * Denotes how the API key must be passed.
     */
    private PassType passAs;

    /**
     * The name of the header or query parameter to be used when passing the API key.
     */
    private String keyname;

    /**
     * A list of supported OAuth2 scopes.
     */
    private List<OAuth2Scope> scopes;

    /**
     * Detailed information about the grant types supported by the oauth2 authorization scheme.
     */
    private GrantTypes grantTypes;

    public Authorization() {
    }

    public Authorization(final AuthorizationType type, final PassType passAs, final String keyname, final List<OAuth2Scope> scopes, final GrantTypes grantTypes) {
        if (type == null) {
            throw new NullPointerException("type");
        }
        if (passAs == null) {
            throw new NullPointerException("passAs");
        }
        if (keyname == null) {
            throw new NullPointerException("keyname");
        }
        this.type = type;
        this.passAs = passAs;
        this.keyname = keyname;
        this.scopes = scopes;
        this.grantTypes = grantTypes;
    }

    public static AuthorizationBuilder builder() {
        return new AuthorizationBuilder();
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Authorization;
    }

    public GrantTypes getGrantTypes() {
        return this.grantTypes;
    }

    public void setGrantTypes(final GrantTypes grantTypes) {
        this.grantTypes = grantTypes;
    }

    public String getKeyname() {
        return this.keyname;
    }

    public void setKeyname(final String keyname) {
        if (keyname == null) {
            throw new NullPointerException("keyname");
        }
        this.keyname = keyname;
    }

    public PassType getPassAs() {
        return this.passAs;
    }

    public void setPassAs(final PassType passAs) {
        if (passAs == null) {
            throw new NullPointerException("passAs");
        }
        this.passAs = passAs;
    }

    public List<OAuth2Scope> getScopes() {
        return this.scopes;
    }

    public void setScopes(final List<OAuth2Scope> scopes) {
        this.scopes = scopes;
    }

    public AuthorizationType getType() {
        return this.type;
    }

    public void setType(final AuthorizationType type) {
        if (type == null) {
            throw new NullPointerException("type");
        }
        this.type = type;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (passAs != null ? passAs.hashCode() : 0);
        result = 31 * result + (keyname != null ? keyname.hashCode() : 0);
        result = 31 * result + (scopes != null ? scopes.hashCode() : 0);
        result = 31 * result + (grantTypes != null ? grantTypes.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Authorization)) return false;

        Authorization that = (Authorization) o;

        if (grantTypes != null ? !grantTypes.equals(that.grantTypes) : that.grantTypes != null)
            return false;
        if (keyname != null ? !keyname.equals(that.keyname) : that.keyname != null) return false;
        if (passAs != that.passAs) return false;
        if (scopes != null ? !scopes.equals(that.scopes) : that.scopes != null) return false;
        if (type != that.type) return false;

        return true;
    }

    @Override
    public String toString() {
        return "Authorization{" +
            "type=" + type +
            ", passAs=" + passAs +
            ", keyname='" + keyname + '\'' +
            ", scopes=" + scopes +
            ", grantTypes=" + grantTypes +
            '}';
    }

    public static class AuthorizationBuilder {

        private AuthorizationType type;
        private PassType passAs;
        private String keyname;
        private List<OAuth2Scope> scopes;
        private GrantTypes grantTypes;

        AuthorizationBuilder() {
        }

        public Authorization build() {
            return new Authorization(type, passAs, keyname, scopes, grantTypes);
        }

        public AuthorizationBuilder grantTypes(final GrantTypes grantTypes) {
            this.grantTypes = grantTypes;
            return this;
        }

        public AuthorizationBuilder keyname(final String keyname) {
            this.keyname = keyname;
            return this;
        }

        public AuthorizationBuilder passAs(final PassType passAs) {
            this.passAs = passAs;
            return this;
        }

        public AuthorizationBuilder scopes(final List<OAuth2Scope> scopes) {
            this.scopes = scopes;
            return this;
        }

        public AuthorizationBuilder type(final AuthorizationType type) {
            this.type = type;
            return this;
        }

        @Override
        public String toString() {
            return "AuthorizationBuilder{" +
                "type=" + type +
                ", passAs=" + passAs +
                ", keyname='" + keyname + '\'' +
                ", scopes=" + scopes +
                ", grantTypes=" + grantTypes +
                '}';
        }
    }

}