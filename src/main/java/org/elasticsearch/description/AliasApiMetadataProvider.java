package org.elasticsearch.description;

import net.itimothy.rest.description.HttpMethod;
import net.itimothy.rest.description.ParamType;
import net.itimothy.rest.description.Route;

import java.util.List;

import static java.util.Arrays.asList;

class AliasApiMetadataProvider extends ElasticSearchMetadataProvider {
    public AliasApiMetadataProvider(ModelsCatalog modelsCatalog, DataProvider dataProvider, String indexOrAlias) {
        super("Alias APIs", modelsCatalog, dataProvider, indexOrAlias);
    }

    @Override
    public List<Route> getRoutesInternal() {
        if (getIndexOrAlias() != null) {
            return asList();
        }

        return asList(
            Route.builder()
                .method(HttpMethod.GET)
                .apiPath("_alias")
                .model(ModelsCatalog.INDEX_ALIASES).build(),

            Route.builder()
                .method(HttpMethod.GET)
                .apiPath("_alias/{index}")
                .model(ModelsCatalog.INDEX_FEATURES)
                .parameters(
                    indexOrAliasSelectParam("index", ParamType.PATH).build()
                ).build()
        );
    }
}
