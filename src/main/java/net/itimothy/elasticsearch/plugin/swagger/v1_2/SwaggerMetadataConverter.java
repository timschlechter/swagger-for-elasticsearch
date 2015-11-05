package net.itimothy.elasticsearch.plugin.swagger.v1_2;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import net.itimothy.elasticsearch.routes.model.*;
import org.apache.commons.lang3.StringUtils;
import net.itimothy.elasticsearch.plugin.swagger.v1_2.model.Items;
import net.itimothy.elasticsearch.plugin.swagger.v1_2.model.apiDeclaration.*;
import net.itimothy.elasticsearch.plugin.swagger.v1_2.model.apiDeclaration.HttpMethod;
import net.itimothy.elasticsearch.plugin.swagger.v1_2.model.apiDeclaration.Model;
import net.itimothy.elasticsearch.plugin.swagger.v1_2.model.apiDeclaration.Parameter;
import net.itimothy.elasticsearch.plugin.swagger.v1_2.model.resourceListing.Info;
import net.itimothy.elasticsearch.plugin.swagger.v1_2.model.resourceListing.Resource;
import net.itimothy.elasticsearch.plugin.swagger.v1_2.model.resourceListing.ResourceListing;
import net.itimothy.util.CollectionUtil;
import org.jboss.netty.util.internal.StringUtil;

import java.util.*;

class SwaggerMetadataConverter {
    private static final Map<net.itimothy.elasticsearch.routes.model.HttpMethod, HttpMethod> httpMethodMap =
        ImmutableMap.<net.itimothy.elasticsearch.routes.model.HttpMethod, HttpMethod>builder()
            .put(net.itimothy.elasticsearch.routes.model.HttpMethod.DELETE, HttpMethod.DELETE)
            .put(net.itimothy.elasticsearch.routes.model.HttpMethod.GET, HttpMethod.GET)
            .put(net.itimothy.elasticsearch.routes.model.HttpMethod.OPTIONS, HttpMethod.OPTIONS)
            .put(net.itimothy.elasticsearch.routes.model.HttpMethod.PATCH, HttpMethod.PATCH)
            .put(net.itimothy.elasticsearch.routes.model.HttpMethod.POST, HttpMethod.POST)
            .put(net.itimothy.elasticsearch.routes.model.HttpMethod.PUT, HttpMethod.PUT)
            .put(net.itimothy.elasticsearch.routes.model.HttpMethod.HEAD, HttpMethod.HEAD)
            .build();

    private static List<Property> flattenProperties(net.itimothy.elasticsearch.routes.model.Model model) {
        if (model == null) {
            return Collections.emptyList();
        }

        List<Property> result = new ArrayList<>(model.getProperties());

        for (Property p : model.getProperties()) {
            if (p.getModel() != null && p.getModel().getProperties() != null) {
                result.addAll(flattenProperties(p.getModel()));
            }
        }

        return result;
    }

    private static String toCamelCase(final String value) {
        return StringUtils.capitalize(value);
    }

    public HttpMethod convert(net.itimothy.elasticsearch.routes.model.HttpMethod httpMethod) {
        return httpMethodMap.containsKey(httpMethod) ? httpMethodMap.get(httpMethod) : null;
    }

    public List<Api> toApis(List<Route> routes) {
        Map<String, List<Route>> routeGroups = new HashMap<>();

        for (Route route : routes) {
            String groupKey = route.getApiPath();
            if (!routeGroups.containsKey(groupKey)) {
                routeGroups.put(groupKey, new ArrayList<Route>());
            }
            routeGroups.get(groupKey).add(route);
        }

        List<Api> result = new ArrayList<>();
        for (List<Route> routeGroup : routeGroups.values()) {
            result.add(toApi(routeGroup));
        }

        CollectionUtil.sort(result, new Function<Api, Comparable>() {
            @Override
            public Comparable apply(Api api) {
                return api.getPath();
            }
        });

        return result;
    }

    public Api toApi(List<Route> apiRoutes) {
        List<Operation> operations = new ArrayList<>();
        for (Route apiRoute : apiRoutes) {
            operations.add(toOperation(apiRoute));
        }
        
        CollectionUtil.sort(operations, new Function<Operation, Comparable>() {
            @Override
            public Comparable apply(Operation operation) {
                return operation.getMethod();
            }
        });

        return Api.builder()
            .path("/" + apiRoutes.get(0).getApiPath())
            .operations(operations)
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

        List<ResponseMessage> result = new ArrayList<>();
        for (Response response : responses) {
            result.add(
                ResponseMessage.builder()
                    .code(response.getCode())
                    .message(response.getMessage())
                    .responseModel(response.getModel() != null ? response.getModel().getId() : null)
                    .build()
            );
        }

        CollectionUtil.sort(result, new Function<ResponseMessage, Comparable>() {
            @Override
            public Comparable apply(ResponseMessage responseMessage) {
                return responseMessage.getCode();
            }
        });

        return result;
    }

