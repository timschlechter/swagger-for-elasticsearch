package net.itimothy.elasticsearch.plugin.swagger;

import net.itimothy.elasticsearch.plugin.swagger.rest.CategoriesRequestHandler;
import net.itimothy.elasticsearch.routes.ModelsCatalog;
import net.itimothy.elasticsearch.restapispec.OfficialRestApiSpecDataProvider;
import org.elasticsearch.common.inject.AbstractModule;
import net.itimothy.elasticsearch.plugin.swagger.v1_2.rest.ApiDeclarationRequestHandler;
import net.itimothy.elasticsearch.plugin.swagger.v1_2.rest.ResourceListingRequestHandler;

public class SwaggerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(CategoriesRequestHandler.class).asEagerSingleton();

        bind(ResourceListingRequestHandler.class).asEagerSingleton();
        bind(ApiDeclarationRequestHandler.class).asEagerSingleton();

        bind(ModelsCatalog.class).asEagerSingleton();
        bind(OfficialRestApiSpecDataProvider.class).asEagerSingleton();
    }

}
