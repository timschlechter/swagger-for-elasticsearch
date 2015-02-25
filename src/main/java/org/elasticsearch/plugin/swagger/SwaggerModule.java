package org.elasticsearch.plugin.swagger;

import org.elasticsearch.common.inject.AbstractModule;
import org.elasticsearch.plugin.swagger.v1_2.rest.ApiDeclarationHandler;
import org.elasticsearch.plugin.swagger.v1_2.rest.ResourceListHandler;

public class SwaggerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ResourceListHandler.class).asEagerSingleton();
        bind(ApiDeclarationHandler.class).asEagerSingleton();
    }

}
