package org.elasticsearch.routes.root;

import net.itimothy.rest.description.Route;
import org.elasticsearch.client.Client;
import org.elasticsearch.routes.ModelsCatalog;
import org.elasticsearch.routes.RoutesProvider;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class RootRoutesProvider extends RoutesProvider {

    private final List<RoutesProvider> metadataProviders;

    public RootRoutesProvider(Client client, ModelsCatalog modelsCatalog) {
        super("Root", client, modelsCatalog);

        this.metadataProviders = asList(
            new SearchRoutes(getModelsCatalog(), client),
            new IndexRoutes(getModelsCatalog(), client),
            new MappingRoutes(getModelsCatalog(), client),
            new AliasRoutes(getModelsCatalog(), client)
        );
    }

    @Override
    public List<Route> getRoutesInternal() {
        return metadataProviders.stream()
            .flatMap(p -> p.getRoutes().stream())
            .collect(Collectors.toList());
    }
}
