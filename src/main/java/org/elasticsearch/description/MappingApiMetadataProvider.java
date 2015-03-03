package org.elasticsearch.description;

import net.itimothy.rest.description.HttpMethod;
import net.itimothy.rest.description.Parameter;
import net.itimothy.rest.description.ParamType;
import net.itimothy.rest.description.Route;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

class MappingApiMetadataProvider extends ElasticSearchMetadataProvider {
    public MappingApiMetadataProvider(ModelsCatalog modelsCatalog, DataProvider dataProvider, String indexOrAlias) {
        super("Mapping APIs", modelsCatalog, dataProvider, indexOrAlias);
    }

    @Override
    public List<Route> getRoutesInternal() {
        List<Route> result = new ArrayList<>();

        result.addAll(asList(
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
            ));

        if (getIndexOrAlias() == null) {
            result.addAll(asList(
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
            ));
        }
        return result;
    }
}