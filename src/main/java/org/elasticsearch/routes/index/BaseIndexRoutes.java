package org.elasticsearch.routes.index;

import net.itimothy.rest.description.Route;
import org.elasticsearch.client.Client;
import org.elasticsearch.routes.ModelsCatalog;
import org.elasticsearch.routes.RoutesProvider;

import java.util.List;

import static java.util.Arrays.asList;

abstract class BaseIndexRoutes extends RoutesProvider {
    private final String indexOrAlias;

    public BaseIndexRoutes(String defaultGroup, Client client, ModelsCatalog modelsCatalog, String indexOrAlias) {
        super(defaultGroup, client, modelsCatalog);
        this.indexOrAlias = indexOrAlias;
    }

    public String getIndexOrAlias() {
        return indexOrAlias;
    }

    @Override
    public List<Route> getRoutes() {
        if (getIndexOrAlias() == null) {
            return asList();
        }

        return super.getRoutes();
    }

    protected String indexOrAliasPrepended(String apiPath) {
        return getIndexOrAlias() != null ? getIndexOrAlias() + "/" + apiPath : apiPath;
    }
}
