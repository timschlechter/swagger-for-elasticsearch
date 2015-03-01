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

    public MainApiMetadataProvider(Client client) {
        this(new DataProvider(client), null);
    }


    public MainApiMetadataProvider(Client client, String indexOrAlias) {
        this(new DataProvider(client), indexOrAlias);
    }

    private MainApiMetadataProvider(DataProvider dataProvider, String indexOrAlias) {
        this(dataProvider, new ModelsCatalog(dataProvider, indexOrAlias), indexOrAlias);
    }
    
    public MainApiMetadataProvider(DataProvider dataProvider, ModelsCatalog modelsCatalog, String indexOrAlias) {
        super("Elasticsearch",
            modelsCatalog,
            new ParametersFactory(dataProvider, modelsCatalog),
            dataProvider,
            indexOrAlias
        );

        this.metadataProviders = asList(
            new IndexApiMetadataProvider(getModelsCatalog(), getParametersFactory(), dataProvider, indexOrAlias),
            new MappingApiMetadataProvider(getModelsCatalog(), getParametersFactory(), dataProvider, indexOrAlias),
            new AliasApiMetadataProvider(getModelsCatalog(), getParametersFactory(), dataProvider, indexOrAlias),
            new DocumentApiMetadataProvider(getModelsCatalog(), getParametersFactory(), dataProvider, indexOrAlias)
        );
    }


    @Override
    public List<Route> getRoutesInternal() {
        return metadataProviders.stream()
            .flatMap(p -> p.getRoutes().stream())
            .collect(Collectors.toList());
    }
}
