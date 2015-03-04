package org.elasticsearch.routes.index;

import net.itimothy.rest.description.HttpMethod;
import net.itimothy.rest.description.ParamType;
import net.itimothy.rest.description.Route;
import org.elasticsearch.client.Client;
import org.elasticsearch.routes.ModelsCatalog;

import java.util.List;

import static java.util.Arrays.asList;

class MappingRoutes extends BaseIndexRoutes {
    public MappingRoutes(Client client, ModelsCatalog modelsCatalog, String indexOrAlias) {
        super("Mapping APIs", client, modelsCatalog, indexOrAlias);
    }

    @Override
    public List<Route> getRoutesInternal() {
        return asList(
            Route.builder()
                .method(HttpMethod.GET)
                .apiPath(indexOrAliasPrepended("_mapping"))
                .model(ModelsCatalog.INDEX_MAPPINGS).build(),

            Route.builder()
                .method(HttpMethod.GET)
                .apiPath(indexOrAliasPrepended("{type}/_mapping"))
                .parameters(
                    typeSelectParam("type", ParamType.PATH).build()
                )
                .model(ModelsCatalog.INDEX_MAPPINGS).build(),

            Route.builder()
                .method(HttpMethod.GET)
                .apiPath(indexOrAliasPrepended("_mapping/{type}"))
                .parameters(
                    typeSelectParam("type", ParamType.PATH).build()
                )
                .model(ModelsCatalog.INDEX_MAPPINGS).build()
        );
    }
}