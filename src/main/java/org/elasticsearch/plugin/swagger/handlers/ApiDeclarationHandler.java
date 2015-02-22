package org.elasticsearch.plugin.swagger.handlers;

import com.wordnik.swagger.core.util.JsonSerializer;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.plugin.swagger.model.*;
import org.elasticsearch.rest.BytesRestResponse;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.RestResponse;

import java.util.HashMap;
import java.util.List;

import static java.util.Arrays.asList;
import static org.elasticsearch.plugin.swagger.SwaggerPlugin.*;
import static org.elasticsearch.rest.RestRequest.Method.GET;
import static org.elasticsearch.rest.RestStatus.OK;

public class ApiDeclarationHandler extends SwaggerRequestHandler {

    @Inject
    public ApiDeclarationHandler(Settings settings, Client client, RestController controller) {
        super(settings, controller, client);
        controller.registerHandler(GET, PLUGIN_PATH + "/api-docs/{resource}", this);
    }

    @Override
    protected RestResponse handleRequest(RestRequest request, Client client) throws Exception {
        String resource = request.param("resource");
        ApiDeclaration apiDeclaration = new ApiDeclaration(
            SWAGGER_VERSION,
            API_VERSION,
            "/",
            resource,
            getApis(resource),
            new HashMap<>()
        );

        return new BytesRestResponse(OK, "application/json", JsonSerializer.asJson(apiDeclaration));
    }

    private static List<Api> getApis(String resource) {
        switch (resource) {
            case "_mapping":
                return getMappingApis();
            case "_aliases":
                return getAliasesApis();
        }
        
        return asList();
    }

    private static List<Api> getAliasesApis() {
        return asList(
            new Api(
                "/_aliases",
                "The index aliases API allow to alias an index with a name, with all APIs automatically converting the alias name to the actual index name.",
                asList(
                    new Operation(
                        "string",
                        HttpMethod.GET,
                        "The get index alias api allows to filter by alias name and index name.",
                        null,
                        "getAliases",
                        null
                    )
                )
            )
        );
    }

    private static List<Api> getMappingApis() {
        return asList(
            new Api(
                "/_mapping",
                "The get mapping API allows to retrieve mapping definitions for an index or index/type.",
                asList(
                    new Operation(
                        "string",
                        HttpMethod.GET,
                        "The get mapping API can be used to get more than one index or type mapping with a single call. ",
                        null,
                        "getMapping",
                        null
                    )
                )
            ),

            new Api(
                "/_mapping/{type}",
                "The get mapping API allows to retrieve mapping definitions for an index or index/type.",
                asList(
                    new Operation(
                        "string",
                        HttpMethod.GET,
                        "The get mapping API can be used to get more than one index or type mapping with a single call. ",
                        null,
                        "getMapping",
                        asList(
                            new Parameter(
                                "string",
                                ParameterType.path,
                                "type",
                                "Document type",
                                true)
                        )
                    )
                )
            )
        );
    }
}
