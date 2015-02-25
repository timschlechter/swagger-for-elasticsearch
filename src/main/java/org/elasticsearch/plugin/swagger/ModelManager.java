package org.elasticsearch.plugin.swagger;

import net.itimothy.rest.metadata.model.Model;

import static java.util.Arrays.asList;

public class ModelManager {
    public static final Model MAPPING = Model.builder()
        .id("mapping")
        .properties(asList(

        ))
        .build();

}
