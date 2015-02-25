package net.itimothy.rest.metadata.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Property {
    public Object defaultValue;

    public String description;

    public List<String> enumValues;

    public Long maximum;

    public Long minimum;

    public String name;

    public Boolean required;

    public Model model;

    public Boolean uniqueItems;
}
