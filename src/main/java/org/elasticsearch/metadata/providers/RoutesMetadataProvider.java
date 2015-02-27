package org.elasticsearch.metadata.providers;

import lombok.Getter;
import lombok.experimental.Delegate;
import org.elasticsearch.common.lang3.StringUtils;
import org.elasticsearch.metadata.Route;

import java.util.List;

public abstract class RoutesMetadataProvider {
    private DataProvider dataProvider;

    @Getter
    @Delegate(types = ModelsCatalog.class)
    private final ModelsCatalog modelsCatalog;

    @Getter
    @Delegate(types = ParametersFactory.class)
    private final ParametersFactory parametersFactory;

    private final String defaultGroup;

    protected RoutesMetadataProvider(String defaultGroup, ModelsCatalog modelsCatalog, ParametersFactory parametersFactory, DataProvider dataProvider) {
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

    public String getVersion() {
        return dataProvider.getVersion();
    }
}
