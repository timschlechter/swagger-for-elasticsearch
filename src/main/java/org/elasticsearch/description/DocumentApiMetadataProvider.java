package org.elasticsearch.description;

import net.itimothy.rest.description.HttpMethod;
import net.itimothy.rest.description.ParamType;
import net.itimothy.rest.description.Route;
import org.elasticsearch.client.Client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class DocumentApiMetadataProvider extends ElasticSearchMetadataProvider {
    public DocumentApiMetadataProvider(ModelsCatalog modelsCatalog, Client client, String indexOrAlias) {
        super("Document APIs", client, modelsCatalog, indexOrAlias);
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
                .parameters(
                    param("type", ParamType.PATH).build(),
                    bodyParam(ModelsCatalog.OBJECT).build()
                ).build()
        ));

        result.addAll(
            getModelsCatalog().getIndexTypeModelsMap().get(getIndexOrAlias()).stream()
                .sorted(Comparator.comparing(m -> m.getName()))
                .map(model -> asList(
                    Route.builder()
                        .group("- " + model.getName())
                        .method(HttpMethod.POST)
                        .apiPath(indexOrAliasPrepended(model.getName()))
                        .parameters(
                            bodyParam(model).build()
                        )
                        .description("The index API adds or updates a typed JSON document in a specific index, making it searchable")
                        .notes("The index operation can be executed without specifying the id. In such a case, an id will be generated automatically. In addition, the op_type will automatically be set to create. Here is an example (note the POST used instead of PUT):\n" +
                                "\n" +
                                "$ curl -XPOST 'http://localhost:9200/twitter/tweet/' -d '{\n" +
                                "    \"user\" : \"kimchy\",\n" +
                                "    \"post_date\" : \"2009-11-15T14:12:12\",\n" +
                                "    \"message\" : \"trying out Elasticsearch\"\n" +
                                "}'"
                        )
                        .build(),

                    Route.builder()
                        .group("- " + model.getName())
                        .method(HttpMethod.PUT)
                        .apiPath(indexOrAliasPrepended(model.getName()) + "/{id}")
                        .parameters(
                            param("id", ParamType.PATH).build(),
                            bodyParam(model).build()
                        )
                        .description("The index API adds or updates a typed JSON document in a specific index, making it searchable")
                        .notes("The following example inserts the JSON document into the \"twitter\" index, under a type called \"tweet\" with an id of 1:\n" +
                                "\n" +
                                "$ curl -XPUT 'http://localhost:9200/twitter/tweet/1' -d '{\n" +
                                "    \"user\" : \"kimchy\",\n" +
                                "    \"post_date\" : \"2009-11-15T14:12:12\",\n" +
                                "    \"message\" : \"trying out Elasticsearch\"\n" +
                                "}'"
                        )
                        .build(),

                    Route.builder()
                        .group("- " + model.getName())
                        .method(HttpMethod.GET)
                        .apiPath(indexOrAliasPrepended(model.getName() + "/{id}"))
                        .parameters(
                            param("id", ParamType.PATH).build()
                        )
                        .description("The get API allows to get a typed JSON document from the index based on its id")
                        .notes("The following example gets a JSON document from an index called twitter, under a type called tweet, with id valued 1:\n" +
                                "\n" +
                                "$ curl -XGET 'http://localhost:9200/twitter/tweet/1'"
                        )
                        .model(getModelsCatalog().getDocumentModel(model)).build(),

                    Route.builder()
                        .group("- " + model.getName())
                        .method(HttpMethod.DELETE)
                        .apiPath(indexOrAliasPrepended(model.getName() + "/{id}"))
                        .parameters(
                            param("id", ParamType.PATH).build()
                        )
                        .description("The delete API allows to delete a typed JSON document from a specific index based on its id")
                        .notes(" The following example deletes the JSON document from an index called twitter, under a type called tweet, with id valued 1:\n" +
                                "\n" +
                                "$ curl -XDELETE 'http://localhost:9200/twitter/tweet/1'"
                        )
                        .model(getModelsCatalog().DOCUMENT_METADATA).build(),

                    Route.builder()
                        .group("- " + model.getName())
                        .method(HttpMethod.GET)
                        .apiPath(indexOrAliasPrepended(model.getName() + "/_search"))
                        .description("A search request can be executed purely using a URI by providing request parameters")
                        .notes("Not all search options are exposed when executing a search using this mode, but it can be handy for quick \"curl tests\". Here is an example:\n" +
                                "\n" +
                                "$ curl -XGET 'http://localhost:9200/twitter/tweet/_search?q=user:kimchy'"
                        )
                        .parameters(defaultUriSearchParams())
                        .model(getModelsCatalog().getDocumentSearchResultModel(model)).build()
                ))
                .flatMap(r -> r.stream())
                .collect(Collectors.toList())
        );

        return result;
    }
}