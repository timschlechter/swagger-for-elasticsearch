package org.elasticsearch.plugin.swagger.handlers;

import com.wordnik.swagger.core.util.JsonSerializer;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.plugin.swagger.model.Info;
import org.elasticsearch.plugin.swagger.model.Resource;
import org.elasticsearch.plugin.swagger.model.ResourceListing;
import org.elasticsearch.rest.BytesRestResponse;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.RestResponse;

import static java.util.Arrays.asList;
import static org.elasticsearch.plugin.swagger.SwaggerPlugin.*;
import static org.elasticsearch.rest.RestRequest.Method.GET;
import static org.elasticsearch.rest.RestStatus.OK;

public class ResourceListHandler extends SwaggerRequestHandler {

    @Inject
    public ResourceListHandler(Settings settings, Client client, RestController controller) {
        super(settings, controller, client);
        controller.registerHandler(GET, API_DOCS_PATH, this);
    }

    @Override
    protected RestResponse handleRequest(RestRequest request, Client client) throws Exception {
        ResourceListing resourceListing = new ResourceListing(
            SWAGGER_VERSION,
            asList(
                new Resource("/Aliases", null),
                new Resource("/Indices", null),
                new Resource("/Mappings", null)
            ),
            API_VERSION,
            new Info(),
            null
        );

        return new BytesRestResponse(OK, "application/json", JsonSerializer.asJson(resourceListing));
    }
}
