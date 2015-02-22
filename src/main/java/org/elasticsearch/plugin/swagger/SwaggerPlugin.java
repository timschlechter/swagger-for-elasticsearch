package org.elasticsearch.plugin.swagger;

import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.common.inject.Module;
import org.elasticsearch.plugins.AbstractPlugin;

import java.util.Collection;

public class SwaggerPlugin extends AbstractPlugin {
    public final static String PLUGIN_PATH = "_swagger";
    public final static String SWAGGER_VERSION = "2.0";
    public final static String API_VERSION = "1.0";

    @Override
    public String name() {
        return "Swagger Plugin";
    }

    @Override
    public String description() {
        return "Swagger for your Elasticsearch instance";
    }

    @Override
    public Collection<Class<? extends Module>> modules() {
        Collection<Class<? extends Module>> modules = Lists.newArrayList();
        modules.add(SwaggerModule.class);
        return modules;
    }

}
