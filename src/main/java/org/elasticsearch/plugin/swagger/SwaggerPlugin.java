package org.elasticsearch.plugin.swagger;

import org.elasticsearch.common.inject.Module;
import org.elasticsearch.plugins.AbstractPlugin;

import java.util.Arrays;
import java.util.Collection;

public class SwaggerPlugin extends AbstractPlugin {
    public final static String API_DOCS_PATH = "_swagger";

    @Override
    public String name() {
        return "swagger";
    }

    @Override
    public String description() {
        return "Swagger for Elasticsearch";
    }

    @Override
    public Collection<Class<? extends Module>> modules() {
        return Arrays.asList(SwaggerModule.class);
    }

}
