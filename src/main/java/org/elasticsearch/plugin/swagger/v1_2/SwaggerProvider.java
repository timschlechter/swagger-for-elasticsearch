package org.elasticsearch.plugin.swagger.v1_2;

import lombok.Data;
import net.itimothy.rest.metadata.MetadataProvider;
import net.itimothy.rest.metadata.model.Route;
import org.elasticsearch.plugin.swagger.v1_2.model.ApiDeclaration;
import org.elasticsearch.plugin.swagger.v1_2.model.ResourceListing;
import org.elasticsearch.plugin.swagger.v1_2.rest.MetadataConverter;

import java.util.List;
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

        return converter.toResourceListing(provider.getVersion(), routes);
    }

    public ApiDeclaration getApiDeclaration(String resource) {
        List<Route> routes = provider.getRoutes().stream()
            .filter(r -> r.getResourcePath().equals(resource))
            .collect(Collectors.toList());

        return ApiDeclaration.builder()
            .swaggerVersion("1.2")
            .apiVersion(provider.getVersion())
            .basePath("/")
            .resourcePath("/" + resource)
            .apis(converter.toApis(routes))
            .models(converter.toModels(routes))
            .build();
    }
}
