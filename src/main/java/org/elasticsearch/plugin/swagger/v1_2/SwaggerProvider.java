package org.elasticsearch.plugin.swagger.v1_2;

import net.itimothy.rest.description.Info;
import net.itimothy.rest.description.Route;
import org.elasticsearch.plugin.swagger.v1_2.model.apiDeclaration.ApiDeclaration;
import org.elasticsearch.plugin.swagger.v1_2.model.resourceListing.ResourceListing;

import java.util.List;
import java.util.stream.Collectors;

public class SwaggerProvider {

    private final Info info;
    private final List<Route> routes;
    SwaggerMetadataConverter metadataConverter = new SwaggerMetadataConverter();

    public SwaggerProvider(Info info, List<Route> routes) {
        this.info = info;
        this.routes = routes;
    }

    public ResourceListing getResourceListing() {
        return metadataConverter.toResourceListing(info, routes);
    }

    public ApiDeclaration getApiDeclaration(String resource) {
        List<Route> routes = this.routes.stream()
            .filter(r -> r.getGroup().equals(resource))
            .collect(Collectors.toList());

        return ApiDeclaration.builder()
            .swaggerVersion("1.2")
            .apiVersion(info.getVersion())
            .basePath("/")
            .resourcePath("/" + resource)
            .apis(metadataConverter.toApis(routes))
            .models(metadataConverter.toModels(routes))
            .build();
    }
}
