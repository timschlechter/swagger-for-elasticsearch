package org.elasticsearch.plugin.swagger.model;

/**
 * The Parameter Object describes a single parameter to be sent in an operation and maps to the
 * parameters field in the Operation Object.
 */
public class Parameter extends DataType {
    /**
     * The type of the parameter (that is, the location of the parameter in the request).
     */
    private String paramType;

    /**
     * The unique name for the parameter. Each name MUST be unique, even if they are associated with
     * different "ParamType values.
     */
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

    public Parameter(String type, ParameterType paramType, String name, String description) {
        this(type, null, paramType, name, description, false, false);
    }
    
    public Parameter(String type, ParameterType paramType, String name, String description, Boolean required) {
        this(type, null, paramType, name, description, required, false);
    }
    
    public Parameter(String type, String $ref, ParameterType paramType, String name, String description, Boolean required, Boolean allowMultiple) {
        super(type, $ref);
        this.paramType = paramType.toString();
        this.name = name;
        this.description = description;
        this.required = required;
        this.allowMultiple = allowMultiple;
    }
}