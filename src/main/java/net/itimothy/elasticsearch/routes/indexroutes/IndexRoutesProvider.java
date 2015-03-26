package net.itimothy.elasticsearch.routes.indexroutes;

import net.itimothy.elasticsearch.routes.model.Info;
import net.itimothy.elasticsearch.routes.model.Route;
import org.elasticsearch.client.Client;
import net.itimothy.elasticsearch.routes.ModelsCatalog;
import net.itimothy.elasticsearch.routes.RoutesProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IndexRoutesProvider extends RoutesProvider {
    private final List<RoutesProvider> routeProviders;
    private final String indexOrAlias;

    public IndexRoutesProvider(Client client, ModelsCatalog modelsCatalog, String indexOrAlias) {
        super(client, modelsCatalog);

        this.indexOrAlias = indexOrAlias;

        this.routeProviders = Arrays.<RoutesProvider>asList(
            new SearchRoutes(client, getModelsCatalog(), indexOrAlias),
            new MappingRoutes(client, getModelsCatalog(), indexOrAlias), 
            new DocumentRoutes(client, getModelsCatalog(), indexOrAlias)
        );
    }

    @Override
    public Info getInfo() {
        Info info = super.getInfo();
        info.setTitle(info.getTitle() + " for index/alias " + indexOrAlias);
        return info;
    }

    @Override
    public List<Route> getRoutesInternal() {
        List<Route> routes = new ArrayList<Route>();
        for (RoutesProvider provider : routeProviders) {
            routes.addAll(provider.getRoutes());
        }
        return routes;
    }
}
