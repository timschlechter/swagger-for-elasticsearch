package org.elasticsearch.plugin.swagger.v1_2.rest;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.routes.RoutesProvider;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.plugin.swagger.v1_2.SwaggerProvider;
import org.elasticsearch.plugin.swagger.v1_2.model.SwaggerModel;

import static org.elasticsearch.plugin.swagger.SwaggerPlugin.API_DOCS_PATH;
import static org.elasticsearch.rest.RestRequest.Method.GET;

public class ResourceListingRequestHandler extends RequestHandler {
    @Inject
    public ResourceListingRequestHandler(Settings settings, Client client, RestController controller) {
        super(settings, controller, client);
        controller.registerHandler(GET, API_DOCS_PATH + "/v1.2", this);
        controller.registerHandler(GET, API_DOCS_PATH + "/{indexOrAlias}/v1.2/", this);
    }

    @Override
    protected SwaggerModel handleRequest(RestRequest request, Client client) throws Exception {
        RoutesProvider metadataProvider = getMetadataProvider(request, client);
        SwaggerProvider swaggerProvider = new SwaggerProvider(
            metadataProvider.getInfo(), 
            metadataProvider.getRoutes()
        );
        
        return swaggerProvider.getResourceListing();
    }
}
