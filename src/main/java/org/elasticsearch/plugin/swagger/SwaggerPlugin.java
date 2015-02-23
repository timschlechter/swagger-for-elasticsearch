package org.elasticsearch.plugin.swagger;

import com.google.common.collect.ImmutableList;
import org.elasticsearch.common.inject.Module;
import org.elasticsearch.plugins.AbstractPlugin;

import java.util.Collection;

public class SwaggerPlugin extends AbstractPlugin {
    public final static String API_DOCS_PATH = "_api-docs";
    public final static String SWAGGER_VERSION = "2.0";
    public final static String API_VERSION = "1.0";

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
        return ImmutableList.<Class<? extends Module>>of(SwaggerModule.class);
    }

}
