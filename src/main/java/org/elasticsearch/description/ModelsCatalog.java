package org.elasticsearch.description;

import net.itimothy.rest.description.Model;
import net.itimothy.rest.description.Primitive;
import net.itimothy.rest.description.Property;

import static java.util.Arrays.asList;

public class ModelsCatalog {

    private final DataProvider dataProvider;

    public ModelsCatalog(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    public static final Model MAPPING_PROPERTY = Model.builder()
        .id("mapping-property")
        .properties(asList(
            Property.builder()
                .name("type")
                .model(Primitive.STRING).build(),
            Property.builder()
                .name("format")
                .model(Primitive.STRING).build(),
            Property.builder()
                .name("analyzer")
                .model(Primitive.STRING).build(),
            Property.builder()
                .name("index_analyzer")
                .model(Primitive.STRING).build(),
            Property.builder()
                .name("search_analyzer")
                .model(Primitive.STRING).build(),
            Property.builder()
                .name("store")
                .model(Primitive.BOOLEAN).build()
        )).build();

    public static final Model MAPPING_PROPERTIES_PROPERTY = Model.builder()
        .id("mapping-properties-property")
        .properties(asList(
            Property.builder()
                .name("<property>")
                .model(MAPPING_PROPERTY)
                .build()
        )).build();

    public static final Model MAPPING_PROPERTIES = Model.builder()
        .id("mapping-properties")
        .properties(asList(
            Property.builder()
                .name("properties")
                .model(MAPPING_PROPERTIES_PROPERTY)
                .build()
        )).build();

    public static final Model MAPPING = Model.builder()
        .id("mapping")
        .properties(asList(
            Property.builder()
                .name("<type>")
                .model(MAPPING_PROPERTIES).build()
        )).build();

    public static final Model INDEX_MAPPINGS = Model.builder()
        .id("index-mappings")
        .properties(asList(
            Property.builder()
                .name("<index>")
                .model(MAPPING).build()
        )).build();

    public static final Model FEATURES = Model.builder()
        .id("features")
        .properties(asList(
            Property.builder()
                .name("mappings")
                .model(MAPPING).build()
        )).build();

    public static final Model INDEX_FEATURES = Model.builder()
        .id("index-features")
        .properties(asList(
            Property.builder()
                .name("<index>")
                .model(FEATURES).build()
        )).build();

    public static final Model OBJECT = Model.builder()
        .id("object")
        .properties(asList(
        )).build();
}
