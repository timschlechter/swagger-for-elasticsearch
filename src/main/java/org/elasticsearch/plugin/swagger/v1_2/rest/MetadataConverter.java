package org.elasticsearch.plugin.swagger.v1_2.rest;

import net.itimothy.rest.metadata.model.Route;
import org.elasticsearch.common.lang3.StringUtils;
import org.elasticsearch.plugin.swagger.v1_2.model.Api;
import org.elasticsearch.plugin.swagger.v1_2.model.HttpMethod;
import org.elasticsearch.plugin.swagger.v1_2.model.Operation;

import java.util.List;
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
            .type(route.getModel().getId())
            .method(convert(route.getMethod()))
            .nickname(toNickname(route))
            .build();
    }
    
    private String toNickname(Route route) {
        return StringUtils.isBlank(route.getName())
            ? toCamelCase(String.format("%s %s", route.getMethod().name().toLowerCase(), route.getApiPath()))
            : route.getName();        
    }

    private static String toCamelCase(final String value) {
        return StringUtils.capitalize(value);
    }
}
