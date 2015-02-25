package net.itimothy.rest.metadata.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@Builder
public class Route {
    @NonNull
    private String resourcePath;

    @NonNull
    private String apiPath;

    @NonNull
    private HttpMethod method;

    private Model model;

    private String name;

    private List<Parameter> parameters;

    private String description;

    private String notes;

    private List<Response> responseMessages;

    private List<String> produces;

    private List<String> consumes;
}
