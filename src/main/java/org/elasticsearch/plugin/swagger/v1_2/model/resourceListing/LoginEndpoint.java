package org.elasticsearch.plugin.swagger.v1_2.model.resourceListing;

import org.elasticsearch.plugin.swagger.v1_2.model.SwaggerModel;

/**
 * Provides details regarding the Implicit Grant's authorization endpoint.
 */
public class LoginEndpoint extends SwaggerModel {

    /**
     * The URL of the authorization endpoint for the implicit grant flow.
     */
    private String url;

    public LoginEndpoint() {
    }

    public LoginEndpoint(final String url) {
        if (url == null) {
            throw new NullPointerException("url");
        }
        this.url = url;
    }

    public static LoginEndpointBuilder builder() {
        return new LoginEndpointBuilder();
    }

    protected boolean canEqual(final Object other) {
        return other instanceof LoginEndpoint;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(final String url) {
        if (url == null) {
            throw new NullPointerException("url");
        }
        this.url = url;
    }

    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoginEndpoint)) return false;

        LoginEndpoint that = (LoginEndpoint) o;

        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return "LoginEndpoint{" +
            "url='" + url + '\'' +
            '}';
    }

    public static class LoginEndpointBuilder {

        private String url;

        LoginEndpointBuilder() {
        }

        public LoginEndpoint build() {
            return new LoginEndpoint(url);
        }

        public LoginEndpointBuilder url(final String url) {
            this.url = url;
            return this;
        }

        @Override
        public String toString() {
            return "LoginEndpointBuilder{" +
                "url='" + url + '\'' +
                '}';
        }
    }

}