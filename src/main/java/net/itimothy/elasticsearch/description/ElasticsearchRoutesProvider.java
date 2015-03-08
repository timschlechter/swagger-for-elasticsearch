package net.itimothy.elasticsearch.description;

import net.itimothy.elasticsearch.description.index.IndexRoutesProvider;
import net.itimothy.elasticsearch.description.root.RootRoutesProvider;
import net.itimothy.rest.description.Route;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.lang3.StringUtils;

import java.util.List;

/**
 * Provides Elasticsearch API metadata in a generic format
 */
public class ElasticsearchRoutesProvider extends RoutesProvider {

    private final RoutesProvider routesProvider;

    public ElasticsearchRoutesProvider(Client client, String indexOrAlias) {
        this(client, new ModelsCatalog(client), indexOrAlias);
    }

    public ElasticsearchRoutesProvider(Client client, ModelsCatalog modelsCatalog, String indexOrAlias) {
        super("Elasticsearch", client, modelsCatalog);

        routesProvider = StringUtils.isBlank(indexOrAlias)
            ? new RootRoutesProvider(client, getModelsCatalog())
            : new IndexRoutesProvider(client, getModelsCatalog(), indexOrAlias);
    }

    @Override
    protected List<Route> getRoutesInternal() {
        return routesProvider.getRoutes();
    }
}
