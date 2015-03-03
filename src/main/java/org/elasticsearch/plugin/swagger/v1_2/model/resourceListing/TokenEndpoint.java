package org.elasticsearch.plugin.swagger.v1_2.model.resourceListing;

import org.elasticsearch.plugin.swagger.v1_2.model.SwaggerModel;

/**
 * Provides details regarding the OAuth2's Token Endpoint.
 */
public class TokenEndpoint extends SwaggerModel {

    /**
     * The URL of the token endpoint for the authentication code grant flow.
     */
    private String url;

    /**
     * An optional alternative name to standard "access_token" OAuth2 parameter.
     */
    private String tokenName;

    public TokenEndpoint() {
    }

    public TokenEndpoint(final String url, final String tokenName) {
        if (url == null) {
            throw new NullPointerException("url");
        }
        this.url = url;
        this.tokenName = tokenName;
    }

    public static TokenEndpointBuilder builder() {
        return new TokenEndpointBuilder();
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TokenEndpoint;
    }

    public String getTokenName() {
        return this.tokenName;
    }

    public void setTokenName(final String tokenName) {
        this.tokenName = tokenName;
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
        result = 31 * result + (tokenName != null ? tokenName.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TokenEndpoint)) return false;

        TokenEndpoint that = (TokenEndpoint) o;

        if (tokenName != null ? !tokenName.equals(that.tokenName) : that.tokenName != null)
            return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return "TokenEndpoint{" +
            "url='" + url + '\'' +
            ", tokenName='" + tokenName + '\'' +
            '}';
    }

    public static class TokenEndpointBuilder {

        private String url;
        private String tokenName;

        TokenEndpointBuilder() {
        }

        public TokenEndpoint build() {
            return new TokenEndpoint(url, tokenName);
        }

        public TokenEndpointBuilder tokenName(final String tokenName) {
            this.tokenName = tokenName;
            return this;
        }

        public TokenEndpointBuilder url(final String url) {
            this.url = url;
            return this;
        }

        @Override
        public String toString() {
            return "TokenEndpointBuilder{" +
                "url='" + url + '\'' +
                ", tokenName='" + tokenName + '\'' +
                '}';
        }
    }

}