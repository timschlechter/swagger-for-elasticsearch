package org.elasticsearch.description;

import net.itimothy.rest.description.HttpMethod;
import net.itimothy.rest.description.ParamType;
import net.itimothy.rest.description.Response;
import net.itimothy.rest.description.Route;
import org.elasticsearch.client.Client;

import java.util.List;

import static java.util.Arrays.asList;

class IndexApiMetadataProvider extends ElasticSearchMetadataProvider {
    public IndexApiMetadataProvider(ModelsCatalog modelsCatalog, Client client, String indexOrAlias) {
        super("Index APIs", client, modelsCatalog, indexOrAlias);
    }

    @Override
    public List<Route> getRoutesInternal() {
        if (getIndexOrAlias() != null) {
            return asList();
        }

        return asList(
            Route.builder()
                .method(HttpMethod.GET)
                .apiPath("{index}")
                .model(ModelsCatalog.INDEX_FEATURES)
                .parameters(
                    indexOrAliasSelectParam("index", ParamType.PATH).build()
                ).build(),

            Route.builder()
                .method(HttpMethod.DELETE)
                .apiPath("{index}")
                .description("Deletes an existing index")
                .notes("The delete index API allows to delete an existing index. The delete index API can also be applied to more than one index, or on all indices (be careful!) by using _all or * as index.\n" +
                    "\n" +
                    "In order to disable allowing to delete indices via wildcards or _all, set action.destructive_requires_name setting in the config to true. This setting can also be changed via the cluster update settings api.")
                .parameters(
                    indexOrAliasSelectParam("index", ParamType.PATH).build()
                ).build(),

            Route.builder()
                .method(HttpMethod.HEAD)
                .apiPath("{index}")
                .description("Used to check if the index (indices) exists or not")
                .responses(asList(
                    Response.builder().code(200).message("Index exists").build(),
                    Response.builder().code(404).message("Index does not exist").build()
                ))
                .parameters(
                    indexParam("index", ParamType.PATH).build()
                ).build(),

            Route.builder()
                .method(HttpMethod.GET)
                .apiPath("{index}/{features}")
                .parameters(
                    indexOrAliasSelectParam("index", ParamType.PATH).build(),
                    enumParam("features", ParamType.PATH, "_settings", "_mappings", "_warmers", "_aliases")
                        .allowMultiple(true).build()
                )
                .model(ModelsCatalog.INDEX_FEATURES)
                .build(),

            Route.builder()
                .method(HttpMethod.PUT)
                .apiPath("{index}")
                .description("Creates a new index")
                .notes("The create index API allows to instantiate an index. Elasticsearch provides support for isCollection indices, including executing operations across several indices.")
                .parameters(
                    indexParam("index", ParamType.PATH).build()
                ).build(),

            Route.builder()
                .method(HttpMethod.POST)
                .apiPath("{index}/_open")
                .description("Opens an index")
                .notes("The open and close index APIs allow to close an index, and later on opening it. A closed index has almost no overhead on the cluster (except for maintaining its metadata), and is blocked for read/write operations. A closed index can be opened which will then go through the normal recovery process.")
                .parameters(
                    indexOrAliasSelectParam("index", ParamType.PATH).build()
                ).build(),

            Route.builder()
                .method(HttpMethod.POST)
                .apiPath("{index}/_close")
                .description("Closes an index")
                .notes("The open and close index APIs allow to close an index, and later on opening it. A closed index has almost no overhead on the cluster (except for maintaining its metadata), and is blocked for read/write operations. A closed index can be opened which will then go through the normal recovery process.")
                .parameters(
                    indexOrAliasSelectParam("index", ParamType.PATH).build()
                ).build()
        );
    }
}
