package org.elasticsearch.routes.root;

import org.elasticsearch.client.Client;
import org.elasticsearch.routes.CompositeRoutesProvider;
import org.elasticsearch.routes.ModelsCatalog;
import org.elasticsearch.routes.RoutesProvider;

import java.util.List;

import static java.util.Arrays.asList;

public class RootRoutesProvider extends CompositeRoutesProvider {

    private final List<RoutesProvider> routeProviders;

    public RootRoutesProvider(Client client, ModelsCatalog modelsCatalog) {
        super("Root", client, modelsCatalog);

        this.routeProviders = asList(
            new IndexRoutes(getModelsCatalog(), client),
            new MappingRoutes(getModelsCatalog(), client),
            new AliasRoutes(getModelsCatalog(), client),
            new SearchRoutes(getModelsCatalog(), client)
        );
    }

    @Override
    protected List<RoutesProvider> getRoutesProviders() {
        return routeProviders;
    }
}
