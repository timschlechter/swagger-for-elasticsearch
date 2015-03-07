package net.itimothy.elasticsearch.description.root;

import net.itimothy.rest.description.HttpMethod;
import net.itimothy.rest.description.ParamType;
import net.itimothy.rest.description.Parameter;
import net.itimothy.rest.description.Route;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.collect.ImmutableList;
import net.itimothy.elasticsearch.description.ModelsCatalog;
import net.itimothy.elasticsearch.description.RoutesProvider;

import java.util.List;

import static java.util.Arrays.asList;

class SearchRoutes extends RoutesProvider {
    public SearchRoutes(ModelsCatalog modelsCatalog, Client client) {
        super("Search", client, modelsCatalog);
    }

    @Override
    public List<Route> getRoutesInternal() {
        return asList(
            Route.builder()
                .method(HttpMethod.GET)
                .apiPath("_search")
                .description("Search in all indices and type using a URI by providing request parameters")
                .parameters(defaultUriSearchParams())
                .model(ModelsCatalog.SEARCH_RESULT).build(),

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
                .model(ModelsCatalog.SEARCH_RESULT).build(),

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
                .model(ModelsCatalog.SEARCH_RESULT).build()
        );
    }
}
