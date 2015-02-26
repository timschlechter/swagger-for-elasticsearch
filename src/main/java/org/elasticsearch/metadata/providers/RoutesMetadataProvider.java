package org.elasticsearch.metadata.providers;

import lombok.Getter;
import lombok.experimental.Delegate;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.lang3.StringUtils;
import org.elasticsearch.metadata.Route;

import java.util.List;

@Getter
public abstract class RoutesMetadataProvider {
    private final String version;
    private final Client client;

    @Delegate(types = ModelsCatalog.class)
    private final ModelsCatalog modelsCatalog;
    
    @Delegate(types = ParametersFactory.class)
    private final ParametersFactory parametersFactory;
    private DataProvider dataProvider;

    private final String defaultGroup;


    protected RoutesMetadataProvider(String defaultGroup, String version, Client client, ModelsCatalog modelsCatalog, ParametersFactory parametersFactory, DataProvider dataProvider) {
        this.version = version;
        this.client = client;
        this.modelsCatalog = modelsCatalog;
        this.defaultGroup = defaultGroup;
        this.parametersFactory = parametersFactory;
        this.dataProvider = dataProvider;
    }

    public List<Route> getRoutes() {
        List<Route> result = getRoutesInternal();
        
        for(Route route : result) {
            if (StringUtils.isBlank(route.getGroup())) {
                route.setGroup(defaultGroup);
            }
        }
        
        return result;
    }
    
    public abstract List<Route> getRoutesInternal();
}
