package net.itimothy.elasticsearch.description.root._old;

import net.itimothy.rest.description.HttpMethod;
import net.itimothy.rest.description.ParamType;
import net.itimothy.rest.description.Response;
import net.itimothy.rest.description.Route;
import org.elasticsearch.client.Client;
import net.itimothy.elasticsearch.description.ModelsCatalog;
import net.itimothy.elasticsearch.description.RoutesProvider;

import java.util.List;

import static java.util.Arrays.asList;

class IndexRoutes extends RoutesProvider {
    public IndexRoutes(ModelsCatalog modelsCatalog, Client client) {
        super("Index management", client, modelsCatalog);
    }

    @Override
    public List<Route> getRoutesInternal() {
        return asList(
            Route.builder()
                .method(HttpMethod.GET)
                .apiPath("{index}")
                .description("Retrieve information about one or more indexes")
                .notes("<p>The following example gets the information for an index called twitter. Specifying an index, alias or wildcard expression is required.</p>" +
                    "<pre>" +
                    "curl -XGET 'http://localhost:9200/twitter/'\n" +
                    "</pre>" +
                    "<p>The get index API can also be applied to more than one index, or on all indices by using <code>_all</code> or <code>*</code> as index.</p>")
                .model(ModelsCatalog.INDEX_FEATURES)
                .parameters(
                    indexOrAliasSelectParam("index", ParamType.PATH).build()
                ).build(),

            Route.builder()
                .method(HttpMethod.GET)
                .apiPath("{index}/{features}")
                .description("Retrieve information about one or more indexes")
                .notes("<p>The information returned by the get API can be filtered to include only specific features by specifying a comma delimited list of features in the URL:</p>" +
                    "<pre>" +
                    "curl -XGET 'http://localhost:9200/twitter/_settings,_mappings'" +
                    "</pre>" +
                    "<p>The above command will only return the settings and mappings for the index called twitter. The available features are <code>_settings</code>, <code>_mappings</code>, <code>_warmers</code> and <code>_aliases</code>.")
                .parameters(
                    indexOrAliasSelectParam("index", ParamType.PATH).build(),
                    enumParam("features", ParamType.PATH, "_settings", "_mappings", "_warmers", "_aliases")
                        .allowMultiple(true).build()
                )
                .model(ModelsCatalog.INDEX_FEATURES).build(),

            Route.builder()
                .method(HttpMethod.DELETE)
                .apiPath("{index}")
                .description("Deletes an existing index")
                .notes("<p>The delete index API allows to delete an existing index. The delete index API can also be applied to more than one index, or on all indices <strong>(be careful!)</strong> by using <code>_all</code> or <code>*</code> as index.</p>" +
                    "<p>In order to disable allowing to delete indices via wildcards or <code>_all</code>, set <code>action.destructive_requires_name</code> setting in the config to <code>true</code>. This setting can also be changed via the cluster update settings api.</p>")
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
