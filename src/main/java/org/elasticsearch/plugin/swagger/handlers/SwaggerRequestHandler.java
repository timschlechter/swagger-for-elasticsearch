package org.elasticsearch.plugin.swagger.handlers;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.rest.*;

public abstract class SwaggerRequestHandler extends BaseRestHandler {

    protected SwaggerRequestHandler(Settings settings, RestController controller, Client client) {
        super(settings, controller, client);
    }

    protected abstract RestResponse handleRequest(RestRequest request, Client client) throws Exception;

    @Override
    protected void handleRequest(RestRequest request, RestChannel channel, Client client) throws Exception {
        RestResponse response = handleRequest(request, client);

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type, api_key, Authorization");

        channel.sendResponse(response);
    }
}
