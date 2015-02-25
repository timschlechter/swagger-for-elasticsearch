package org.elasticsearch.plugin.swagger.v1_2.rest;

import net.itimothy.rest.metadata.model.Primitive;
import net.itimothy.rest.metadata.model.Property;
import net.itimothy.rest.metadata.model.Route;
import org.elasticsearch.common.lang3.StringUtils;
import org.elasticsearch.plugin.swagger.v1_2.model.*;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MetadataConverter {
    public HttpMethod convert(net.itimothy.rest.metadata.model.HttpMethod httpMethod) {
        switch (httpMethod) {
            case DELETE:
                return HttpMethod.DELETE;
            case GET:
                return HttpMethod.GET;
            case OPTIONS:
                return HttpMethod.OPTIONS;
            case PATCH:
                return HttpMethod.PATCH;
            case POST:
                return HttpMethod.POST;
            case PUT:
                return HttpMethod.PUT;
        }

        return null;
    }

    public List<Api> toApis(List<Route> routes) {
        return routes.stream()
            .collect(Collectors.groupingBy(r -> r.getApiPath()))
            .values()            
            .stream()            
            .map(this::toApi)
            .sorted(Comparator.comparing(a -> a.getPath()))
            .collect(Collectors.toList());
    }

    public Api toApi(List<Route> apiRoutes) {
        return Api.builder()
            .path("/" + apiRoutes.get(0).getApiPath())
            .operations(
                apiRoutes.stream()
                    .map(this::toOperation)
                    .collect(Collectors.toList())
            )
            .build();
    }

    public Operation toOperation(Route route) {
        return Operation.builder()
            .type(route.getModel() == null ? "void" : route.getModel().getId())
            .method(convert(route.getMethod()))
            .nickname(toNickname(route))
            .parameters(toParameters(route.getParameters()))
            .build();
    }

    private List<Parameter> toParameters(List<net.itimothy.rest.metadata.model.Parameter> parameters) {
        if (parameters == null) {
            return null;
        }

        return parameters.stream()
            .map(this::toParameter)
            .collect(Collectors.toList());
    }

    private Parameter toParameter(net.itimothy.rest.metadata.model.Parameter parameter) {
        net.itimothy.rest.metadata.model.Model model = parameter.getModel();
        Primitive primitive = model instanceof Primitive ? (Primitive) model : null;
        
        ParameterType paramType = convert(parameter.getParamType());
        
        return Parameter.builder()
            .name(paramType == ParameterType.body ? "body" : parameter.getName())
            .type(primitive != null ? primitive.getType() : model.getId())
            .paramType(convert(parameter.getParamType()))
            .required(paramType == ParameterType.path
                    ? true
                    : parameter.getRequired() != null
                    ? parameter.getRequired()
                    : false
            )
            .build();
    }

    private ParameterType convert(net.itimothy.rest.metadata.model.ParameterType paramType) {
        switch (paramType) {
            case BODY:
                return ParameterType.body;
            case FORM:
                return ParameterType.form;
            case QUERY:
                return ParameterType.query;
            case PATH:
                return ParameterType.path;
            case HEADER:
                return ParameterType.header;
        }
        
        return null;
    }

    private String toNickname(Route route) {
        return StringUtils.isBlank(route.getName())
            ? toCamelCase(String.format("%s %s", route.getMethod().name().toLowerCase(), route.getApiPath()))
            : route.getName();
    }

    public List<Model> toModels(List<Route> routes) {
        List<net.itimothy.rest.metadata.model.Model> responseModels = routes.stream()
            .map(r -> r.getModel())
            .collect(Collectors.toList());

        List<net.itimothy.rest.metadata.model.Model> responseResponsesModels = routes.stream()
            .filter(r -> r.getResponseMessages() != null)
            .flatMap(r -> r.getResponseMessages().stream())
            .map(m -> m.getModel())
            .collect(Collectors.toList());

        List<net.itimothy.rest.metadata.model.Model> parameterModels = routes.stream()
            .filter(r -> r.getParameters() != null)
            .flatMap(r -> r.getParameters().stream())
            .map(p -> p.getModel())
            .collect(Collectors.toList());

        Set<net.itimothy.rest.metadata.model.Model> result = new HashSet<>();
        result.addAll(responseModels);
        result.addAll(responseResponsesModels);
        result.addAll(parameterModels);

        List<net.itimothy.rest.metadata.model.Model> propertyModels = result.stream()
            .filter(m -> m != null)
            .filter(m -> m.getProperties() != null)
            .flatMap(m -> m.getProperties().stream())
            .map(p -> p.getModel())
            .collect(Collectors.toList());

        result.addAll(propertyModels);

        return result.stream()
            .filter(m -> m != null)
            .filter(m -> !m.isPrimitive())
            .map(m -> toModel(m))
            .collect(Collectors.toList());
    }

    private Model toModel(net.itimothy.rest.metadata.model.Model model) {
        return Model.builder()
            .id(model.getId())
            .properties(
                model.getProperties().stream()
                    .map(this::toModelProperty)
                    .collect(Collectors.toList())
            )
            .build();
    }

    private ModelProperty toModelProperty(Property property) {
        net.itimothy.rest.metadata.model.Model model = property.getModel();
        Primitive primitive = model instanceof Primitive ? (Primitive) model : null;
        return ModelProperty.builder()
            .name(property.getName())
            .description(property.description)
            .type(primitive != null ? primitive.getType() : null)
            .ref(primitive == null ? model.getId() : null)
            .build();
    }

    private static String toCamelCase(final String value) {
        return StringUtils.capitalize(value);
    }

    public ResourceListing toResourceListing(String version, List<Route> routes) {
        return ResourceListing.builder()
            .swaggerVersion("1.2")
            .apiVersion(version)
            .apis(
                routes.stream()
                    .map(r -> r.getResourcePath())
                    .distinct()
                    .map(p -> Resource.builder().path("/" + p).build())
                    .collect(Collectors.toList())
            )
            .info(
                Info.builder()
                    .title("Elasticsearch API")
                    .description("Swagger 1.2 API for this Elasticsearch instance")
                    .build()
            )
            .build();
    }
}
