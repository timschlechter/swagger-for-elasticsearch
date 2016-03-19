package net.itimothy.elasticsearch.plugin.swagger.rest;

import com.google.common.base.Function;
import com.google.gson.GsonBuilder;
import net.itimothy.elasticsearch.routes.ModelsCatalog;
import net.itimothy.elasticsearch.routes.defaultroutes.DefaultRoutesProvider;
import net.itimothy.elasticsearch.routes.model.Route;
import net.itimothy.util.CollectionUtil;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.rest.*;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;

import static net.itimothy.elasticsearch.plugin.swagger.SwaggerPlugin.API_DOCS_PATH;
import static org.elasticsearch.rest.RestRequest.Method.GET;
import static org.elasticsearch.rest.RestStatus.OK;

public class CategoriesRequestHandler extends BaseRestHandler {

    private final DefaultRoutesProvider defaultRoutesProvider;

    @Inject
    public CategoriesRequestHandler(Settings settings, RestController controller, Client client, DefaultRoutesProvider defaultRoutesProvider, ModelsCatalog modelsCatalog) {
        super(settings, controller, client);
        this.defaultRoutesProvider = defaultRoutesProvider;
        controller.registerHandler(GET, API_DOCS_PATH + "/categories", this);
    }

    @Override
    protected void handleRequest(RestRequest request, final RestChannel channel, Client client) throws Exception {
        try {
            final List<String> result = new ArrayList<>();
            for (Route route : defaultRoutesProvider.getRoutes()) {
                if (!result.contains(route.getCategory())) {
                    result.add(route.getCategory());
                }
            }

            CollectionUtil.sort(result, new Function<String, String>() {
                @Override
                public String apply(String o) {
                    return o;
                }
            });

            AccessController.doPrivileged(
                new PrivilegedAction<Void>() {
                    public Void run() {
                        GsonBuilder gson = new GsonBuilder();
                        gson.setPrettyPrinting();

                        RestResponse response = new BytesRestResponse(OK, "application/json", gson.create().toJson(result));

                        response.addHeader("Access-Control-Allow-Methods", "GET");

                        channel.sendResponse(response);

                        return null;
                    }
                }
            );
        } catch (
            Exception ex
            )

        {
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }
}
