package org.elasticsearch.routes.index;

import net.itimothy.rest.description.Info;
import net.itimothy.rest.description.Route;
import org.elasticsearch.client.Client;
import org.elasticsearch.routes.ModelsCatalog;
import org.elasticsearch.routes.RoutesProvider;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class IndexRoutesProvider extends BaseIndexRoutes {
    private final List<RoutesProvider> metadataProviders;

    public IndexRoutesProvider(Client client, ModelsCatalog modelsCatalog, String indexOrAlias) {
        super("Index specific routes", client, modelsCatalog, indexOrAlias);

        this.metadataProviders = asList(
            new SearchRoutes(client, getModelsCatalog(), indexOrAlias),
            new MappingRoutes(client, getModelsCatalog(), indexOrAlias),
            new DocumentRoutes(client, getModelsCatalog(), indexOrAlias)
        );
    }

    @Override
    protected List<Route> getRoutesInternal() {
        return metadataProviders.stream()
            .flatMap(p -> p.getRoutes().stream())
            .collect(Collectors.toList());
    }

    @Override
    public Info getInfo() {
        Info info = super.getInfo();
        info.setTitle(info.getTitle() + " for index/alias " + getIndexOrAlias());
        return info;
    }
}
