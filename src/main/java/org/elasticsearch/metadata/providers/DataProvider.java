package org.elasticsearch.metadata.providers;

import lombok.Getter;
import org.elasticsearch.action.admin.cluster.node.info.NodeInfo;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.hppc.cursors.ObjectCursor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Used to get lazy loaded, cached data from Elasticseach
 */
class DataProvider {
    @Getter
    private final Client client;

    private List<String> allIndices;
    private List<String> allAliases;
    private String version;
    private ArrayList<String> allDocumentTypes;

    public DataProvider(Client client) {
        this.client = client;
    }

    public List<String> getAllIndices() {

        if (allIndices == null) {
            allIndices = asList(
                client.admin().indices()
                    .prepareGetIndex()
                    .get()
                    .getIndices()
            );
        }

        return allIndices;
    }

    public List<String> getAllAliases() {
        if (allAliases == null) {
            allAliases = asList(
                client.admin().indices()
                    .prepareGetAliases()
                    .get()
                    .getAliases()
                    .keys()
                    .toArray(String.class)            
            );
        }

        return allAliases;
    }

    public String getVersion() {
        if (version == null) {
            NodeInfo[] nodes = client.admin().cluster().prepareNodesInfo()
                .get().getNodes();

            version = nodes[0].getVersion().number();
        }
        return version;
    }
    
    public List<String> getAllIndicesAliasesAndWildcardExpessions() {
        List<String> result = new ArrayList<>();

        result.addAll(getAllIndices());
        result.addAll(getAllAliases());
        result.sort(Comparator.comparing(s -> s));
        result.add("*");
        
        return result;        
    }

    public List<String> getAllDocumentTypes() {
        if (allDocumentTypes == null) {
            allDocumentTypes = new ArrayList<>();

            GetMappingsResponse getMappingsResponse = client.admin().indices().prepareGetMappings().get();

            ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>> indexMappings = getMappingsResponse.getMappings();
            for (ObjectCursor<String> index : indexMappings.keys()) {
                ImmutableOpenMap<String, MappingMetaData> typeMappings = indexMappings.get(index.value);
                for (ObjectCursor<String> type : typeMappings.keys()) {
                    if (!allDocumentTypes.contains(type.value)) {
                        allDocumentTypes.add(type.value);
                    }
                }
            }

            allDocumentTypes.sort(Comparator.<String>naturalOrder());
        }

        return allDocumentTypes;
    }
}
