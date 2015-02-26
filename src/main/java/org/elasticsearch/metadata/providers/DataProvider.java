package org.elasticsearch.metadata.providers;

import org.elasticsearch.client.Client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Used to get lazy loaded, cached data from Elasticseach
 */
class DataProvider {
    private Client client;
    private List<String> allIndices;
    private List<String> allAliases;

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
    
    public List<String> getAllIndicesAliasesAndWildcardExpessions() {
        List<String> result = new ArrayList<>();
        
        result.add("*");
        result.addAll(getAllIndices());
        result.addAll(getAllAliases());
        result.sort(Comparator.comparing(s -> s));
        
        return result;        
    }
}
