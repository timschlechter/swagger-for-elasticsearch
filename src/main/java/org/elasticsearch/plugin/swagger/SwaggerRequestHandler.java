package org.elasticsearch.plugin.swagger;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.plugin.swagger.v1_2.model.SwaggerModel;
import org.elasticsearch.rest.*;

import static org.elasticsearch.rest.RestStatus.OK;

public abstract class SwaggerRequestHandler extends BaseRestHandler {

    protected SwaggerRequestHandler(Settings settings, RestController controller, Client client) {
        super(settings, controller, client);
    }

    protected abstract SwaggerModel handleRequest(RestRequest request, Client client) throws Exception;

    @Override
    protected void handleRequest(RestRequest request, RestChannel channel, Client client) throws Exception {
        SwaggerModel model = handleRequest(request, client);
        RestResponse response = new BytesRestResponse(OK, "application/json", model.toJson());

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type, api_key, Authorization");

        channel.sendResponse(response);
    }
}
