package org.elasticsearch.description;

import net.itimothy.rest.description.HttpMethod;
import net.itimothy.rest.description.Parameter;
import net.itimothy.rest.description.ParameterType;
import net.itimothy.rest.description.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class TypeApiMetadataProvider extends ElasticSearchMetadataProvider {
    public TypeApiMetadataProvider(ModelsCatalog modelsCatalog, ParametersFactory parametersFactory, DataProvider dataProvider, String indexOrAlias) {
        super("Type", modelsCatalog, parametersFactory, dataProvider, indexOrAlias);
    }

    @Override
    public List<Route> getRoutesInternal() {
        if (getIndexOrAlias() == null) {
            return asList();
        }

        List<Route> result = new ArrayList<>();

        result.addAll(asList(
            Route.builder()
                .method(HttpMethod.POST)
                .apiPath(indexOrAliasPrepended("{type}"))
                .parameters(asList(
                    pathParam("type").build(),
                    Parameter.builder()
                        .paramType(ParameterType.BODY)
                        .model(ModelsCatalog.OBJECT).build()
                )).build()
        ));

        result.addAll(
            this.getDataProvider().getAllDocumentTypes().stream()
                .map(t -> asList(
                    Route.builder()
                        .group(getDefaultGroup() + ": " + t)
                        .method(HttpMethod.POST)
                        .apiPath(indexOrAliasPrepended(t))
                        .parameters(asList(
                            Parameter.builder()
                                .paramType(ParameterType.BODY)
                                .model(ModelsCatalog.OBJECT).build()
                        )).build(),
                    
                    Route.builder()
                        .group(getDefaultGroup() + ": " + t)
                        .method(HttpMethod.GET)
                        .apiPath(indexOrAliasPrepended(t + "/{id}"))
                        .parameters(asList(
                            pathParam("id").build()
                        ))
                        .model(ModelsCatalog.INDEX_MAPPINGS).build()
                ))
                .flatMap(r -> r.stream())
                .collect(Collectors.toList())
        );

        return result;
    }
}