package net.itimothy.elasticsearch.description.root._old;

import org.elasticsearch.client.Client;
import net.itimothy.elasticsearch.description.CompositeRoutesProvider;
import net.itimothy.elasticsearch.description.ModelsCatalog;
import net.itimothy.elasticsearch.description.RoutesProvider;

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
            new IndexSettingsRoutes(getModelsCatalog(), client),
            new SearchRoutes(getModelsCatalog(), client)
        );
    }

    @Override
    protected List<RoutesProvider> getRoutesProviders() {
        return routeProviders;
    }
}
