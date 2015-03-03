package org.elasticsearch.description;

import org.elasticsearch.client.Client;
import org.elasticsearch.cluster.node.DiscoveryNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Used to get lazy loaded, cached data from Elasticseach
 */
class DataProvider {
    private final Client client;
    private List<String> allIndices;
    private List<String> allAliases;
    private DiscoveryNode currentNode;

    public DataProvider(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public List<String> getAllIndices() {
        if (allIndices == null) {
            allIndices = new ArrayList<>();

            allIndices.add("*");
            allIndices.addAll(
                Arrays.asList(client.admin().indices()
                        .prepareGetIndex()
                        .get()
                        .getIndices()
                )
            );

            allIndices.sort(Comparator.naturalOrder());
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

    public List<String> getAllIndicesAndAliases() {
        List<String> result = new ArrayList<>();

        result.addAll(getAllIndices());
        result.addAll(getAllAliases());
        result.sort(Comparator.comparing(s -> s));
        
        return result;        
    }
}
