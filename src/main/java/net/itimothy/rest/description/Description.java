package net.itimothy.rest.description;

public abstract class Description {

    /**
     * The first version in which this routes became applicable
     */
    private String minVersion;

    /**
     * The last version in which this routes was applicable
     */
    private String maxVersion;

    protected Description(String minVersion, String maxVersion) {
        this.minVersion = minVersion;
        this.maxVersion = maxVersion;
    }

    public String getMaxVersion() {
        return maxVersion;
    }

    public void setMaxVersion(String maxVersion) {
        this.maxVersion = maxVersion;
    }

    public String getMinVersion() {
        return minVersion;
    }

    public void setMinVersion(String minVersion) {
        this.minVersion = minVersion;
    }

    @Override
    public int hashCode() {
        int result = minVersion != null ? minVersion.hashCode() : 0;
        result = 31 * result + (maxVersion != null ? maxVersion.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Description)) return false;

        Description that = (Description) o;

        if (maxVersion != null ? !maxVersion.equals(that.maxVersion) : that.maxVersion != null)
            return false;
        if (minVersion != null ? !minVersion.equals(that.minVersion) : that.minVersion != null)
            return false;

        return true;
    }

    @Override
    public String toString() {
        return "Description{" +
            "minVersion='" + minVersion + '\'' +
            ", maxVersion='" + maxVersion + '\'' +
            '}';
    }
}
