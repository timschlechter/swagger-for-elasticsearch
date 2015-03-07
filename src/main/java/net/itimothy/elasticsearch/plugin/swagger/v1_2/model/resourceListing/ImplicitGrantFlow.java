package net.itimothy.elasticsearch.plugin.swagger.v1_2.model.resourceListing;

import net.itimothy.elasticsearch.plugin.swagger.v1_2.model.SwaggerModel;

/**
 * Provides details regarding the OAuth2's Implicit Grant flow model.
 */
public class ImplicitGrantFlow extends SwaggerModel {

    /**
     * The login endpoint definition.
     */
    private LoginEndpoint loginEndpoint;

    /**
     * An optional alternative name to standard "access_token" OAuth2 parameter.
     */
    private String tokenName;

    public ImplicitGrantFlow() {
    }

    public ImplicitGrantFlow(final LoginEndpoint loginEndpoint, final String tokenName) {
        if (loginEndpoint == null) {
            throw new NullPointerException("loginEndpoint");
        }
        this.loginEndpoint = loginEndpoint;
        this.tokenName = tokenName;
    }

    public static ImplicitGrantFlowBuilder builder() {
        return new ImplicitGrantFlowBuilder();
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ImplicitGrantFlow;
    }

    public LoginEndpoint getLoginEndpoint() {
        return this.loginEndpoint;
    }

    public void setLoginEndpoint(final LoginEndpoint loginEndpoint) {
        if (loginEndpoint == null) {
            throw new NullPointerException("loginEndpoint");
        }
        this.loginEndpoint = loginEndpoint;
    }

    public String getTokenName() {
        return this.tokenName;
    }

    public void setTokenName(final String tokenName) {
        this.tokenName = tokenName;
    }

    @Override
    public int hashCode() {
        int result = loginEndpoint != null ? loginEndpoint.hashCode() : 0;
        result = 31 * result + (tokenName != null ? tokenName.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImplicitGrantFlow)) return false;

        ImplicitGrantFlow that = (ImplicitGrantFlow) o;

        if (loginEndpoint != null ? !loginEndpoint.equals(that.loginEndpoint) : that.loginEndpoint != null)
            return false;
        if (tokenName != null ? !tokenName.equals(that.tokenName) : that.tokenName != null)
            return false;

        return true;
    }

    @Override
    public String toString() {
        return "ImplicitGrantFlow{" +
            "loginEndpoint=" + loginEndpoint +
            ", tokenName='" + tokenName + '\'' +
            '}';
    }

    public static class ImplicitGrantFlowBuilder {
        private LoginEndpoint loginEndpoint;
        private String tokenName;

        ImplicitGrantFlowBuilder() {
        }

        public ImplicitGrantFlow build() {
            return new ImplicitGrantFlow(loginEndpoint, tokenName);
        }

        public ImplicitGrantFlowBuilder loginEndpoint(final LoginEndpoint loginEndpoint) {
            this.loginEndpoint = loginEndpoint;
            return this;
        }

        public ImplicitGrantFlowBuilder tokenName(final String tokenName) {
            this.tokenName = tokenName;
            return this;
        }

        @Override
        public String toString() {
            return "ImplicitGrantFlowBuilder{" +
                "loginEndpoint=" + loginEndpoint +
                ", tokenName='" + tokenName + '\'' +
                '}';
        }
    }
}