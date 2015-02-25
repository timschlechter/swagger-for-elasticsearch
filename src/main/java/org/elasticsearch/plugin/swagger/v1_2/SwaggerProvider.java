package org.elasticsearch.plugin.swagger.v1_2;

import lombok.Data;
import net.itimothy.rest.metadata.MetadataProvider;
import net.itimothy.rest.metadata.model.Primitive;
import net.itimothy.rest.metadata.model.Property;
import net.itimothy.rest.metadata.model.Route;
import org.elasticsearch.plugin.swagger.v1_2.model.*;
import org.elasticsearch.plugin.swagger.v1_2.rest.MetadataConverter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class SwaggerProvider {

    MetadataProvider provider;
    MetadataConverter converter = new MetadataConverter();

    public SwaggerProvider(MetadataProvider provider) {
        this.provider = provider;
    }

    public ResourceListing getResourceListing() {
        List<Route> routes = provider.getRoutes();

        return ResourceListing.builder()
            .swaggerVersion("1.2")
            .apiVersion(provider.getVersion())
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

    public ApiDeclaration getApiDeclaration(String resource) {
        List<Route> routes = provider.getRoutes().stream()
            .filter(r -> r.getResourcePath().equals(resource))
            .collect(Collectors.toList());

        return ApiDeclaration.builder()
            .swaggerVersion("1.2")
            .apiVersion(provider.getVersion())
            .basePath("/")
            .resourcePath(resource)
            .apis(
                converter.toApis(routes)
            )
            .models(toModels(routes))
            .build();
    }

    private List<Model> toModels(List<Route> routes) {
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
            .filter(r -> r.getProperties() != null)
            .flatMap(m -> m.getProperties().stream())
            .map(p -> p.getModel())
            .collect(Collectors.toList());

        result.addAll(propertyModels);

        return result.stream()
            .map(m -> toModel(m))
            .collect(Collectors.toList());
    }

    private Model toModel(net.itimothy.rest.metadata.model.Model model) {
        return Model.builder()
            .id(model.getId())
            .properties(
                model.getProperties().stream()
                    .map(this::toProperty)
                    .collect(Collectors.toList())
            )
            .build();
    }

    private ModelProperty toProperty(Property property) {
        net.itimothy.rest.metadata.model.Model model = property.getModel();
        Primitive primitive = model instanceof Primitive ? (Primitive) model : null;
        return ModelProperty.builder()
            .name(property.getName())
            .description(property.description)
            .type(primitive != null ? primitive.getType() : null)
            .ref(primitive == null ? model.getId() : null)
            .build();
    }
}
