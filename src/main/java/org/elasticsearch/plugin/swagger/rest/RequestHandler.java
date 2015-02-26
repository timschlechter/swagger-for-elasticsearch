package org.elasticsearch.plugin.swagger.rest;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.metadata.providers.ElasticsearchRoutesMetadataProvider;
import org.elasticsearch.metadata.providers.RoutesMetadataProvider;
import org.elasticsearch.rest.*;
import org.elasticsearch.swagger.v1_2.model.SwaggerModel;

import static org.elasticsearch.plugin.swagger.SwaggerPlugin.API_VERSION;
import static org.elasticsearch.rest.RestStatus.OK;

public abstract class RequestHandler extends BaseRestHandler {

    protected RequestHandler(Settings settings, RestController controller, Client client) {
        super(settings, controller, client);
    }

    protected abstract SwaggerModel handleRequest(RestRequest request, Client client) throws Exception;

    @Override
    protected void handleRequest(RestRequest request, RestChannel channel, Client client) throws Exception {
        SwaggerModel model = handleRequest(request, client);
        RestResponse response = new BytesRestResponse(OK, "application/json", model.toJson());

        response.addHeader("Access-Control-Allow-Methods", "GET");

        channel.sendResponse(response);
    }
    
    protected RoutesMetadataProvider getMetadataProvider(RestRequest request, Client client) {
        return new ElasticsearchRoutesMetadataProvider(API_VERSION, client);
    }
}
