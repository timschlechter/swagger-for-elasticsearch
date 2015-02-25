package org.elasticsearch.plugin.swagger.v1_2.rest;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.plugin.swagger.ElasticsearchMetadataProvider;
import org.elasticsearch.plugin.swagger.SwaggerRequestHandler;
import org.elasticsearch.plugin.swagger.v1_2.SwaggerProvider;
import org.elasticsearch.plugin.swagger.v1_2.model.SwaggerModel;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestRequest;

import static org.elasticsearch.plugin.swagger.SwaggerPlugin.API_DOCS_PATH;
import static org.elasticsearch.rest.RestRequest.Method.GET;

public class ApiDeclarationHandler extends SwaggerRequestHandler {

    @Inject
    public ApiDeclarationHandler(Settings settings, Client client, RestController controller) {
        super(settings, controller, client);
        controller.registerHandler(GET, API_DOCS_PATH + "/{resource}", this);
    }

    @Override
    protected SwaggerModel handleRequest(RestRequest request, Client client) throws Exception {
        ElasticsearchMetadataProvider metadataProvider = new ElasticsearchMetadataProvider();
        SwaggerProvider swaggerProvider = new SwaggerProvider(metadataProvider);

        return swaggerProvider.getApiDeclaration(request.param("resource"));
    }
}
