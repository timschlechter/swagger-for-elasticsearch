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

    public ElasticsearchRoutesMetadataProvider(Client client) {
        this(new DataProvider(client));
    }
    
    public ElasticsearchRoutesMetadataProvider(DataProvider dataProvider) {
        super("Elasticsearch",
            new ModelsCatalog(dataProvider),
            new ParametersFactory(dataProvider),
            dataProvider
        );

        this.metadataProviders = asList(
            new IndexRoutesMetadataProvider("Index", getModelsCatalog(), getParametersFactory(), dataProvider),
            new MappingRoutesMetadataProvider("Mapping", getModelsCatalog(), getParametersFactory(), dataProvider)
        );
    }

    @Override
    public List<Route> getRoutesInternal() {
        return metadataProviders.stream()
            .flatMap(p -> p.getRoutes().stream())
            .collect(Collectors.toList());
    }
}
