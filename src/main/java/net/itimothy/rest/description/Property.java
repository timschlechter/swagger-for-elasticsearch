package net.itimothy.rest.description;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class Property extends Description {
    private Object defaultValue;

    private String description;

    private List<String> enumValues;

    private Long maximum;

    private Long minimum;

    private String name;

    private Boolean required;

    private Model model;

    private Boolean uniqueItems;

    @Builder
    public Property(String minVersion, String maxVersion, Object defaultValue, String description,
                    List<String> enumValues, Long maximum, Long minimum, String name,
                    Boolean required, Model model, Boolean uniqueItems) {
        super(minVersion, maxVersion);
        this.defaultValue = defaultValue;
        this.description = description;
        this.enumValues = enumValues;
        this.maximum = maximum;
        this.minimum = minimum;
        this.name = name;
        this.required = required;
        this.model = model;
        this.uniqueItems = uniqueItems;
    }
}
