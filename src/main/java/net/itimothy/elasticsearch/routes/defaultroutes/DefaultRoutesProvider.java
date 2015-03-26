package net.itimothy.elasticsearch.routes.defaultroutes;

import net.itimothy.elasticsearch.routes.ModelsCatalog;
import net.itimothy.elasticsearch.routes.RoutesProvider;
import net.itimothy.elasticsearch.routes.model.Model;
import net.itimothy.elasticsearch.routes.model.ParamType;
import net.itimothy.elasticsearch.routes.model.Parameter;
import net.itimothy.elasticsearch.routes.model.Route;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.collect.ImmutableMap;
import org.elasticsearch.common.inject.Inject;

import java.util.List;
import java.util.Map;

public class DefaultRoutesProvider extends RoutesProvider {

    private final OfficialRestApiSpecRoutesProvider officialRestApiSpecRoutesProvider;
    Map<String, Model> bodyModels = new ImmutableMap.Builder<String, Model>()
        .put("_mapping/{type}", ModelsCatalog.INDEX_MAPPINGS)
        .build();

    @Inject
    public DefaultRoutesProvider(Client client, ModelsCatalog modelsCatalog, OfficialRestApiSpecRoutesProvider officialRestApiSpecRoutesProvider) {
        super(client, modelsCatalog);

        this.officialRestApiSpecRoutesProvider = officialRestApiSpecRoutesProvider;
    }

    @Override
    protected List<Route> getRoutesInternal() {
        List<Route> routes = this.officialRestApiSpecRoutesProvider.getRoutes();

        for (Route route : routes) {
            for (Parameter parameter : route.getParameters()) {
                if (parameter.getParamType().equals(ParamType.BODY)) {
                    if (bodyModels.containsKey(route.getApiPath())) {
                        parameter.setModel(bodyModels.get(route.getApiPath()));
                    }
                }
            }
        }

        return routes;
    }
}
