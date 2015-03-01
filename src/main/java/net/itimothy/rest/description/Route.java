package net.itimothy.rest.description;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class Route extends Description {
    private String apiPath;

    private HttpMethod method;

    private String group;

    private Model model;

    private String name;

    private List<Parameter> parameters;

    private String description;

    private String notes;

    private List<Response> responses;

    private List<String> produces;

    private List<String> consumes;

    @Builder
    public Route(String minVersion, String maxVersion, String apiPath, HttpMethod method, 
                 String group, Model model, String name, List<Parameter> parameters, 
                 String description, String notes, List<Response> responses, List<String> produces,
                 List<String> consumes) {
        super(minVersion, maxVersion);
        this.apiPath = apiPath;
        this.method = method;
        this.group = group;
        this.model = model;
        this.name = name;
        this.parameters = parameters;
        this.description = description;
        this.notes = notes;
        this.responses = responses;
        this.produces = produces;
        this.consumes = consumes;
    }
}
