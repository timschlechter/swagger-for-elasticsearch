package org.elasticsearch.description;

import net.itimothy.rest.description.HttpMethod;
import net.itimothy.rest.description.Parameter;
import net.itimothy.rest.description.ParameterType;
import net.itimothy.rest.description.Route;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

class MappingApiMetadataProvider extends ElasticSearchMetadataProvider {
    public MappingApiMetadataProvider(ModelsCatalog modelsCatalog, ParametersFactory parametersFactory, DataProvider dataProvider, String indexOrAlias) {
        super("Mapping", modelsCatalog, parametersFactory, dataProvider, indexOrAlias);
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
                .parameters(asList(
                    indexAliasOrWildcardExpressionsPathParam("index").build()
                ))
                .model(ModelsCatalog.INDEX_MAPPINGS).build(),

            Route.builder()
                .method(HttpMethod.GET)
                .apiPath(indexOrAliasPrepended("_mapping/{type}"))
                .parameters(asList(
                    typePathParam("type").build()
                ))
                .model(ModelsCatalog.INDEX_MAPPINGS).build()
        ));

        if (getIndexOrAlias() == null) {
            result.addAll(asList(
                Route.builder()
                    .method(HttpMethod.GET)
                    .apiPath("{index}/_mapping/{type}")
                    .parameters(asList(
                        indexAliasOrWildcardExpressionsPathParam("index").build(),
                        typePathParam("type").build()
                    ))
                    .model(ModelsCatalog.INDEX_MAPPINGS).build(),

                Route.builder()
                    .method(HttpMethod.PUT)
                    .parameters(asList(
                        indexAliasOrWildcardExpressionsPathParam("index").build(),
                        typePathParam("type")
                            .description("Name of the type to add. Must be the name of the type defined in the body").build(),
                        Parameter.builder()
                            .paramType(ParameterType.BODY)
                            .model(ModelsCatalog.MAPPING).build()
                    ))
                    .apiPath("{index}/_mapping/{type}")
                    .name("putMapping")
                    .model(ModelsCatalog.MAPPING).build()
            ));
        }
        return result;
    }
}