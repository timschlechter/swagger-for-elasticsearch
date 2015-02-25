package org.elasticsearch.plugin.swagger;

import net.itimothy.rest.metadata.MetadataProvider;
import net.itimothy.rest.metadata.model.*;

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
                .resourcePath("Indices")
                .method(HttpMethod.PUT)
                .apiPath("{index}")
                .name("createIndex")
                .parameters(asList(
                    Parameter.builder()
                        .paramType(ParameterType.PATH)
                        .model(Primitive.STRING)
                        .name("index")
                        .build()
                ))
                .build(),


            Route.builder()
                .resourcePath("Mappings")
                .method(HttpMethod.GET)
                .apiPath("_mapping")
                .name("getMapping")
                .model(ModelManager.MAPPING)
                .build(),

            Route.builder()
                .resourcePath("Mappings")
                .method(HttpMethod.PUT)
                .parameters(asList(
                    Parameter.builder()
                        .paramType(ParameterType.PATH)
                        .model(Primitive.STRING)
                        .name("index")
                        .build(),
                    Parameter.builder()
                        .paramType(ParameterType.PATH)
                        .model(Primitive.STRING)
                        .name("type")
                        .build(),
                    Parameter.builder()
                        .paramType(ParameterType.BODY)
                        .model(ModelManager.MAPPING)
                        .build()
                ))
                .apiPath("{index}/_mapping/{type}")
                .name("putMapping")
                .model(ModelManager.MAPPING)
                .build()
        );
    }
}
