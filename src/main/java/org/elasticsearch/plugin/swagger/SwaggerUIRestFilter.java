package org.elasticsearch.plugin.swagger;

import org.elasticsearch.http.netty.NettyHttpRequest;
import org.elasticsearch.rest.RestChannel;
import org.elasticsearch.rest.RestFilter;
import org.elasticsearch.rest.RestFilterChain;
import org.elasticsearch.rest.RestRequest;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.elasticsearch.plugin.swagger.SwaggerPlugin.PLUGIN_PATH;

public class SwaggerUIRestFilter extends RestFilter {

    @Override
    public void process(RestRequest request, RestChannel channel, RestFilterChain filterChain) throws Exception {
        String path = request.path();
        if (path.startsWith("/" + PLUGIN_PATH) && !path.startsWith("/" + PLUGIN_PATH + "/api-docs")) {
            Pattern p = Pattern.compile("^/" + PLUGIN_PATH + "/(.*)?");
            Matcher m = p.matcher(request.path());
            if (m.matches()) {
                Field pathField = NettyHttpRequest.class.getDeclaredField("rawPath");
                pathField.setAccessible(true);
                pathField.set(request, "_swagger");
                request.params().put("file", m.group(1));
            }
        }
        filterChain.continueProcessing(request, channel);
    }
}