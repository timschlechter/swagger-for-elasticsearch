package org.elasticsearch.plugin.swagger.handlers;

import com.wordnik.swagger.core.util.JsonSerializer;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.plugin.swagger.model.*;
import org.elasticsearch.rest.*;

import static java.util.Arrays.asList;
import static org.elasticsearch.plugin.swagger.SwaggerPlugin.*;
import static org.elasticsearch.rest.RestRequest.Method.GET;
import static org.elasticsearch.rest.RestStatus.OK;

public class ResourceListHandler extends SwaggerRequestHandler {

    @Inject
    public ResourceListHandler(Settings settings, Client client, RestController controller) {
        super(settings, controller, client);
        controller.registerHandler(GET, PLUGIN_PATH + "/api-docs", this);
    }

    @Override
    protected RestResponse handleRequest(RestRequest request, Client client) throws Exception {
        ResourceListing resourceListing = new ResourceListing(
            SWAGGER_VERSION,
            asList(
                new Resource("/_mapping", "The get mapping API allows to retrieve mapping definitions for an index or index/type."),
                new Resource("/_aliases", "The index aliases API allow to alias an index with a name, with all APIs automatically converting the alias name to the actual index name.")
            ),
            API_VERSION,
            new Info(),
            null
        );
        
        return new BytesRestResponse(OK, "application/json", JsonSerializer.asJson(resourceListing));
    }
}
