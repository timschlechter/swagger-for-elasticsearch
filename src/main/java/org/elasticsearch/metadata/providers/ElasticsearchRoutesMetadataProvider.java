package org.elasticsearch.metadata.providers;

import org.elasticsearch.client.Client;
import org.elasticsearch.metadata.Route;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

/**
 * Provides Elasticsearch's API metadata in a generic format
 */
public class ElasticsearchRoutesMetadataProvider extends RoutesMetadataProvider {

    private final List<RoutesMetadataProvider> metadataProviders;

    public ElasticsearchRoutesMetadataProvider(String version, Client client) {
        this(version, client, new DataProvider(client));
    }
    
    public ElasticsearchRoutesMetadataProvider(String version, Client client, DataProvider dataProvider) {
        super("Elasticsearch", version, client, 
            new ModelsCatalog(version, client), 
            new ParametersFactory(version, client, dataProvider),
            dataProvider
        );

        this.metadataProviders = asList(
            new IndexRoutesMetadataProvider("Index", getVersion(), getClient(), getModelsCatalog(), getParametersFactory(), dataProvider),
            new MappingRoutesMetadataProvider("Mapping", getVersion(), getClient(), getModelsCatalog(), getParametersFactory(), dataProvider)
        );
    }

    @Override
    public List<Route> getRoutesInternal() {
        return metadataProviders.stream()
            .flatMap(p -> p.getRoutes().stream())
            .collect(Collectors.toList());
    }
}
