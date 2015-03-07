package net.itimothy.elasticsearch.description.index;

import net.itimothy.rest.description.Info;
import org.elasticsearch.client.Client;
import net.itimothy.elasticsearch.description.CompositeRoutesProvider;
import net.itimothy.elasticsearch.description.ModelsCatalog;
import net.itimothy.elasticsearch.description.RoutesProvider;

import java.util.Arrays;
import java.util.List;

public class IndexRoutesProvider extends CompositeRoutesProvider {
    private final List<RoutesProvider> routeProviders;
    private final String indexOrAlias;

    public IndexRoutesProvider(Client client, ModelsCatalog modelsCatalog, String indexOrAlias) {
        super("Index specific routes", client, modelsCatalog);

        this.indexOrAlias = indexOrAlias;

        this.routeProviders = Arrays.<RoutesProvider>asList(
            new SearchRoutes(client, getModelsCatalog(), indexOrAlias),
            new MappingRoutes(client, getModelsCatalog(), indexOrAlias), 
            new DocumentRoutes(client, getModelsCatalog(), indexOrAlias)
        );
    }

    @Override
    protected List<RoutesProvider> getRoutesProviders() {
        return routeProviders;
    }

    @Override
    public Info getInfo() {
        Info info = super.getInfo();
        info.setTitle(info.getTitle() + " for index/alias " + indexOrAlias);
        return info;
    }
}
