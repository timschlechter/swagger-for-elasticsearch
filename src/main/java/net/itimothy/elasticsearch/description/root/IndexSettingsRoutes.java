package net.itimothy.elasticsearch.description.root;

import net.itimothy.elasticsearch.description.ModelsCatalog;
import net.itimothy.elasticsearch.description.RoutesProvider;
import net.itimothy.rest.description.HttpMethod;
import net.itimothy.rest.description.ParamType;
import net.itimothy.rest.description.Route;
import org.elasticsearch.client.Client;

import java.util.List;

import static java.util.Arrays.asList;

public class IndexSettingsRoutes extends RoutesProvider {
    public IndexSettingsRoutes(ModelsCatalog modelsCatalog, Client client) {
        super("Index settings", client, modelsCatalog);
    }

    @Override
    protected List<Route> getRoutesInternal() {
        return asList(
            Route.builder()
                .method(HttpMethod.GET)
                .apiPath("{index}/_settings")
                .description("Retrieve settings of index/indices")
                .notes("<h2>Multiple indices and types</h2><p>The get settings API can be used to get settings for more than one index with a single call. General usage of the API follows the following syntax: host:port/{index}/_settings where {index} can stand for comma-separated list of index names and aliases. To get settings for all indices you can use _all for {index}. Wildcard expressions are also supported. The following are some examples:</p>" +
                    "<pre>" +
                    "curl -XGET 'http://localhost:9200/twitter,kimchy/_settings'\n" +
                    "curl -XGET 'http://localhost:9200/_all/_settings'\n" +
                    "curl -XGET 'http://localhost:9200/2013-*/_settings'\n" +
                    "</pre>\n" +
                    "<h2>Prefix option</h2>\n" +
                    "<p>There is also support for a prefix query string option that allows to include only settings matches the specified prefix.</p>" +
                    "<pre>" +
                    "curl -XGET 'http://localhost:9200/my-index/_settings?prefix=index.'\n" +
                    "curl -XGET 'http://localhost:9200/_all/_settings?prefix=index.routing.allocation.'\n" +
                    "curl -XGET 'http://localhost:9200/2013-*/_settings?name=index.merge.*'\n" +
                    "curl -XGET 'http://localhost:9200/2013-*/_settings/index.merge.*'\n" +
                    "</pre>" +
                    "<p>The first example returns all index settings the start with index. in the index my-index, the second example gets all index settings that start with index.routing.allocation. for all indices, lastly the third example returns all index settings that start with index.merge. in indices that start with 2013-.</p>")
                .model(ModelsCatalog.INDEX_FEATURES)
                .parameters(
                    indexOrAliasSelectParam("index", ParamType.PATH).build(),
                    queryParam("prefix").description("Allows to include only settings matches the specified prefix").build()
                ).build(),

            Route.builder()
                .method(HttpMethod.PUT)
                .apiPath("{index}/_settings")
                .description("Retrieve settings of index/indices")
                .notes("<h2>Multiple indices and types</h2><p>The get settings API can be used to get settings for more than one index with a single call. General usage of the API follows the following syntax: host:port/{index}/_settings where {index} can stand for comma-separated list of index names and aliases. To get settings for all indices you can use _all for {index}. Wildcard expressions are also supported. The following are some examples:</p>" +
                    "<pre>" +
                    "curl -XGET 'http://localhost:9200/twitter,kimchy/_settings'\n" +
                    "curl -XGET 'http://localhost:9200/_all/_settings'\n" +
                    "curl -XGET 'http://localhost:9200/2013-*/_settings'\n" +
                    "</pre>\n" +
                    "<h2>Prefix option</h2>\n" +
                    "<p>There is also support for a prefix query string option that allows to include only settings matches the specified prefix.</p>" +
                    "<pre>" +
                    "curl -XGET 'http://localhost:9200/my-index/_settings?prefix=index.'\n" +
                    "curl -XGET 'http://localhost:9200/_all/_settings?prefix=index.routing.allocation.'\n" +
                    "curl -XGET 'http://localhost:9200/2013-*/_settings?name=index.merge.*'\n" +
                    "curl -XGET 'http://localhost:9200/2013-*/_settings/index.merge.*'\n" +
                    "</pre>" +
                    "<p>The first example returns all index settings the start with index. in the index my-index, the second example gets all index settings that start with index.routing.allocation. for all indices, lastly the third example returns all index settings that start with index.merge. in indices that start with 2013-.</p>")
                .model(ModelsCatalog.INDEX_FEATURES)
                .parameters(
                    indexOrAliasSelectParam("index", ParamType.PATH).build(),
                    queryParam("prefix").description("Allows to include only settings matches the specified prefix").build()
                ).build()
            
        );
    }
}