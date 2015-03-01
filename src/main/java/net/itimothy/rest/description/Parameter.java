package net.itimothy.rest.description;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class Parameter extends Description {
    private ParameterType paramType;

    private String name;

    private String description;

    private Boolean required;

    private Object defaultValue;

    private Model model;

    public Boolean allowMultiple;
    
    private List<String> _enum;

    @Builder
    public Parameter(String minVersion, String maxVersion, ParameterType paramType, String name,
                     String description, Boolean required, Object defaultValue, Model model,
                     Boolean allowMultiple, List<String> _enum) {
        super(minVersion, maxVersion);
        this.paramType = paramType;
        this.name = name;
        this.description = description;
        this.required = required;
        this.defaultValue = defaultValue;
        this.model = model;
        this.allowMultiple = allowMultiple;
        this._enum = _enum;
    }
}
