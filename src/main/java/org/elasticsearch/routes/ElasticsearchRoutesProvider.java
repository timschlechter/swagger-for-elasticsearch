package org.elasticsearch.routes;

import net.itimothy.rest.description.Route;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.lang3.StringUtils;
import org.elasticsearch.routes.index.IndexRoutesProvider;
import org.elasticsearch.routes.root.RootRoutesProvider;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

/**
 * Provides Elasticsearch's API metadata in a generic format
 */
public class ElasticsearchRoutesProvider extends RoutesProvider {

    private final List<RoutesProvider> metadataProviders;

    public ElasticsearchRoutesProvider(Client client, String indexOrAlias) {
        this(client, new ModelsCatalog(client, indexOrAlias), indexOrAlias);
    }

    public ElasticsearchRoutesProvider(Client client, ModelsCatalog modelsCatalog, String indexOrAlias) {
        super("Elasticsearch", client, modelsCatalog);

        if (StringUtils.isBlank(indexOrAlias)) {
            // Root route providers
            this.metadataProviders = asList(new RootRoutesProvider(client, getModelsCatalog()));
        } else {
            // Index route providers
            this.metadataProviders = asList(new IndexRoutesProvider(client, getModelsCatalog(), indexOrAlias));
        }
    }

    @Override
    public List<Route> getRoutesInternal() {
        return metadataProviders.stream()
            .flatMap(p -> p.getRoutes().stream())
            .collect(Collectors.toList());
    }
}
