package org.elasticsearch.plugin.swagger.v1_2.model.resourceListing;

import org.elasticsearch.plugin.swagger.v1_2.model.SwaggerModel;

/**
 * Provides details regarding the OAuth2 grant types that are supported by the API.
 */
public class GrantTypes extends SwaggerModel {

    /**
     * The Implicit Grant flow definition.
     */
    private ImplicitGrantFlow implicit;

    /**
     * The Authorization Code Grant flow definition.
     */
    private AuthorizationCodeGrantFlow authorization_code;

    public GrantTypes() {
    }

    @java.beans.ConstructorProperties({"implicit", "authorization_code"})

    public GrantTypes(final ImplicitGrantFlow implicit, final AuthorizationCodeGrantFlow authorization_code) {
        this.implicit = implicit;
        this.authorization_code = authorization_code;
    }

    public static GrantTypesBuilder builder() {
        return new GrantTypesBuilder();
    }

    protected boolean canEqual(final Object other) {
        return other instanceof GrantTypes;
    }

    /**
     * The Authorization Code Grant flow definition.
     */

    public AuthorizationCodeGrantFlow getAuthorization_code() {
        return this.authorization_code;
    }

    /**
     * The Authorization Code Grant flow definition.
     */

    public void setAuthorization_code(final AuthorizationCodeGrantFlow authorization_code) {
        this.authorization_code = authorization_code;
    }

    /**
     * The Implicit Grant flow definition.
     */

    public ImplicitGrantFlow getImplicit() {
        return this.implicit;
    }

    /**
     * The Implicit Grant flow definition.
     */

    public void setImplicit(final ImplicitGrantFlow implicit) {
        this.implicit = implicit;
    }

    @Override

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $implicit = this.getImplicit();
        result = result * PRIME + ($implicit == null ? 0 : $implicit.hashCode());
        final Object $authorization_code = this.getAuthorization_code();
        result = result * PRIME + ($authorization_code == null ? 0 : $authorization_code.hashCode());
        return result;
    }

    @Override

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof GrantTypes)) return false;
        final GrantTypes other = (GrantTypes) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$implicit = this.getImplicit();
        final Object other$implicit = other.getImplicit();
        if (this$implicit == null ? other$implicit != null : !this$implicit.equals(other$implicit))
            return false;
        final Object this$authorization_code = this.getAuthorization_code();
        final Object other$authorization_code = other.getAuthorization_code();
        if (this$authorization_code == null ? other$authorization_code != null : !this$authorization_code.equals(other$authorization_code))
            return false;
        return true;
    }

    @Override

    public String toString() {
        return "GrantTypes(implicit=" + this.getImplicit() + ", authorization_code=" + this.getAuthorization_code() + ")";
    }

    public static class GrantTypesBuilder {

        private ImplicitGrantFlow implicit;

        private AuthorizationCodeGrantFlow authorization_code;

        GrantTypesBuilder() {
        }

        public GrantTypesBuilder authorization_code(final AuthorizationCodeGrantFlow authorization_code) {
            this.authorization_code = authorization_code;
            return this;
        }

        public GrantTypes build() {
            return new GrantTypes(implicit, authorization_code);
        }

        public GrantTypesBuilder implicit(final ImplicitGrantFlow implicit) {
            this.implicit = implicit;
            return this;
        }

        @Override

        public String toString() {
            return "GrantTypes.GrantTypesBuilder(implicit=" + this.implicit + ", authorization_code=" + this.authorization_code + ")";
        }
    }

}