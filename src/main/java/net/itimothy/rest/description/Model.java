package net.itimothy.rest.description;

import lombok.*;

import java.util.List;

@Data
public class Model extends Description {
    private String id;    

    private List<Property> properties;

    private String name;

    private String description;

    @Builder
    public Model(String minVersion, String maxVersion, String id, List<Property> properties, String name, String description) {
        super(minVersion, maxVersion);
        this.id = id;
        this.properties = properties;
        this.name = name;
        this.description = description;
    }
    
    public boolean isPrimitive() {
        return false;
    }
}
