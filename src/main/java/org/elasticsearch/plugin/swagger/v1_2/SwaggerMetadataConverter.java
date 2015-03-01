package org.elasticsearch.plugin.swagger.v1_2;

import net.itimothy.rest.description.Primitive;
import net.itimothy.rest.description.Property;
import net.itimothy.rest.description.Response;
import net.itimothy.rest.description.Route;
import org.elasticsearch.common.lang3.StringUtils;
import org.elasticsearch.plugin.swagger.v1_2.model.apiDeclaration.*;
import org.elasticsearch.plugin.swagger.v1_2.model.resourceListing.Info;
import org.elasticsearch.plugin.swagger.v1_2.model.resourceListing.Resource;
import org.elasticsearch.plugin.swagger.v1_2.model.resourceListing.ResourceListing;

import java.util.*;
import java.util.stream.Collectors;

public class SwaggerMetadataConverter {
    public HttpMethod convert(net.itimothy.rest.description.HttpMethod httpMethod) {
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
            case HEAD:
                return HttpMethod.HEAD;
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
            .summary(route.getDescription())
            .notes(route.getNotes())
            .nickname(toNickname(route))
            .parameters(toParameters(route.getParameters()))
            .responseMessages(toResponseMessages(route.getResponses()))
            .build();
    }

    private List<ResponseMessage> toResponseMessages(List<Response> responses) {
        if (responses == null) {
            return null;
        }

        return responses.stream()
            .map(r -> ResponseMessage.builder()
                    .code(r.getCode())
                    .message(r.getMessage())
                    .responseModel(r.getModel() != null ? r.getModel().getId() : null)
                    .build()
            )
            .sorted(Comparator.comparing(r -> r.getCode()))
            .collect(Collectors.toList());
    }

    private List<Parameter> toParameters(List<net.itimothy.rest.description.Parameter> parameters) {
        if (parameters == null) {
            return null;
        }

        return parameters.stream()
            .map(this::toParameter)
            .collect(Collectors.toList());
    }

    private Parameter toParameter(net.itimothy.rest.description.Parameter parameter) {
        net.itimothy.rest.description.Model model = parameter.getModel();
        Primitive primitive = model instanceof Primitive ? (Primitive) model : null;

        ParameterType paramType = convert(parameter.getParamType());

        return Parameter.builder()
            .name(paramType == ParameterType.body ? "body" : parameter.getName())
            .type(primitive != null ? primitive.getType() : model.getId())
            .paramType(convert(parameter.getParamType()))
            .description(parameter.getDescription())
            .required(paramType == ParameterType.path
                    ? true
                    : parameter.getRequired() != null
                    ? parameter.getRequired()
                    : false
            )
            ._enum(parameter.get_enum())
            .build();
    }

    private ParameterType convert(net.itimothy.rest.description.ParameterType paramType) {
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

    public Map<String, Model> toModels(List<Route> routes) {
        List<net.itimothy.rest.description.Model> responseModels = routes.stream()
            .map(r -> r.getModel())
            .collect(Collectors.toList());

        List<net.itimothy.rest.description.Model> responseResponsesModels = routes.stream()
            .filter(r -> r.getResponses() != null)
            .flatMap(r -> r.getResponses().stream())
            .map(m -> m.getModel())
            .collect(Collectors.toList());

        List<net.itimothy.rest.description.Model> parameterModels = routes.stream()
            .filter(r -> r.getParameters() != null)
            .flatMap(r -> r.getParameters().stream())
            .map(p -> p.getModel())
            .collect(Collectors.toList());

        Set<net.itimothy.rest.description.Model> result = new HashSet<>();
        result.addAll(responseModels);
        result.addAll(responseResponsesModels);
        result.addAll(parameterModels);

        List<net.itimothy.rest.description.Model> propertyModels = result.stream()
            .filter(m -> m != null)
            .filter(m -> m.getProperties() != null)
            .flatMap(m -> flattenProperties(m).stream())
            .map(p -> p.getModel())
            .collect(Collectors.toList());

        result.addAll(propertyModels);

        return result.stream()
            .filter(m -> m != null)
            .filter(m -> !m.isPrimitive())
            .map(m -> toModel(m))
            .collect(Collectors.toMap(
                m -> m.getId(),
                m -> m
            ));
    }

    private List<Property> flattenProperties(net.itimothy.rest.description.Model model) {
        List<Property> result = new ArrayList<>(model.getProperties());

        for (Property p : model.getProperties()) {
            if (p.getModel() != null && p.getModel().getProperties() != null) {
                result.addAll(flattenProperties(p.getModel()));
            }
        }

        return result;
    }

    private Model toModel(net.itimothy.rest.description.Model model) {
        return Model.builder()
            .id(model.getId())
            .required(
                model.getProperties().stream()
                    .filter(p -> p != null)
                    .filter(p -> p.getRequired() != null)
                    .filter(p -> p.getRequired())
                    .map(p -> p.getName())
                    .collect(Collectors.toList())
            )
            .properties(
                model.getProperties().stream()
                    .map(this::toModelProperty)
                    .collect(Collectors.toMap(
                        p -> p.getName(),
                        p -> p
                    ))
            ).build();
    }

    private ModelProperty toModelProperty(Property property) {
        net.itimothy.rest.description.Model model = property.getModel();
        Primitive primitive = model instanceof Primitive ? (Primitive) model : null;
        return ModelProperty.builder()
            .name(property.getName())
            .description(property.getDescription())
            .type(primitive != null ? primitive.getType() : null)
            .ref(primitive == null ? model.getId() : null).build();
    }

    private static String toCamelCase(final String value) {
        return StringUtils.capitalize(value);
    }

    public ResourceListing toResourceListing(net.itimothy.rest.description.Info info, List<Route> routes) {
        return ResourceListing.builder()
            .swaggerVersion("1.2")
            .apiVersion(info.getVersion())
            .apis(
                routes.stream()
                    .map(r -> r.getGroup())
                    .distinct()
                    .map(p -> Resource.builder().path("/" + p).build())
                    .collect(Collectors.toList())
            )
            .info(
                Info.builder()
                    .title(info.getTitle())
                    .description(info.getDescription()).build()
            ) .build();
    }
}
