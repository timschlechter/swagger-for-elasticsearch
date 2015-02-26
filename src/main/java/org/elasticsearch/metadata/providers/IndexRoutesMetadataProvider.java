package org.elasticsearch.metadata.providers;

import org.elasticsearch.client.Client;
import org.elasticsearch.metadata.HttpMethod;
import org.elasticsearch.metadata.Response;
import org.elasticsearch.metadata.Route;

import java.util.List;

import static java.util.Arrays.asList;

class IndexRoutesMetadataProvider extends RoutesMetadataProvider {
    public IndexRoutesMetadataProvider(String defaultGroup, String version, Client client, ModelsCatalog modelsCatalog, ParametersFactory parametersFactory, DataProvider dataProvider) {
        super(defaultGroup, version, client, modelsCatalog, parametersFactory, dataProvider);
    }

    @Override
    public List<Route> getRoutesInternal() {
        return asList(
            Route.builder()
                .method(HttpMethod.GET)
                .apiPath("{index}")
                .parameters(asList(
                    buildIndexAliasOrWildcardExpressionsPathParam("index").build()
                )).build(),

            Route.builder()
                .method(HttpMethod.DELETE)
                .apiPath("{index}")
                .parameters(asList(
                    buildIndexAliasOrWildcardExpressionsPathParam("index").build()
                )).build(),

            Route.builder()
                .method(HttpMethod.HEAD)
                .apiPath("{index}")
                .description("Used to check if the index (indices) exists or not")
                .responses(asList(
                    Response.builder().code(404).message("Index does not exist").build(),
                    Response.builder().code(200).message("Index exists").build()
                ))
                .parameters(asList(
                    buildIndexPathParam("index").build()
                )).build(),

            Route.builder()
                .method(HttpMethod.GET)
                .apiPath("{index}/{features}")
                .parameters(asList(
                    buildIndexAliasOrWildcardExpressionsPathParam("index").build(),
                    buildEnumPathParam("features", getFeatures())
                        .allowMultiple(true)
                        .build()
                )).build(),

            Route.builder()
                .method(HttpMethod.PUT)
                .apiPath("{index}")
                .parameters(asList(
                    buildIndexPathParam("index").build()
                )).build()
        );
    }
}
