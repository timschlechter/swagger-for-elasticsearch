package org.elasticsearch.plugin.swagger.handlers;

import com.google.common.io.Resources;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.base.Charsets;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.plugin.swagger.SwaggerUIRestFilter;
import org.elasticsearch.rest.BytesRestResponse;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.RestResponse;

import java.net.URL;

import static org.elasticsearch.plugin.swagger.SwaggerPlugin.PLUGIN_PATH;
import static org.elasticsearch.rest.RestRequest.Method.GET;
import static org.elasticsearch.rest.RestStatus.OK;

public class SwaggerUIRequestHandler extends SwaggerRequestHandler {
    @Inject
    protected SwaggerUIRequestHandler(Settings settings, RestController controller, Client client) {
        super(settings, controller, client);
        controller.registerFilter(new SwaggerUIRestFilter());
        controller.registerHandler(GET, PLUGIN_PATH, this);
    }

    @Override
    protected RestResponse handleRequest(RestRequest request, Client client) throws Exception {
        String file = request.param("file");
        if (StringUtils.isBlank(file)) {
            file = "index.html";
        }
        
        URL url = Resources.getResource("swagger-ui/" + file);

        String contents = Resources.toString(url, Charsets.UTF_8);
        
        return new BytesRestResponse(OK, getContentType(url), contents);
    }
    
    private static String getContentType(URL url) {
       String[] parts =  url.getPath().split("\\.");
        
        switch (parts[parts.length-1].toLowerCase()) {
            case "jpg":  return "image/jpeg";
            case "js": return "text/javascript";
            case "css": return "text/css";
            case "html": return "text/html";
            default: return "text/plain";
        }
        
    }
}
