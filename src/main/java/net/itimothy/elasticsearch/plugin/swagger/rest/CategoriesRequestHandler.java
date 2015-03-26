package net.itimothy.elasticsearch.plugin.swagger.rest;

import com.google.gson.GsonBuilder;
import net.itimothy.elasticsearch.routes.ModelsCatalog;
import net.itimothy.elasticsearch.routes.defaultroutes.DefaultRoutesProvider;
import net.itimothy.elasticsearch.routes.model.Route;
import net.itimothy.util.CollectionUtil;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.base.Function;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.util.CollectionUtils;
import org.elasticsearch.rest.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    protected void handleRequest(RestRequest request, RestChannel channel, Client client) throws Exception {
        try {
            List<String> result = new ArrayList<>();
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

            GsonBuilder gson = new GsonBuilder();
            gson.setPrettyPrinting();

            RestResponse response = new BytesRestResponse(OK, "application/json", gson.create().toJson(result));

            response.addHeader("Access-Control-Allow-Methods", "GET");

            channel.sendResponse(response);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }
}
