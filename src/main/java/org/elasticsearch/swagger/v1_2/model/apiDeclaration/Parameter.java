package org.elasticsearch.swagger.v1_2.model.apiDeclaration;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.elasticsearch.swagger.v1_2.model.DataType;
import org.elasticsearch.swagger.v1_2.model.Items;

import java.util.List;

/**
 * The Parameter Object describes a single parameter to be sent in an operation and maps to the
 * parameters field in the Operation Object.
 */
@Data
public class Parameter extends DataType {
    /**
     * The model of the parameter (that is, the location of the parameter in the request).
     */
    @NonNull
    private ParameterType paramType;

    /**
     * The unique name for the parameter. Each name MUST be unique, even if they are associated with
     * different "ParamType values.
     */
    @NonNull
    private String name;

    /**
     * A brief description of this parameter.
     */
    private String description;

    /**
     * A flag to note whether this parameter is required. If this field is not included, it is
     * equivalent to adding this field with the value false.
     */
    private Boolean required;

    /**
     * Another way to allow multiple values for a qury parameter. If
     * used, the query parameter may accept comma-separated values.
     */
    private Boolean allowMultiple;
    
    @Builder
    public Parameter(String type, String ref, String format, Object defaultValue, 
                     List<String> _enum, Long minimum, Long maximum, Items items, 
                     Boolean uniqueItems,  ParameterType paramType, String name, String description,
                     Boolean required, Boolean allowMultiple) {
        super(type, ref, format, defaultValue, _enum, minimum, maximum, items, uniqueItems);

        this.paramType = paramType;
        this.name = name;
        this.description = description;
        this.required = required;
        this.allowMultiple = allowMultiple;
    }
}