package net.itimothy.elasticsearch.plugin.swagger.v1_2.rest;

import net.itimothy.elasticsearch.description.ElasticsearchRoutesProvider;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import net.itimothy.elasticsearch.plugin.swagger.v1_2.model.SwaggerModel;
import org.elasticsearch.rest.*;
import net.itimothy.elasticsearch.description.RoutesProvider;

import static org.elasticsearch.rest.RestStatus.OK;

public abstract class RequestHandler extends BaseRestHandler {

    protected RequestHandler(Settings settings, RestController controller, Client client) {
        super(settings, controller, client);
    }

    protected abstract SwaggerModel handleRequest(RestRequest request, Client client) throws Exception;

    @Override
    protected void handleRequest(RestRequest request, RestChannel channel, Client client) throws Exception {
        try {
            SwaggerModel model = handleRequest(request, client);
            RestResponse response = new BytesRestResponse(OK, "application/json", model.toJson());

            response.addHeader("Access-Control-Allow-Methods", "GET");

            channel.sendResponse(response);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    protected RoutesProvider getMetadataProvider(RestRequest request, Client client) {
        return new ElasticsearchRoutesProvider(client, request.param("indexOrAlias"));
    }
}
