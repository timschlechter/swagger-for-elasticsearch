package net.itimothy.elasticsearch.description.root;

import net.itimothy.elasticsearch.description.ModelsCatalog;
import net.itimothy.elasticsearch.description.restApiSpec.OfficialRestApiSpecRoutesProvider;
import net.itimothy.elasticsearch.description.RoutesProvider;
import net.itimothy.rest.description.Model;
import net.itimothy.rest.description.ParamType;
import net.itimothy.rest.description.Parameter;
import net.itimothy.rest.description.Route;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

public class RootRoutesProvider extends RoutesProvider{
    public RootRoutesProvider(Client client, ModelsCatalog modelsCatalog) {
        super(null, client, modelsCatalog);
    }

    @Override
    protected List<Route> getRoutesInternal() {
        List<Route> routes = new OfficialRestApiSpecRoutesProvider(getClient()).getRoutes();

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

    Map<String, Model> bodyModels = new ImmutableMap.Builder<String, Model>()
        .put("_mapping/{type}", ModelsCatalog.INDEX_MAPPINGS)
        .build();
}
