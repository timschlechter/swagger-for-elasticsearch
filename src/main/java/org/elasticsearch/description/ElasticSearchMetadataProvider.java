package org.elasticsearch.description;

import lombok.Getter;
import lombok.experimental.Delegate;
import net.itimothy.rest.description.Info;
import net.itimothy.rest.description.Route;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.lang3.StringUtils;

import java.util.List;

@Getter
public abstract class ElasticSearchMetadataProvider {
    private DataProvider dataProvider;
    private String indexOrAlias;
    private final ModelsCatalog modelsCatalog;
    private final String defaultGroup;

    @Delegate(types = ParametersFactory.class)
    private final ParametersFactory parametersFactory;


    protected ElasticSearchMetadataProvider(String defaultGroup, ModelsCatalog modelsCatalog, ParametersFactory parametersFactory, DataProvider dataProvider, String indexOrAlias) {
        this.modelsCatalog = modelsCatalog;
        this.defaultGroup = defaultGroup;
        this.parametersFactory = parametersFactory;
        this.dataProvider = dataProvider;
        this.indexOrAlias = indexOrAlias;
    }

    public List<Route> getRoutes() {
        List<Route> result = getRoutesInternal();

        for (Route route : result) {
            if (StringUtils.isBlank(route.getGroup())) {
                route.setGroup(defaultGroup);
            }
        }

        return result;
    }

    public abstract List<Route> getRoutesInternal();

    public Info getInfo() {
        DiscoveryNode currentNode = dataProvider.getCurrentNode();

        return Info.builder()
            .version(currentNode.getVersion().number())
            .title(
                "Elasticsearch " + currentNode.getVersion().number() + " API" +
                    (indexOrAlias != null ? " for index/alias " + indexOrAlias : "")
            )
            .description(
                String.format("Hostname: %s\n\nNode: %s",
                    currentNode.getHostName(),
                    currentNode.getName()
                )
            )
            .build();
    }

    protected String indexOrAliasPrepended(String apiPath) {
        return getIndexOrAlias() != null ? getIndexOrAlias() + "/" + apiPath : apiPath;
    }
}
