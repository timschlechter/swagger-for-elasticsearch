package org.elasticsearch.description;

import net.itimothy.rest.description.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class DocumentApiMetadataProvider extends ElasticSearchMetadataProvider {
    public DocumentApiMetadataProvider(ModelsCatalog modelsCatalog, ParametersFactory parametersFactory, DataProvider dataProvider, String indexOrAlias) {
        super("Document APIs", modelsCatalog, parametersFactory, dataProvider, indexOrAlias);
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
            getModelsCatalog().getIndexTypeModelsMap().get(getIndexOrAlias()).stream()
                .sorted(Comparator.comparing(m -> m.getName()))
                .map(model -> asList(
                    Route.builder()
                        .group("- " + model.getName())
                        .method(HttpMethod.POST)
                        .apiPath(indexOrAliasPrepended(model.getName()))
                        .parameters(asList(
                            Parameter.builder()
                                .paramType(ParameterType.BODY)
                                .model(model).build()
                        ))
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
                        .parameters(asList(
                            pathParam("id").build(),
                            Parameter.builder()
                                .paramType(ParameterType.BODY)
                                .model(model).build()
                        ))
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
                        .parameters(asList(
                            pathParam("id").build()
                        ))
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
                        .parameters(asList(
                            pathParam("id").build()
                        ))
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
                        .parameters(asList(
                            queryParam("q")
                                .description("The query string").build(),
                            queryParam("df")
                                .description("The default field to use when no field prefix is defined within the query").build(),
                            queryParam("analyzer")
                                .description("The analyzer name to be used when analyzing the query string").build(),
                            queryParam("default_operator")
                                .description("The default operator to be used, can be AND or OR. Defaults to OR").build(),
                            queryParam("explain", Primitive.BOOLEAN)
                                .defaultValue("false")
                                .description("For each hit, contain an explanation of how scoring of the hits was computed").build(),
                            queryParam("_source", Primitive.BOOLEAN)
                                .defaultValue("true")
                                .description("Set to false to disable retrieval of the _source field. You can also retrieve part of the document by using _source_include & _source_exclude (see the request body documentation for more details)").build(),
                            queryParam("fields")
                                .description("The selective stored fields of the document to return for each hit, comma delimited. Not specifying any value will cause no fields to return").build(),
                            queryParam("sort")
                                .description("Sorting to perform. Can either be in the form of fieldName, or fieldName:asc/fieldName:desc. The fieldName can either be an actual field within the document, or the special _score name to indicate sorting based on scores. There can be several sort parameters (order is important)").build(),
                            queryParam("track_scores", Primitive.BOOLEAN)
                                .defaultValue("false")
                                .description("When sorting, set to true in order to still track scores and return them as part of each hit").build(),
                            queryParam("timeout", Primitive.LONG)
                                .description("A search timeout, bounding the search request to be executed within the specified time value and bail with the hits accumulated up to that point when expired. Defaults to no timeout").build(),
                            queryParam("terminate_after", Primitive.LONG)
                                .minVersion("1.4.0.Beta1")
                                .description("The maximum number of documents to collect for each shard, upon reaching which the query execution will terminate early. If set, the response will have a boolean field terminated_early to indicate whether the query execution has actually terminated_early. Defaults to no terminate_after").build(),
                            queryParam("from", Primitive.LONG)
                                .description("The starting from index of the hits to return. Defaults to 0").build(),
                            queryParam("size", Primitive.LONG)
                                .description("The number of hits to return. Defaults to 10").build(),
                            enumQueryParam("search_type", asList("dfs_query_then_fetch", "dfs_query_and_fetch", "query_then_fetch", "query_and_fetch", "count", "scan"))
                                .description("The type of the search operation to perform. Can be dfs_query_then_fetch, dfs_query_and_fetch, query_then_fetch, query_and_fetch, count, scan").build(),
                            queryParam("lowercase_expanded_terms", Primitive.BOOLEAN)
                                .defaultValue("true")
                                .description("Should terms be automatically lowercased or not. Defaults to true").build(),
                            queryParam("analyze_wildcard", Primitive.BOOLEAN)
                                .defaultValue("false")
                                .description("Should wildcard and prefix queries be analyzed or not. Defaults to false").build()
                            ))
                        .model(getModelsCatalog().getDocumentModel(model)).build()
                ))
                .flatMap(r -> r.stream())
                .collect(Collectors.toList())
        );

        return result;
    }
}