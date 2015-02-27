package org.elasticsearch.swagger.v1_2;

import org.elasticsearch.common.lang3.StringUtils;
import org.elasticsearch.metadata.Primitive;
import org.elasticsearch.metadata.Property;
import org.elasticsearch.metadata.Response;
import org.elasticsearch.metadata.Route;
import org.elasticsearch.swagger.v1_2.model.apiDeclaration.*;
import org.elasticsearch.swagger.v1_2.model.resourceListing.Info;
import org.elasticsearch.swagger.v1_2.model.resourceListing.Resource;
import org.elasticsearch.swagger.v1_2.model.resourceListing.ResourceListing;

import java.util.*;
import java.util.stream.Collectors;

public class SwaggerMetadataConverter {
    public HttpMethod convert(org.elasticsearch.metadata.HttpMethod httpMethod) {
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

    private List<Parameter> toParameters(List<org.elasticsearch.metadata.Parameter> parameters) {
        if (parameters == null) {
            return null;
        }

        return parameters.stream()
            .map(this::toParameter)
            .collect(Collectors.toList());
    }

    private Parameter toParameter(org.elasticsearch.metadata.Parameter parameter) {
        org.elasticsearch.metadata.Model model = parameter.getModel();
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
            ._enum(parameter._enum)
            .build();
    }

    private ParameterType convert(org.elasticsearch.metadata.ParameterType paramType) {
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
        List<org.elasticsearch.metadata.Model> responseModels = routes.stream()
            .map(r -> r.getModel())
            .collect(Collectors.toList());

        List<org.elasticsearch.metadata.Model> responseResponsesModels = routes.stream()
            .filter(r -> r.getResponses() != null)
            .flatMap(r -> r.getResponses().stream())
            .map(m -> m.getModel())
            .collect(Collectors.toList());

        List<org.elasticsearch.metadata.Model> parameterModels = routes.stream()
            .filter(r -> r.getParameters() != null)
            .flatMap(r -> r.getParameters().stream())
            .map(p -> p.getModel())
            .collect(Collectors.toList());

        Set<org.elasticsearch.metadata.Model> result = new HashSet<>();
        result.addAll(responseModels);
        result.addAll(responseResponsesModels);
        result.addAll(parameterModels);

        List<org.elasticsearch.metadata.Model> propertyModels = result.stream()
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

    private List<Property> flattenProperties(org.elasticsearch.metadata.Model model) {
        List<Property> result = new ArrayList<>(model.getProperties());

        for(Property p : model.getProperties()) {
            if (p.model != null && p.model.getProperties() != null) {
                result.addAll(flattenProperties(p.model));
            }
        }

        return result;
    }

    private Model toModel(org.elasticsearch.metadata.Model model) {
        return Model.builder()
            .id(model.getId())
            .properties(
                model.getProperties().stream()
                    .map(this::toModelProperty)
                    .collect(Collectors.toMap(
                        p -> p.getName(),
                        p -> p
                    ))
            )
            .build();
    }

    private ModelProperty toModelProperty(Property property) {
        org.elasticsearch.metadata.Model model = property.getModel();
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
                    .map(r -> r.getGroup())
                    .distinct()
                    .map(p -> Resource.builder().path("/" + p).build())
                    .collect(Collectors.toList())
            )
            .info(
                Info.builder()
                    .title("Elasticsearch " + version + " API")
                    .description("Swagger 1.2 API for this Elasticsearch instance")
                    .build()
            )
            .build();
    }
}
