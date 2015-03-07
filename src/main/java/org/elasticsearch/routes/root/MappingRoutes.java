package org.elasticsearch.routes.root;

import net.itimothy.rest.description.*;
import org.elasticsearch.client.Client;
import org.elasticsearch.routes.ModelsCatalog;
import org.elasticsearch.routes.RoutesProvider;

import java.util.List;

import static java.util.Arrays.asList;

class MappingRoutes extends RoutesProvider {
    public MappingRoutes(ModelsCatalog modelsCatalog, Client client) {
        super("Mapping management", client, modelsCatalog);
    }

    @Override
    public List<Route> getRoutesInternal() {
        return asList(

            //
            // Put Mapping
            //
            Route.builder()
                .method(HttpMethod.PUT)
                .apiPath("{index}/_mapping/{type}")
                .description("Allows to register specific mapping definition for a specific type")
                .notes("The following example creates a mapping called tweet within the twitter index. The mapping simply defines that the message field should be stored (by default, fields are not stored, just indexed) so we can retrieve it later on using selective loading.\n" +
                    "\n" +
                    "$ curl -XPUT 'http://localhost:9200/twitter/_mapping/tweet' -d '\n" +
                    "{\n" +
                    "    \"tweet\" : {\n" +
                    "        \"properties\" : {\n" +
                    "            \"message\" : {\"type\" : \"string\", \"store\" : true }\n" +
                    "        }\n" +
                    "    }\n" +
                    "}\n" +
                    "'")
                .parameters(
                    indexOrAliasSelectParam("index", ParamType.PATH).build(),
                    pathParam("type")
                        .description("Name of the type to add. Must be the name of the type defined in the body").build(),
                    Parameter.builder()
                        .paramType(ParamType.BODY)
                        .model(ModelsCatalog.MAPPING).build()
                )
                .model(ModelsCatalog.MAPPING).build(),

            //
            // Get Mapping
            //
            Route.builder()
                .method(HttpMethod.GET)
                .apiPath("{index}/_mapping")
                .description("Retrieve mapping definitions for an index")
                .parameters(
                    indexOrAliasSelectParam("index", ParamType.PATH).build()
                )
                .model(ModelsCatalog.INDEX_MAPPINGS).build(),

            Route.builder()
                .method(HttpMethod.GET)
                .apiPath("{index}/_mapping/{type}")
                .description("Retrieve mapping definitions for an index/type")
                .parameters(
                    indexOrAliasSelectParam("index", ParamType.PATH).build(),
                    typeSelectParam("type", ParamType.PATH).build()
                )
                .model(ModelsCatalog.INDEX_MAPPINGS).build(),

            //
            // Get Field Mapping
            //
            Route.builder()
                .method(HttpMethod.GET)
                .apiPath("{index}/_mapping/{type}/field/{field}")
                .parameters(
                    indexOrAliasSelectParam("index", ParamType.PATH).build(),
                    typeSelectParam("type", ParamType.PATH).build(),
                    fieldSelectParam("field", ParamType.PATH).build(),
                    queryParam("include_defaults", Primitive.BOOLEAN)
                        .defaultValue(false).build()

                )
                .description("Retrieve mapping definitions for one or more fields")
                .notes("The get field mapping API allows you to retrieve mapping definitions for one or more fields. This is useful when you do not need the complete type mapping returned by the Get Mapping API.\n" +
                    "\n" +
                    "The following returns the mapping of the field text only:\n" +
                    "\n" +
                    "curl -XGET 'http://localhost:9200/twitter/_mapping/tweet/field/text'")
                .model(ModelsCatalog.INDEX_MAPPINGS).build(),

            //
            // Delete Mapping
            //
            Route.builder()
                .method(HttpMethod.DELETE)
                .apiPath("{index}/{type}")
                .description("Allow to delete a mapping (type) along with its data")
                .notes("Note, most times, it make more sense to reindex the data into a fresh index compared to delete large chunks of it.")
                .parameters(
                    indexOrAliasSelectParam("index", ParamType.PATH).build(),
                    typeSelectParam("type", ParamType.PATH).build()
                ).build(),

            Route.builder()
                .method(HttpMethod.DELETE)
                .apiPath("{index}/{type}/_mapping")
                .description("Allow to delete a mapping (type) along with its data")
                .notes("Note, most times, it make more sense to reindex the data into a fresh index compared to delete large chunks of it.")
                .parameters(
                    indexOrAliasSelectParam("index", ParamType.PATH).build(),
                    typeSelectParam("type", ParamType.PATH).build()
                ).build(),

            Route.builder()
                .method(HttpMethod.DELETE)
                .apiPath("{index}/_mapping/{type}")
                .description("Allow to delete a mapping (type) along with its data")
                .notes("Note, most times, it make more sense to reindex the data into a fresh index compared to delete large chunks of it.")
                .parameters(
                    indexOrAliasSelectParam("index", ParamType.PATH).build(),
                    typeSelectParam("type", ParamType.PATH).build()
                ).build(),

            //
            // Type exists
            //
            Route.builder()
                .method(HttpMethod.HEAD)
                .apiPath("{index}/{type}")
                .description("Check if a type/types exists in an index/indices")
                .parameters(
                    indexOrAliasSelectParam("index", ParamType.PATH).build(),
                    pathParam("type").build()
                )
                .responses(asList(
                    Response.builder().code(200).message("Type exists").build(),
                    Response.builder().code(404).message("Type does not exist").build()
                )).build()
        );
    }
}