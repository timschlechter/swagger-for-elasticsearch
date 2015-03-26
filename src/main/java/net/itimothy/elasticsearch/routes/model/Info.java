package net.itimothy.elasticsearch.routes.model;

public class Info extends Description {
    private String version;
    private String title;
    private String description;

    public Info(String minVersion, String maxVersion, String version, String title, String description) {
        super(minVersion, maxVersion);
        this.version = version;
        this.title = title;
        this.description = description;
    }

    public static InfoBuilder builder() {
        return new InfoBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Info)) return false;

        Info info = (Info) o;

        if (description != null ? !description.equals(info.description) : info.description != null)
            return false;
        if (title != null ? !title.equals(info.title) : info.title != null) return false;
        if (version != null ? !version.equals(info.version) : info.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = version != null ? version.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Info{" +
            "version='" + version + '\'' +
            ", title='" + title + '\'' +
            ", routes='" + description + '\'' +
            '}';
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    public static class InfoBuilder {
        private String minVersion;
        private String maxVersion;
        private String version;
        private String title;
        private String description;

        InfoBuilder() {
        }

        public Info build() {
            return new Info(minVersion, maxVersion, version, title, description);
        }

        public InfoBuilder description(final String description) {
            this.description = description;
            return this;
        }

        public InfoBuilder maxVersion(final String maxVersion) {
            this.maxVersion = maxVersion;
            return this;
        }

        public InfoBuilder minVersion(final String minVersion) {
            this.minVersion = minVersion;
            return this;
        }

        public InfoBuilder title(final String title) {
            this.title = title;
            return this;
        }

        @Override
        public String toString() {
            return "InfoBuilder{" +
                "minVersion='" + minVersion + '\'' +
                ", maxVersion='" + maxVersion + '\'' +
                ", version='" + version + '\'' +
                ", title='" + title + '\'' +
                ", routes='" + description + '\'' +
                '}';
        }

        public InfoBuilder version(final String version) {
            this.version = version;
            return this;
        }
    }
}