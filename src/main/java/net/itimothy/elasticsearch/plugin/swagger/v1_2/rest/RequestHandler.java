package net.itimothy.elasticsearch.plugin.swagger.v1_2.rest;

import net.itimothy.elasticsearch.plugin.swagger.v1_2.model.SwaggerModel;
import net.itimothy.elasticsearch.routes.ModelsCatalog;
import net.itimothy.elasticsearch.routes.RoutesProvider;
import net.itimothy.elasticsearch.routes.defaultroutes.DefaultRoutesProvider;
import net.itimothy.elasticsearch.routes.indexroutes.IndexRoutesProvider;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.rest.*;

import java.security.AccessController;
import java.security.PrivilegedAction;

import static org.elasticsearch.rest.RestStatus.OK;

public abstract class RequestHandler extends BaseRestHandler {

    protected RequestHandler(Settings settings, RestController controller, Client client) {
        super(settings, controller, client);
    }

    protected abstract SwaggerModel handleRequest(RestRequest request, Client client, RoutesProvider routesProvider) throws Exception;

    @Override
    protected void handleRequest(RestRequest request, final RestChannel channel, Client client) throws Exception {
        try {
            final SwaggerModel model = handleRequest(request, client, createMetadataProvider(request, client));

            AccessController.doPrivileged(
                new PrivilegedAction<Void>() {
                    public Void run() {
                        RestResponse response = new BytesRestResponse(OK, "application/json", model.toJson());
                        response.addHeader("Access-Control-Allow-Methods", "GET");
                        channel.sendResponse(response);
                        return null;
                    }
                }
            );
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    private RoutesProvider createMetadataProvider(RestRequest request, Client client) {
        String indexOrAlias = request.param("indexOrAlias");

        return StringUtils.isBlank(indexOrAlias)
            ? new DefaultRoutesProvider(client, new ModelsCatalog(client))
            : new IndexRoutesProvider(client, new ModelsCatalog(client), indexOrAlias);
    }
}
