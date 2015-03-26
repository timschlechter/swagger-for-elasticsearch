package net.itimothy.elasticsearch.routes.indexroutes;

import net.itimothy.elasticsearch.routes.model.Route;
import org.elasticsearch.client.Client;
import net.itimothy.elasticsearch.routes.ModelsCatalog;
import net.itimothy.elasticsearch.routes.RoutesProvider;

import java.util.List;

import static java.util.Arrays.asList;

abstract class BaseIndexRoutes extends RoutesProvider {
    private final String indexOrAlias;

    public BaseIndexRoutes(Client client, ModelsCatalog modelsCatalog, String indexOrAlias) {
        super(client, modelsCatalog);
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
