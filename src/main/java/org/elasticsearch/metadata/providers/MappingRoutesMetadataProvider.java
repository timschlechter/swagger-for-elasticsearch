package org.elasticsearch.metadata.providers;

import org.elasticsearch.metadata.*;

import java.util.List;

import static java.util.Arrays.asList;

class MappingRoutesMetadataProvider extends RoutesMetadataProvider {
    public MappingRoutesMetadataProvider(String defaultGroup, ModelsCatalog modelsCatalog, ParametersFactory parametersFactory, DataProvider dataProvider) {
        super(defaultGroup, modelsCatalog, parametersFactory, dataProvider);
    }

    @Override
    public List<Route> getRoutesInternal() {
        return asList(
            Route.builder()
                .method(HttpMethod.GET)
                .apiPath("_mapping")
                .model(ModelsCatalog.INDEX_MAPPINGS).build(),

            Route.builder()
                .method(HttpMethod.GET)
                .apiPath("{type}/_mapping")
                .parameters(asList(
                    buildIndexAliasOrWildcardExpressionsPathParam("index").build()
                ))
                .model(ModelsCatalog.INDEX_MAPPINGS).build(),

            Route.builder()
                .method(HttpMethod.GET)
                .apiPath("_mapping/{type}")
                .parameters(asList(
                    builDocumentTypePathParam("type").build()
                ))
                .model(ModelsCatalog.INDEX_MAPPINGS).build(),

            Route.builder()
                .method(HttpMethod.GET)
                .apiPath("{index}/_mapping/{type}")
                .parameters(asList(
                    buildIndexAliasOrWildcardExpressionsPathParam("index").build(),
                    builDocumentTypePathParam("type").build()
                ))
                .model(ModelsCatalog.INDEX_MAPPINGS).build(),
            
            Route.builder()
                .method(HttpMethod.PUT)
                .parameters(asList(
                    buildIndexAliasOrWildcardExpressionsPathParam("index").build(),
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