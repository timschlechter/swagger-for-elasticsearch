package org.elasticsearch.metadata.providers;

import org.elasticsearch.client.Client;
import org.elasticsearch.metadata.Model;

import static java.util.Arrays.asList;

public class ModelsCatalog {

    private String version;
    private Client client;

    public ModelsCatalog(String version, Client client) {
        this.version = version;
        this.client = client;
    }


    public static final Model MAPPING = Model.builder()
        .id("mapping")
        .properties(asList(

        ))
        .build();

}
