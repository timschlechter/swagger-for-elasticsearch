package org.elasticsearch.plugin.swagger;

import org.elasticsearch.common.inject.AbstractModule;
import org.elasticsearch.plugin.swagger.handlers.ApiDeclarationHandler;
import org.elasticsearch.plugin.swagger.handlers.ResourceListHandler;

public class SwaggerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ResourceListHandler.class).asEagerSingleton();
        bind(ApiDeclarationHandler.class).asEagerSingleton();
    }

}