    private List<Parameter> toParameters(List<net.itimothy.elasticsearch.routes.model.Parameter> parameters) {
        if (parameters == null) {
            return null;
        }

        List<Parameter> result = new ArrayList<>();

        for (net.itimothy.elasticsearch.routes.model.Parameter parameter : parameters) {
            result.add(toParameter(parameter));
        }

        return result;
    }

    private Parameter toParameter(net.itimothy.elasticsearch.routes.model.Parameter parameter) {
        net.itimothy.elasticsearch.routes.model.Model model = parameter.getModel();
        Primitive primitive = model instanceof Primitive ? (Primitive) model : null;

        ParameterType paramType = convert(parameter.getParamType());

        return Parameter.builder()
            .name(paramType == ParameterType.body ? "body" : parameter.getName())
            .type(primitive != null ? primitive.getType() : model.getId())
            .paramType(convert(parameter.getParamType()))
            .description(parameter.getDescription())
            .defaultValue(parameter.getDefaultValue())
            .required(paramType == ParameterType.path
                    ? true
                    : parameter.getRequired() != null
                    ? parameter.getRequired()
                    : false
            )
            .enumValues(parameter.getEnumValues())
            .build();
    }

    private ParameterType convert(ParamType paramType) {
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
        Set<net.itimothy.elasticsearch.routes.model.Model> descriptionModels = new HashSet<>();

        for (Route route : routes) {
            if (route.getModel() != null) {
                descriptionModels.add(route.getModel());
            }

            if (route.getResponses() != null) {
                for (Response response : route.getResponses()) {
                    if (response.getModel() != null) {
                        descriptionModels.add(response.getModel());
                    }
                }
            }

            if (route.getParameters() != null) {
                for (net.itimothy.elasticsearch.routes.model.Parameter parameter : route.getParameters()) {
                    if (parameter.getModel() != null) {
                        descriptionModels.add(parameter.getModel());
                    }
                }
            }
        }

        for (net.itimothy.elasticsearch.routes.model.Model model : new ArrayList<>(descriptionModels)) {
            if (model.getProperties() != null) {
                for (Property property : flattenProperties(model)) {
                    if (property.getModel() != null) {
                        descriptionModels.add(property.getModel());
                    }
                }
            }
        }

        Map<String, Model> result = new HashMap<>();

        for (net.itimothy.elasticsearch.routes.model.Model descriptionModel : descriptionModels) {
            if (!descriptionModel.isPrimitive()) {
                Model model = toModel(descriptionModel);
                result.put(model.getId(), model);
            }
        }

        return result;
    }

    private Model toModel(net.itimothy.elasticsearch.routes.model.Model model) {
        List<String> requiredPropertyNames = new ArrayList<>();
        if (model.getProperties() != null) {
            for (Property property : model.getProperties()) {
                if (property != null && property.getRequired() != null && property.getRequired()) {
                    requiredPropertyNames.add((property.getName()));
                }
            }
        }

        Map<String, ModelProperty> properties = new HashMap<>();
        if (model.getProperties() != null) {
            for (Property property : model.getProperties()) {
                ModelProperty modelProperty = toModelProperty(property);
                properties.put(modelProperty.getName(), modelProperty);
            }
        }

        return Model.builder()
            .id(model.getId())
            .required(requiredPropertyNames)
            .properties(properties).build();
    }

    private ModelProperty toModelProperty(Property property) {
        net.itimothy.elasticsearch.routes.model.Model model = property.getModel();
        Primitive primitive = model instanceof Primitive ? (Primitive) model : null;
        ModelProperty.ModelPropertyBuilder modelProperty = ModelProperty.builder()
            .name(property.getName())
            .description(property.getDescription());

        if (property.getIsCollection() != null && property.getIsCollection()) {
            modelProperty.type("array");

            if (primitive != null) {
                modelProperty.items(
                    Items.builder()
                        .type(primitive.getType())
                        .format(primitive.getFormat()).build()
                );
            } else {
                modelProperty.items(
                    Items.builder()
                        .ref(model.getId()).build()
                );
            }
        } else {
            modelProperty
                .type(primitive != null ? primitive.getType() : null)
                .ref(primitive == null ? model.getId() : null);
        }

        return modelProperty.build();
    }

    public ResourceListing toResourceListing(net.itimothy.elasticsearch.routes.model.Info info, List<Route> routes) {
        List<String> resourcPaths = new ArrayList<>();
        for (Route route : routes) {
            String resourcePath = "/" + route.getGroup();
            if (!resourcPaths.contains(resourcePath)) {
                resourcPaths.add(resourcePath);
            }
        }

        List<Resource> resources = new ArrayList<>();
        for (String resourcPath : resourcPaths) {
            resources.add(Resource.builder().path(resourcPath).build());
        }


        return ResourceListing.builder()
            .swaggerVersion("1.2")
            .apiVersion(info.getVersion())
            .apis(resources)
            .info(
                Info.builder()
                    .title(info.getTitle())
                    .description(info.getDescription()).build()
            ).build();
    }
}
