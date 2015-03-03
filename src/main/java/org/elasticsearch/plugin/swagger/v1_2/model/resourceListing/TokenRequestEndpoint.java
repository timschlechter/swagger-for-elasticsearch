package org.elasticsearch.plugin.swagger.v1_2.model.resourceListing;

import org.elasticsearch.plugin.swagger.v1_2.model.SwaggerModel;

/**
 * Provides details regarding the OAuth2's Authorization Endpoint.
 */
public class TokenRequestEndpoint extends SwaggerModel {

    /**
     * The URL of the authorization endpoint for the authentication code grant flow.
     */
    private String url;

    /**
     * An optional alternative name to standard "client_id" OAuth2 parameter.
     */
    private String clientIdName;

    /**
     * An optional alternative name to standard "client_secret" OAuth2 parameter.
     */
    private String clientSecretName;

    public TokenRequestEndpoint() {
    }

    public TokenRequestEndpoint(final String url, final String clientIdName, final String clientSecretName) {
        this.url = url;
        this.clientIdName = clientIdName;
        this.clientSecretName = clientSecretName;
    }

    public static TokenRequestEndpointBuilder builder() {
        return new TokenRequestEndpointBuilder();
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TokenRequestEndpoint;
    }

    public String getClientIdName() {
        return this.clientIdName;
    }

    public void setClientIdName(final String clientIdName) {
        this.clientIdName = clientIdName;
    }

    public String getClientSecretName() {
        return this.clientSecretName;
    }

    public void setClientSecretName(final String clientSecretName) {
        this.clientSecretName = clientSecretName;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + (clientIdName != null ? clientIdName.hashCode() : 0);
        result = 31 * result + (clientSecretName != null ? clientSecretName.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TokenRequestEndpoint)) return false;

        TokenRequestEndpoint that = (TokenRequestEndpoint) o;

        if (clientIdName != null ? !clientIdName.equals(that.clientIdName) : that.clientIdName != null)
            return false;
        if (clientSecretName != null ? !clientSecretName.equals(that.clientSecretName) : that.clientSecretName != null)
            return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return "TokenRequestEndpoint{" +
            "url='" + url + '\'' +
            ", clientIdName='" + clientIdName + '\'' +
            ", clientSecretName='" + clientSecretName + '\'' +
            '}';
    }

    public static class TokenRequestEndpointBuilder {

        private String url;

        private String clientIdName;

        private String clientSecretName;

        TokenRequestEndpointBuilder() {
        }

        public TokenRequestEndpoint build() {
            return new TokenRequestEndpoint(url, clientIdName, clientSecretName);
        }

        public TokenRequestEndpointBuilder clientIdName(final String clientIdName) {
            this.clientIdName = clientIdName;
            return this;
        }

        public TokenRequestEndpointBuilder clientSecretName(final String clientSecretName) {
            this.clientSecretName = clientSecretName;
            return this;
        }

        public TokenRequestEndpointBuilder url(final String url) {
            this.url = url;
            return this;
        }

        @Override
        public String toString() {
            return "TokenRequestEndpointBuilder{" +
                "url='" + url + '\'' +
                ", clientIdName='" + clientIdName + '\'' +
                ", clientSecretName='" + clientSecretName + '\'' +
                '}';
        }
    }

}