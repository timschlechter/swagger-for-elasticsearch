package net.itimothy.elasticsearch.routes.indexroutes;

import net.itimothy.elasticsearch.routes.model.HttpMethod;
import net.itimothy.elasticsearch.routes.model.ParamType;
import net.itimothy.elasticsearch.routes.model.Route;
import org.elasticsearch.client.Client;
import net.itimothy.elasticsearch.routes.ModelsCatalog;

import java.util.List;

import static java.util.Arrays.asList;

class MappingRoutes extends BaseIndexRoutes {
    public MappingRoutes(Client client, ModelsCatalog modelsCatalog, String indexOrAlias) {
        super(client, modelsCatalog, indexOrAlias);
    }

    @Override
    protected String getDefaultGroup() {
        return "Mapping APIs";
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