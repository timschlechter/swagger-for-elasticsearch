package org.elasticsearch.routes.root;

import net.itimothy.rest.description.HttpMethod;
import net.itimothy.rest.description.ParamType;
import net.itimothy.rest.description.Parameter;
import net.itimothy.rest.description.Route;
import org.elasticsearch.client.Client;
import org.elasticsearch.routes.ModelsCatalog;
import org.elasticsearch.routes.RoutesProvider;

import java.util.List;

import static java.util.Arrays.asList;

class MappingRoutes extends RoutesProvider {
    public MappingRoutes(ModelsCatalog modelsCatalog, Client client) {
        super("Mapping APIs", client, modelsCatalog);
    }

    @Override
    public List<Route> getRoutesInternal() {
        return asList(
            Route.builder()
                .method(HttpMethod.GET)
                .apiPath("{index}/_mapping/{type}")
                .parameters(
                    indexOrAliasSelectParam("index", ParamType.PATH).build(),
                    typeSelectParam("type", ParamType.PATH).build()
                )
                .model(ModelsCatalog.INDEX_MAPPINGS).build(),

            Route.builder()
                .method(HttpMethod.PUT)
                .parameters(
                    indexOrAliasSelectParam("index", ParamType.PATH).build(),
                    pathParam("type")
                        .description("Name of the type to add. Must be the name of the type defined in the body").build(),
                    Parameter.builder()
                        .paramType(ParamType.BODY)
                        .model(ModelsCatalog.MAPPING).build()
                )
                .apiPath("{index}/_mapping/{type}")
                .name("putMapping")
                .model(ModelsCatalog.MAPPING).build()
        );
    }
}