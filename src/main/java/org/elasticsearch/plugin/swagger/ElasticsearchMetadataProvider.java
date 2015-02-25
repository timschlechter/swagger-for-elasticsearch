package org.elasticsearch.plugin.swagger;

import net.itimothy.rest.metadata.model.HttpMethod;
import net.itimothy.rest.metadata.MetadataProvider;
import net.itimothy.rest.metadata.model.Parameter;
import net.itimothy.rest.metadata.model.ParameterType;
import net.itimothy.rest.metadata.model.Route;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Provides Elasticsearch's API metadata in a generic format
 */
public class ElasticsearchMetadataProvider implements MetadataProvider
{
    ModelManager models = new ModelManager();

    @Override
    public String getVersion() {
        return "1.4";
    }

    @Override
    public List<Route> getRoutes() {
        return asList(
            Route.builder()
                .resourcePath("Mapping")
                .method(HttpMethod.GET)
                .apiPath("_mapping")
                .name("getMapping")
                .model(ModelManager.MAPPING)
                .build(),

            Route.builder()
                .resourcePath("Mapping")
                .method(HttpMethod.PUT)
                .parameters(asList(
                    Parameter.builder()
                        .paramType(ParameterType.BODY)
                        .model(ModelManager.MAPPING)
                        .build()
                ))
                .apiPath("_mapping")
                .name("putMapping")
                .model(ModelManager.MAPPING)
                .build()
        );
    }
}
