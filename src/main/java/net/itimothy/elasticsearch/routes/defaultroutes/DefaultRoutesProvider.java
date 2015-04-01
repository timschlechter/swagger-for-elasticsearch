package net.itimothy.elasticsearch.routes.defaultroutes;

import net.itimothy.elasticsearch.routes.ModelsCatalog;
import net.itimothy.elasticsearch.routes.RoutesProvider;
import net.itimothy.elasticsearch.routes.model.*;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.collect.ImmutableMap;
import org.elasticsearch.common.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DefaultRoutesProvider extends RoutesProvider {

    private final OfficialRestApiSpecRoutesProvider officialRestApiSpecRoutesProvider;
    Map<String, Model> bodyModels = new ImmutableMap.Builder<String, Model>()
        .put("_mapping/{type}", ModelsCatalog.INDEX_MAPPINGS)
        .build();

    @Inject
    public DefaultRoutesProvider(Client client, ModelsCatalog modelsCatalog) {
        super(client, modelsCatalog);

        this.officialRestApiSpecRoutesProvider = new OfficialRestApiSpecRoutesProvider(client, modelsCatalog);
    }

    @Override
    protected List<Route> getRoutesInternal() {
        List<Route> routes = this.officialRestApiSpecRoutesProvider.getRoutes();

        for (Route route : routes) {
            List<Parameter> paramsNotInPath = new ArrayList<>();
            for (Parameter parameter : route.getParameters()) {

                // Collect all path parameters which are not in the path
                if (parameter.getParamType().equals(ParamType.PATH)) {
                    if (!route.getApiPath().contains("{" + parameter.getName() + "}")) {
                        paramsNotInPath.add(parameter);
                    }
                }

                // Add all indices and aliasses to {index} path params
                if (parameter.getParamType().equals(ParamType.PATH) && parameter.getName().equals("index")) {
                    boolean isCreateIndexRoute = route.getApiPath().equals("{index}") &&
                        (route.getMethod().equals(HttpMethod.PUT) || route.getMethod().equals(HttpMethod.POST));

                    if (!isCreateIndexRoute) {
                        parameter.setEnumValues(getAllIndicesAndAliases());
                    }

                }

                // Add all aliasses to "_alias/{name}" or "_aliases/{name}" path params
                if (parameter.getParamType().equals(ParamType.PATH) && parameter.getName().equals("name")) {
                    boolean isAliasParam = route.getApiPath().contains("_alias/{name}") ||
                        route.getApiPath().contains("_aliases/{name}");

                    if (isAliasParam) {

                        boolean isCreateAliasRoute =
                            (route.getApiPath().endsWith("_alias/{name}") || route.getApiPath().endsWith("_aliases/{name}"))
                                && (route.getMethod().equals(HttpMethod.PUT) || route.getMethod().equals(HttpMethod.POST));

                        if (!isCreateAliasRoute) {
                            parameter.setEnumValues(getAllAliases());
                        }
                    }
                }

                if (parameter.getParamType().equals(ParamType.BODY)) {
                    if (bodyModels.containsKey(route.getApiPath())) {
                        parameter.setModel(bodyModels.get(route.getApiPath()));
                    }
                }
            }

            // Remove all path parameters which are not in the path
            for (Parameter parameter : paramsNotInPath) {
                route.getParameters().remove(parameter);
            }
        }

        return routes;
    }
}
