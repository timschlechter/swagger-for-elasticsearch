package net.itimothy.elasticsearch.plugin.swagger;

import org.elasticsearch.common.inject.Module;
import org.elasticsearch.plugins.Plugin;

import java.util.Collection;
import java.util.Collections;

public class SwaggerPlugin extends Plugin {
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
    public Collection<Module> nodeModules() {
        return Collections.<Module>singletonList(new SwaggerModule());
    }

}
