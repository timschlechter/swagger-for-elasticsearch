package net.itimothy.rest.description;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@Builder
public class Route {
    @NonNull
    private String apiPath;

    @NonNull
    private HttpMethod method;

    /**
     * Can be used to group routes
     */
    private String group;

    private Model model;

    private String name;

    private List<Parameter> parameters;

    private String description;

    private String notes;

    private List<Response> responses;

    private List<String> produces;

    private List<String> consumes;
}
