package net.itimothy.elasticsearch.plugin.swagger.v1_2.rest;

import net.itimothy.elasticsearch.plugin.swagger.v1_2.SwaggerProvider;
import net.itimothy.elasticsearch.plugin.swagger.v1_2.model.SwaggerModel;
import net.itimothy.elasticsearch.routes.ModelsCatalog;
import net.itimothy.elasticsearch.routes.RoutesProvider;
import net.itimothy.elasticsearch.routes.defaultroutes.DefaultRoutesProvider;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestRequest;

import static net.itimothy.elasticsearch.plugin.swagger.SwaggerPlugin.API_DOCS_PATH;
import static org.elasticsearch.rest.RestRequest.Method.GET;

public class ResourceListingRequestHandler extends RequestHandler {
    @Inject
    public ResourceListingRequestHandler(Settings settings, Client client, RestController controller, DefaultRoutesProvider defaultRoutesProvider, ModelsCatalog modelsCatalog) {
        super(settings, controller, client, defaultRoutesProvider, modelsCatalog);
        controller.registerHandler(GET, API_DOCS_PATH + "/v1.2", this);
        controller.registerHandler(GET, API_DOCS_PATH + "/index/{index}/v1.2/", this);
        controller.registerHandler(GET, API_DOCS_PATH + "/alias/{alias}/v1.2/", this);
    }

    @Override
    protected SwaggerModel handleRequest(RestRequest request, Client client) throws Exception {
        RoutesProvider metadataProvider = getMetadataProvider(request, client);

        SwaggerProvider swaggerProvider = new SwaggerProvider(
            metadataProvider.getInfo(),
            metadataProvider.getRoutes(request.param("category"))
        );

        return swaggerProvider.getResourceListing();
    }
}
