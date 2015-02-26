package org.elasticsearch.plugin.swagger.rest;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.metadata.providers.RoutesMetadataProvider;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.swagger.v1_2.SwaggerProvider;
import org.elasticsearch.swagger.v1_2.model.SwaggerModel;

import static org.elasticsearch.plugin.swagger.SwaggerPlugin.API_DOCS_PATH;
import static org.elasticsearch.rest.RestRequest.Method.GET;

public class ApiDeclarationRequestHandler extends RequestHandler {
    @Inject
    public ApiDeclarationRequestHandler(Settings settings, Client client, RestController controller) {
        super(settings, controller, client);
        controller.registerHandler(GET, API_DOCS_PATH + "/{resource}", this);
    }

    @Override
    protected SwaggerModel handleRequest(RestRequest request, Client client) throws Exception {
        RoutesMetadataProvider metadataProvider = getMetadataProvider(request, client);
        SwaggerProvider swaggerProvider = new SwaggerProvider(metadataProvider);

        return swaggerProvider.getApiDeclaration(request.param("resource"));
    }
}
