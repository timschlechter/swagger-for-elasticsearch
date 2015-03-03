package org.elasticsearch.plugin.swagger.v1_2.model.resourceListing;

import org.elasticsearch.plugin.swagger.v1_2.model.SwaggerModel;

/**
 * Provides details regarding the OAuth2's Authorization Code Grant flow model.
 */
public class AuthorizationCodeGrantFlow extends SwaggerModel {

    /**
     * The token request endpoint definition.
     */
    private TokenRequestEndpoint tokenRequestEndpoint;

    /**
     * The token endpoint definition.
     */
    private TokenEndpoint tokenEndpoint;

    public AuthorizationCodeGrantFlow() {
    }

    public AuthorizationCodeGrantFlow(final TokenRequestEndpoint tokenRequestEndpoint, final TokenEndpoint tokenEndpoint) {
        if (tokenRequestEndpoint == null) {
            throw new NullPointerException("tokenRequestEndpoint");
        }
        if (tokenEndpoint == null) {
            throw new NullPointerException("tokenEndpoint");
        }
        this.tokenRequestEndpoint = tokenRequestEndpoint;
        this.tokenEndpoint = tokenEndpoint;
    }

    public static AuthorizationCodeGrantFlowBuilder builder() {
        return new AuthorizationCodeGrantFlowBuilder();
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AuthorizationCodeGrantFlow;
    }

    public TokenEndpoint getTokenEndpoint() {
        return this.tokenEndpoint;
    }

    public void setTokenEndpoint(final TokenEndpoint tokenEndpoint) {
        if (tokenEndpoint == null) {
            throw new NullPointerException("tokenEndpoint");
        }
        this.tokenEndpoint = tokenEndpoint;
    }

    public TokenRequestEndpoint getTokenRequestEndpoint() {
        return this.tokenRequestEndpoint;
    }

    public void setTokenRequestEndpoint(final TokenRequestEndpoint tokenRequestEndpoint) {
        if (tokenRequestEndpoint == null) {
            throw new NullPointerException("tokenRequestEndpoint");
        }
        this.tokenRequestEndpoint = tokenRequestEndpoint;
    }

    @Override
    public int hashCode() {
        int result = tokenRequestEndpoint != null ? tokenRequestEndpoint.hashCode() : 0;
        result = 31 * result + (tokenEndpoint != null ? tokenEndpoint.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorizationCodeGrantFlow)) return false;

        AuthorizationCodeGrantFlow that = (AuthorizationCodeGrantFlow) o;

        if (tokenEndpoint != null ? !tokenEndpoint.equals(that.tokenEndpoint) : that.tokenEndpoint != null)
            return false;
        if (tokenRequestEndpoint != null ? !tokenRequestEndpoint.equals(that.tokenRequestEndpoint) : that.tokenRequestEndpoint != null)
            return false;

        return true;
    }

    @Override
    public String toString() {
        return "AuthorizationCodeGrantFlow{" +
            "tokenRequestEndpoint=" + tokenRequestEndpoint +
            ", tokenEndpoint=" + tokenEndpoint +
            '}';
    }

    public static class AuthorizationCodeGrantFlowBuilder {

        private TokenRequestEndpoint tokenRequestEndpoint;
        private TokenEndpoint tokenEndpoint;

        AuthorizationCodeGrantFlowBuilder() {
        }

        public AuthorizationCodeGrantFlow build() {
            return new AuthorizationCodeGrantFlow(tokenRequestEndpoint, tokenEndpoint);
        }

        public AuthorizationCodeGrantFlowBuilder tokenEndpoint(final TokenEndpoint tokenEndpoint) {
            this.tokenEndpoint = tokenEndpoint;
            return this;
        }

        public AuthorizationCodeGrantFlowBuilder tokenRequestEndpoint(final TokenRequestEndpoint tokenRequestEndpoint) {
            this.tokenRequestEndpoint = tokenRequestEndpoint;
            return this;
        }

        @Override
        public String toString() {
            return "AuthorizationCodeGrantFlowBuilder{" +
                "tokenRequestEndpoint=" + tokenRequestEndpoint +
                ", tokenEndpoint=" + tokenEndpoint +
                '}';
        }
    }
}