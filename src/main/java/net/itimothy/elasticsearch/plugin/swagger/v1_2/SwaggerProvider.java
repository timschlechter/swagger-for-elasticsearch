package net.itimothy.elasticsearch.plugin.swagger.v1_2;

import net.itimothy.elasticsearch.routes.model.Info;
import net.itimothy.elasticsearch.routes.model.Route;
import net.itimothy.elasticsearch.plugin.swagger.v1_2.model.apiDeclaration.ApiDeclaration;
import net.itimothy.elasticsearch.plugin.swagger.v1_2.model.resourceListing.ResourceListing;

import java.util.ArrayList;
import java.util.List;

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
        List<Route> routes = new ArrayList<>();
        for (Route route : this.routes) {
            if (route.getGroup().equals(resource)) {
                routes.add(route);
            }
        }

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
