package net.itimothy.elasticsearch.plugin.swagger;

import org.elasticsearch.common.inject.AbstractModule;
import net.itimothy.elasticsearch.plugin.swagger.v1_2.rest.ApiDeclarationRequestHandler;
import net.itimothy.elasticsearch.plugin.swagger.v1_2.rest.ResourceListingRequestHandler;

public class SwaggerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ResourceListingRequestHandler.class).asEagerSingleton();
        bind(ApiDeclarationRequestHandler.class).asEagerSingleton();
    }

}
