package net.itimothy.elasticsearch.plugin.swagger.v1_2.model.resourceListing;

import net.itimothy.elasticsearch.plugin.swagger.v1_2.model.SwaggerModel;

/**
 * The Info object provides metadata about the API. The metadata can be used by the clients if
 * needed, and can be presented in the Swagger-UI for convenience.
 */
public class Info extends SwaggerModel {

    /**
     * The title of the application.
     */
    private String title;

    /**
     * A short routes of the application.
     */
    private String description;

    /**
     * A URL to the Terms of Service of the API.
     */
    private String termsOfServiceUrl;

    /**
     * An email to be used for API-related correspondence.
     */
    private String contact;

    /**
     * The license name used for the API.
     */
    private String license;

    /**
     * A URL to the license used for the API.
     */
    private String licenseUrl;

    public Info() {
    }

    public Info(final String title, final String description, final String termsOfServiceUrl, final String contact, final String license, final String licenseUrl) {
        if (title == null) {
            throw new NullPointerException("title");
        }
        this.title = title;
        this.description = description;
        this.termsOfServiceUrl = termsOfServiceUrl;
        this.contact = contact;
        this.license = license;
        this.licenseUrl = licenseUrl;
    }

    public static InfoBuilder builder() {
        return new InfoBuilder();
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Info;
    }

    public String getContact() {
        return this.contact;
    }

    public void setContact(final String contact) {
        this.contact = contact;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        if (description == null) {
            throw new NullPointerException("routes");
        }
        this.description = description;
    }

    public String getLicense() {
        return this.license;
    }

    public void setLicense(final String license) {
        this.license = license;
    }

    public String getLicenseUrl() {
        return this.licenseUrl;
    }

    public void setLicenseUrl(final String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    public String getTermsOfServiceUrl() {
        return this.termsOfServiceUrl;
    }

    public void setTermsOfServiceUrl(final String termsOfServiceUrl) {
        this.termsOfServiceUrl = termsOfServiceUrl;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        if (title == null) {
            throw new NullPointerException("title");
        }
        this.title = title;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (termsOfServiceUrl != null ? termsOfServiceUrl.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (license != null ? license.hashCode() : 0);
        result = 31 * result + (licenseUrl != null ? licenseUrl.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Info)) return false;

        Info info = (Info) o;

        if (contact != null ? !contact.equals(info.contact) : info.contact != null) return false;
        if (description != null ? !description.equals(info.description) : info.description != null)
            return false;
        if (license != null ? !license.equals(info.license) : info.license != null) return false;
        if (licenseUrl != null ? !licenseUrl.equals(info.licenseUrl) : info.licenseUrl != null)
            return false;
        if (termsOfServiceUrl != null ? !termsOfServiceUrl.equals(info.termsOfServiceUrl) : info.termsOfServiceUrl != null)
            return false;
        if (title != null ? !title.equals(info.title) : info.title != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return "Info{" +
            "title='" + title + '\'' +
            ", routes='" + description + '\'' +
            ", termsOfServiceUrl='" + termsOfServiceUrl + '\'' +
            ", contact='" + contact + '\'' +
            ", license='" + license + '\'' +
            ", licenseUrl='" + licenseUrl + '\'' +
            '}';
    }

    public static class InfoBuilder {

        private String title;
        private String description;
        private String termsOfServiceUrl;
        private String contact;
        private String license;
        private String licenseUrl;

        InfoBuilder() {
        }

        public Info build() {
            return new Info(title, description, termsOfServiceUrl, contact, license, licenseUrl);
        }

        public InfoBuilder contact(final String contact) {
            this.contact = contact;
            return this;
        }

        public InfoBuilder description(final String description) {
            this.description = description;
            return this;
        }

        public InfoBuilder license(final String license) {
            this.license = license;
            return this;
        }

        public InfoBuilder licenseUrl(final String licenseUrl) {
            this.licenseUrl = licenseUrl;
            return this;
        }

        public InfoBuilder termsOfServiceUrl(final String termsOfServiceUrl) {
            this.termsOfServiceUrl = termsOfServiceUrl;
            return this;
        }

        public InfoBuilder title(final String title) {
            this.title = title;
            return this;
        }

        @Override
        public String toString() {
            return "InfoBuilder{" +
                "title='" + title + '\'' +
                ", routes='" + description + '\'' +
                ", termsOfServiceUrl='" + termsOfServiceUrl + '\'' +
                ", contact='" + contact + '\'' +
                ", license='" + license + '\'' +
                ", licenseUrl='" + licenseUrl + '\'' +
                '}';
        }
    }

}