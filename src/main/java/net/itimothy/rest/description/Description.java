package net.itimothy.rest.description;

public abstract class Description {
    /**
     * The first version in which this description became applicable
     */
    private String minVersion;

    /**
     * The last version in which this description was applicable
     */
    private String maxVersion;
    
    protected Description(String minVersion, String maxVersion) {
        this.minVersion = minVersion;
        this.maxVersion = maxVersion;
    }
}
