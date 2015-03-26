package net.itimothy.elasticsearch.routes.indexroutes;

import net.itimothy.elasticsearch.routes.model.HttpMethod;
import net.itimothy.elasticsearch.routes.model.ParamType;
import net.itimothy.elasticsearch.routes.model.Parameter;
import net.itimothy.elasticsearch.routes.model.Route;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.collect.ImmutableList;
import net.itimothy.elasticsearch.routes.ModelsCatalog;

import java.util.List;

import static java.util.Arrays.asList;

class SearchRoutes extends BaseIndexRoutes {
    public SearchRoutes(Client client, ModelsCatalog modelsCatalog, String indexOrAlias) {
        super(client, modelsCatalog, indexOrAlias);
    }

    @Override
    protected String getDefaultGroup() {
        return "Search APIs";
    }

    @Override
    public List<Route> getRoutesInternal() {
        return asList(
            Route.builder()
                .method(HttpMethod.GET)
                .apiPath(indexOrAliasPrepended("_search"))
                .description("Search in the index/alias using a URI by providing request parameters")
                .parameters(
                    new ImmutableList.Builder<Parameter>()
                        .addAll(defaultUriSearchParams())
                        .build()
                )
                .model(ModelsCatalog.SEARCH_RESULT).build(),

            Route.builder()
                .method(HttpMethod.GET)
                .apiPath(indexOrAliasPrepended("{type}/_search"))
                .description("Search in the index/alias and a specific type using a URI by providing request parameters")
                .parameters(
                    new ImmutableList.Builder<Parameter>()
                        .add(typeSelectParam("type", ParamType.PATH).build())
                        .addAll(defaultUriSearchParams())
                        .build()
                )
                .model(ModelsCatalog.SEARCH_RESULT).build()
        );
    }
}
