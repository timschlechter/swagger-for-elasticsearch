package org.elasticsearch.description;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.hppc.cursors.ObjectCursor;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

/**
 * Used to get lazy loaded, cached data from Elasticseach
 */
class DataProvider {
    @Getter
    private final Client client;

    private List<String> allIndices;
    private List<String> allAliases;
    private ArrayList<String> allDocumentTypes;
    private DiscoveryNode currentNode;

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

    public DiscoveryNode getCurrentNode() {
        if (currentNode == null) {
            currentNode = client.admin().cluster().prepareNodesInfo()
                .get().getNodes()[0].getNode();
        }

        return currentNode;
    }

    public List<String> getAllIndicesAliasesAndWildcardExpessions() {
        List<String> result = new ArrayList<>();

        result.addAll(getAllIndices());
        result.addAll(getAllAliases());
        result.sort(Comparator.comparing(s -> s));
        result.add("*");
        
        return result;        
    }

    @Data
    @AllArgsConstructor
    public class MappingProperty {
        String name;
        String format;
    }
    
    @Data
    @AllArgsConstructor
    public class TypeMapping {
        String type;
        HashMap<String, MappingProperty> properties;
    }
    
    @Data
    @AllArgsConstructor
    public class IndexMappings {
        String index;        
        Map<String, TypeMapping> typeMappings;
    }

    Map<String, IndexMappings> allMappings;

    public List<TypeMapping> getMapping(String index) {
        return getAllMappings().values().stream()
            .flatMap(m -> m.getTypeMappings().values().stream())
            .distinct()
            .collect(Collectors.toList());
    }

    
    public Map<String, IndexMappings> getAllMappings() {
        if (allMappings == null) {
            allMappings = new HashMap<>();

            GetMappingsResponse getMappingsResponse = client.admin().indices().prepareGetMappings().get();

            ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>> indexMappings = getMappingsResponse.getMappings();
            for (ObjectCursor<String> index : indexMappings.keys()) {
                
                IndexMappings indexMapping = new IndexMappings(index.value, new HashMap<>());
                this.allMappings.put(indexMapping.getIndex(), indexMapping);
                
                ImmutableOpenMap<String, MappingMetaData> typeMappings = indexMappings.get(index.value);
                for (ObjectCursor<String> type : typeMappings.keys()) {
                    TypeMapping typeMapping = new TypeMapping(type.value, new HashMap<>());
                    indexMapping.getTypeMappings().put(typeMapping.getType(), typeMapping);

                    Map properties = null;
                    try {
                        properties = (Map)typeMappings.get(type.value).getSourceAsMap().get("properties");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    
                    for (Object propertyName : properties.keySet()) {
                        MappingProperty mappingProperty = new MappingProperty(
                            propertyName.toString(),
                            properties.containsKey("type")
                                ? properties.get("type").toString()
                                : "string"
                        );
                        
                        typeMapping.getProperties().put(mappingProperty.getName(), mappingProperty);
                    }
                }
            }
        }

        return allMappings;
    }

    public List<String> getAllDocumentTypes() {
        return getAllMappings().values().stream()
            .flatMap(m -> m.getTypeMappings().keySet().stream())
            .distinct()
            .collect(Collectors.toList());
    }
}
