package org.elasticsearch.swagger.v1_2;

import lombok.Data;
import org.elasticsearch.metadata.providers.RoutesMetadataProvider;
import org.elasticsearch.metadata.Route;
import org.elasticsearch.swagger.v1_2.model.apiDeclaration.ApiDeclaration;
import org.elasticsearch.swagger.v1_2.model.resourceListing.ResourceListing;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class SwaggerProvider {

    RoutesMetadataProvider metadataProvider;
    SwaggerMetadataConverter metadataConverter = new SwaggerMetadataConverter();

    public SwaggerProvider(RoutesMetadataProvider provider) {
        this.metadataProvider = provider;
    }

    public ResourceListing getResourceListing() {
        List<Route> routes = metadataProvider.getRoutes();

        return metadataConverter.toResourceListing(metadataProvider.getVersion(), routes);
    }

    public ApiDeclaration getApiDeclaration(String resource) {
        List<Route> routes = metadataProvider.getRoutes().stream()
            .filter(r -> r.getGroup().equals(resource))
            .collect(Collectors.toList());

        return ApiDeclaration.builder()
            .swaggerVersion("1.2")
            .apiVersion(metadataProvider.getVersion())
            .basePath("/")
            .resourcePath("/" + resource)
            .apis(metadataConverter.toApis(routes))
            .models(metadataConverter.toModels(routes))
            .build();
    }
}
