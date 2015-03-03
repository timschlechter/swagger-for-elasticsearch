package org.elasticsearch.description;

import net.itimothy.rest.description.HttpMethod;
import net.itimothy.rest.description.ParamType;
import net.itimothy.rest.description.Parameter;
import net.itimothy.rest.description.Route;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class SearchApiMetadataProvider extends ElasticSearchMetadataProvider {
    public SearchApiMetadataProvider(ModelsCatalog modelsCatalog, Client client, String indexOrAlias) {
        super("Search APIs", client, modelsCatalog, indexOrAlias);
    }

    @Override
    public List<Route> getRoutesInternal() {
        List<Route> result = new ArrayList<>();

        if (getIndexOrAlias() == null) {
            result.addAll(asList(
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

            ));
        } else {
            result.addAll(asList(
                Route.builder()
                    .method(HttpMethod.GET)
                    .apiPath(indexOrAliasPrepended("_search"))
                    .description("Search in the index/alias using a URI by providing request parameters")
                    .parameters(
                        new ImmutableList.Builder<Parameter>()
                            .addAll(defaultUriSearchParams())
                            .build()
                    )
                    .model(getModelsCatalog().SEARCH_RESULT).build(),

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
                    .model(getModelsCatalog().SEARCH_RESULT).build()
            ));
        }
        return result;
    }
}
