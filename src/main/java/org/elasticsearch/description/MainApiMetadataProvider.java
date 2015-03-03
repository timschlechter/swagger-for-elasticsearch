package org.elasticsearch.description;

import org.elasticsearch.client.Client;
import net.itimothy.rest.description.Route;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

/**
 * Provides Elasticsearch's API metadata in a generic format
 */
public class MainApiMetadataProvider extends ElasticSearchMetadataProvider {

    private final List<ElasticSearchMetadataProvider> metadataProviders;

    public MainApiMetadataProvider(Client client, String indexOrAlias) {
        this(client, new ModelsCatalog(client, indexOrAlias), indexOrAlias);
    }
    
    public MainApiMetadataProvider(Client client, ModelsCatalog modelsCatalog, String indexOrAlias) {
        super("Elasticsearch",
            client, modelsCatalog,
            indexOrAlias
        );

        this.metadataProviders = asList(
            new IndexApiMetadataProvider(getModelsCatalog(), client, indexOrAlias),
            new MappingApiMetadataProvider(getModelsCatalog(), client, indexOrAlias),
            new AliasApiMetadataProvider(getModelsCatalog(), client, indexOrAlias),
            new SearchApiMetadataProvider(getModelsCatalog(), client, indexOrAlias),
            new DocumentApiMetadataProvider(getModelsCatalog(), client, indexOrAlias)
        );
    }

    @Override
    public List<Route> getRoutesInternal() {
        return metadataProviders.stream()
            .flatMap(p -> p.getRoutes().stream())
            .collect(Collectors.toList());
    }
}
