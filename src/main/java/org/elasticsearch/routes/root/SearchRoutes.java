package org.elasticsearch.routes.root;

import net.itimothy.rest.description.HttpMethod;
import net.itimothy.rest.description.ParamType;
import net.itimothy.rest.description.Parameter;
import net.itimothy.rest.description.Route;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.collect.ImmutableList;
import org.elasticsearch.routes.ModelsCatalog;
import org.elasticsearch.routes.RoutesProvider;

import java.util.List;

import static java.util.Arrays.asList;

class SearchRoutes extends RoutesProvider {
    public SearchRoutes(ModelsCatalog modelsCatalog, Client client) {
        super("Search APIs", client, modelsCatalog);
    }

    @Override
    public List<Route> getRoutesInternal() {
        return asList(
            Route.builder()
                .method(HttpMethod.GET)
                .apiPath("_search")
                .description("Search in all indices and type using a URI by providing request parameters")
                .parameters(defaultUriSearchParams())
                .model(getModelsCatalog().SEARCH_RESULT).build(),

            Route.builder()
                .method(HttpMethod.GET)
                .apiPath("{index}/_search")
                .description("Search in a specific index/alias using a URI by providing request parameters")
                .parameters(
                    new ImmutableList.Builder<Parameter>()
                        .add(indexOrAliasSelectParam("index", ParamType.PATH).build())
                        .addAll(defaultUriSearchParams())
                        .build()
                )
                .model(getModelsCatalog().SEARCH_RESULT).build(),

            Route.builder()
                .method(HttpMethod.GET)
                .apiPath("{index}/{type}/_search")
                .description("Search in a specific index/alias and type using a URI by providing request parameters")
                .parameters(
                    new ImmutableList.Builder<Parameter>()
                        .add(indexOrAliasSelectParam("index", ParamType.PATH).build())
                        .add(typeSelectParam("type", ParamType.PATH).build())
                        .addAll(defaultUriSearchParams())
                        .build()
                )
                .model(getModelsCatalog().SEARCH_RESULT).build()
        );
    }
}
