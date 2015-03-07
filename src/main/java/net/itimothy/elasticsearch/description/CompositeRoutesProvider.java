package net.itimothy.elasticsearch.description;

import net.itimothy.rest.description.Route;
import org.elasticsearch.client.Client;

import java.util.ArrayList;
import java.util.List;

public abstract class CompositeRoutesProvider extends RoutesProvider {
    protected CompositeRoutesProvider(String defaultGroup, Client client, ModelsCatalog modelsCatalog) {
        super(defaultGroup, client, modelsCatalog);
    }

    protected abstract List<RoutesProvider> getRoutesProviders();

    @Override
    public List<Route> getRoutesInternal() {
        List<Route> routes = new ArrayList<Route>();
        for (RoutesProvider provider : getRoutesProviders()) {
            routes.addAll(provider.getRoutes());
        }
        return routes;
    }
}
