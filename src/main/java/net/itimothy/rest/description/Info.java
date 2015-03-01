package net.itimothy.rest.description;

import lombok.*;

@Data
public class Info extends Description {
    private String version;

    private String title;

    private String description;
    
    @Builder
    public Info(String minVersion, String maxVersion, String version, String title, String description) {
        super(minVersion, maxVersion);
        this.version = version;
        this.title = title;
        this.description = description;
    }
}
