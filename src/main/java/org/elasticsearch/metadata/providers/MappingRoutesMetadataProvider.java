package org.elasticsearch.metadata.providers;

import org.elasticsearch.client.Client;
import org.elasticsearch.metadata.*;

import java.util.List;

import static java.util.Arrays.asList;

class MappingRoutesMetadataProvider extends RoutesMetadataProvider {
    public MappingRoutesMetadataProvider(String defaultGroup, String version, Client client, ModelsCatalog modelsCatalog, ParametersFactory parametersFactory, DataProvider dataProvider) {
        super(defaultGroup, version, client, modelsCatalog, parametersFactory, dataProvider);
    }

    @Override
    public List<Route> getRoutesInternal() {
        return asList(
            Route.builder()
                .method(HttpMethod.GET)
                .apiPath("_mapping")
                .name("getMapping")
                .model(ModelsCatalog.MAPPING).build(),
            
            Route.builder()
                .method(HttpMethod.PUT)
                .parameters(asList(
                    Parameter.builder()
                        .paramType(ParameterType.PATH)
                        .model(Primitive.STRING)
                        .name("index").build(),
                    Parameter.builder()
                        .paramType(ParameterType.PATH)
                        .model(Primitive.STRING)
                        .name("type").build(),
                    Parameter.builder()
                        .paramType(ParameterType.BODY)
                        .model(ModelsCatalog.MAPPING).build()
                ))
                .apiPath("{index}/_mapping/{type}")
                .name("putMapping")
                .model(ModelsCatalog.MAPPING).build()
        );
    }
}