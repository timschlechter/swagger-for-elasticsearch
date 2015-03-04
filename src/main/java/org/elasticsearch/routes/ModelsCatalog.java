package org.elasticsearch.routes;

import net.itimothy.rest.description.Model;
import net.itimothy.rest.description.Primitive;
import net.itimothy.rest.description.Property;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.hppc.cursors.ObjectCursor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class ModelsCatalog {

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
    public static final Model MAPPING = Model.builder()
        .id("mapping")
        .properties(asList(
            Property.builder()
                .name("<type>")
                .model(
                    Model.builder()
                        .id("mapping-properties")
                        .properties(asList(
                            Property.builder()
                                .name("properties")
                                .model(MAPPING_PROPERTIES_PROPERTY)
                                .build()
                        )).build()
                ).build()
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
    public static final Model FILTER = Model.builder()
        .id("filter")
        .properties(asList(
        )).build();
    public static final Model ALIAS = Model.builder()
        .id("alias")
        .properties(asList(
            Property.builder()
                .name("filter")
                .model(FILTER).build(),
            Property.builder()
                .name("index_routing")
                .model(Primitive.STRING).build(),
            Property.builder()
                .name("search_routing")
                .model(Primitive.STRING).build()
        )).build();
    public static final Model ALIASES = Model.builder()
        .id("aliases")
        .properties(asList(
            Property.builder()
                .name("<alias>")
                .model(ALIAS).build()
        )).build();
    public static final Model INDEX_ALIASES = Model.builder()
        .id("index-aliases")
        .properties(asList(
            Property.builder()
                .name("<index>")
                .model(Model.builder()
                    .id("index-aliases-aliases")
                    .properties(asList(
                        Property.builder()
                            .name("aliases")
                            .model(ALIASES)
                            .build()
                    )).build()).build()
        )).build();
    public static final Model DOCUMENT_METADATA = Model.builder()
        .id("document-metadata")
        .properties(asList(
            Property.builder()
                .name("_index")
                .description("Index which this document is in")
                .required(true)
                .model(Primitive.STRING).build(),
            Property.builder()
                .name("_type")
                .description("Type of this document")
                .required(true)
                .model(Primitive.STRING).build(),
            Property.builder()
                .name("_id")
                .description("Unique identifier of this document within its index and type")
                .required(true)
                .model(Primitive.STRING).build(),
            Property.builder()
                .name("_version")
                .description("Version of the document")
                .required(true)
                .model(Primitive.LONG).build(),
            Property.builder()
                .name("_found")
                .description("Returns whether the document was found or not")
                .required(true)
                .model(Primitive.BOOLEAN).build()
        )).build();
    public static final Model SHARD_INFO = Model.builder()
        .id("shard-info")
        .properties(
            Property.builder()
                .name("total")
                .required(true)
                .model(Primitive.INTEGER).build(),
            Property.builder()
                .name("successful")
                .required(true)
                .model(Primitive.INTEGER).build(),
            Property.builder()
                .name("failed")
                .required(true)
                .model(Primitive.INTEGER).build()
        ).build();
    public static final Model HITS = Model.builder()
        .id("hits")
        .properties(
            Property.builder()
                .name("total")
                .required(true)
                .model(Primitive.INTEGER).build(),
            Property.builder()
                .name("hits")
                .required(true)
                .model(DOCUMENT_METADATA)
                .isCollection(true).build()
        ).build();
    public static final Model SEARCH_RESULT = Model.builder()
        .id("search-result")
        .properties(
            Property.builder()
                .name("shards")
                .required(true)
                .model(SHARD_INFO).build(),
            Property.builder()
                .name("hits")
                .required(true)
                .model(HITS).build()
        ).build();
    private final Client client;
    Map<String, List<Model>> indexTypeModelsMap;
    Map<String, List<Model>> indexDocumentModelsMap;
    Map<String, List<Model>> indexDocumentSearchResultModelsMap;
    private String indexOrAlias;


    public ModelsCatalog(Client client, String indexOrAlias) {
        this.client = client;
        this.indexOrAlias = indexOrAlias;
    }

    private static Model mappingTypeToModel(String mappingType) {
        switch (mappingType) {
            case "string":
                return Primitive.STRING;
            case "float":
                return Primitive.FLOAT;
            case "double":
                return Primitive.DOUBLE;
            case "byte":
                return Primitive.BYTE;
            case "short":
                return Primitive.SHORT;
            case "integer":
                return Primitive.INTEGER;
            case "long":
                return Primitive.LONG;
        }

        return Primitive.STRING;
    }

    private static String getTypeModelId(String index, String typeName) {
        return index + "." + typeName;
    }

    private static String getDocumentModelId(String index, String typeName) {
        return getTypeModelId(index, typeName) + "Document";
    }

    private static String getDocumentSearchResultModelId(String index, String typeName) {
        return getDocumentModelId(index, typeName) + "SearchResult";
    }

    public Map<String, List<Model>> getIndexTypeModelsMap() {
        if (indexTypeModelsMap == null) {
            indexTypeModelsMap = new HashMap<>();

            GetMappingsResponse getMappingsResponse = client.admin().indices().prepareGetMappings().get();

            ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>> indexTypeMappings = getMappingsResponse.getMappings();
            for (ObjectCursor<String> indexCursor : indexTypeMappings.keys()) {
                String indexName = indexCursor.value;
                List<Model> typeModels = new ArrayList<>();
                indexTypeModelsMap.put(indexName, typeModels);

                ImmutableOpenMap<String, MappingMetaData> typeMappings = indexTypeMappings.get(indexName);
                for (ObjectCursor<String> typeCursor : typeMappings.keys()) {
                    String typeName = typeCursor.value;

                    Map mappingProperties = null;
                    try {
                        mappingProperties = (Map) typeMappings.get(typeCursor.value).getSourceAsMap().get("properties");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    List<Property> properties = new ArrayList<>();
                    for (Object propertyNameObj : mappingProperties.keySet()) {
                        String propertyName = propertyNameObj.toString();
                        if (mappingProperties.containsKey(propertyName)) {
                            Map fieldMapping = (Map) mappingProperties.get(propertyName);
                            String mappingType = fieldMapping.containsKey("type") ? fieldMapping.get("type").toString() : "string";
                            properties.add(
                                Property.builder()
                                    .name(propertyName.toString())
                                    .model(mappingType != null ? mappingTypeToModel(mappingType) : null).build()
                            );
                        }
                    }

                    typeModels.add(
                        Model.builder()
                            .id(getTypeModelId(indexName, typeName))
                            .name(typeName)
                            .properties(properties)
                            .build()
                    );
                }
            }
        }

        return indexTypeModelsMap;
    }

    public Map<String, List<Model>> getIndexDocumentModelsMap() {
        if (indexDocumentModelsMap == null) {
            indexDocumentModelsMap = getIndexTypeModelsMap().entrySet().stream()
                .collect(
                    Collectors.toMap(
                        e -> e.getKey(),
                        e -> {
                            String indexName = e.getKey();
                            List<Model> typeModels = e.getValue();

                            return typeModels.stream()
                                .map(m -> Model.builder()
                                        .id(getDocumentModelId(indexName, m.getName()))
                                        .properties(asList(
                                            Property.builder()
                                                .name("_index")
                                                .description("Index which this document is in")
                                                .required(true)
                                                .model(Primitive.STRING).build(),
                                            Property.builder()
                                                .name("_type")
                                                .description("Type of this document")
                                                .required(true)
                                                .model(Primitive.STRING).build(),
                                            Property.builder()
                                                .name("_id")
                                                .description("Unique identifier of this document within its index and type")
                                                .required(true)
                                                .model(Primitive.STRING).build(),
                                            Property.builder()
                                                .name("_version")
                                                .description("Version of the document")
                                                .required(true)
                                                .model(Primitive.LONG).build(),
                                            Property.builder()
                                                .name("_found")
                                                .description("Returns whether the document was found or not")
                                                .required(true)
                                                .model(Primitive.BOOLEAN).build(),
                                            Property.builder()
                                                .name("_source")
                                                .required(false)
                                                .description("Contains the actual document when found")
                                                .model(m).build()
                                        )).build()
                                ).collect(Collectors.toList());
                        }
                    )
                );
        }

        return indexDocumentModelsMap;
    }

    public Map<String, List<Model>> getIndexDocumentSearchResultModelsMap() {
        if (indexDocumentSearchResultModelsMap == null) {
            indexDocumentSearchResultModelsMap = getIndexTypeModelsMap().entrySet().stream()
                .collect(
                    Collectors.toMap(
                        e -> e.getKey(),
                        e -> {
                            String indexName = e.getKey();
                            List<Model> typeModels = e.getValue();

                            return typeModels.stream()
                                .map(m ->
                                        Model.builder()
                                            .id(getDocumentSearchResultModelId(indexName, m.getName()))
                                            .properties(
                                                Property.builder()
                                                    .name("shards")
                                                    .required(true)
                                                    .model(SHARD_INFO).build(),
                                                Property.builder()
                                                    .name("hits")
                                                    .required(true)
                                                    .model(
                                                        Model.builder()
                                                            .id("hits")
                                                            .properties(
                                                                Property.builder()
                                                                    .name("total")
                                                                    .required(true)
                                                                    .model(Primitive.INTEGER).build(),
                                                                Property.builder()
                                                                    .name("hits")
                                                                    .required(true)
                                                                    .model(getDocumentModel(m))
                                                                    .isCollection(true).build()
                                                            ).build()
                                                    ).build()
                                            ).build()
                                ).collect(Collectors.toList());
                        }
                    )
                );
        }

        return indexDocumentSearchResultModelsMap;
    }

    public List<Model> getTypeModels() {
        return getIndexTypeModelsMap().values().stream()
            .flatMap(m -> m.stream())
            .collect(Collectors.toList());
    }

    public List<Model> getDocumentModels() {
        return getIndexDocumentModelsMap().values().stream()
            .flatMap(m -> m.stream())
            .collect(Collectors.toList());
    }

    public Model getTypeModel(String index, String typeName) {
        if (getIndexTypeModelsMap().containsKey(index)) {
            return getIndexTypeModelsMap().get(index).stream()
                .filter(m -> m.getId().equals(getTypeModelId(index, typeName)))
                .findFirst()
                .orElse(null);
        }

        return null;
    }

    public Model getDocumentModel(String index, String typeName) {
        if (getIndexDocumentModelsMap().containsKey(index)) {
            return getIndexDocumentModelsMap().get(index).stream()
                .filter(m -> m.getId().equals(getDocumentModelId(index, typeName)))
                .findFirst()
                .orElse(null);
        }

        return null;
    }

    public Model getDocumentModel(Model typeModel) {
        String indexName = getIndexTypeModelsMap().entrySet().stream()
            .filter(e -> e.getValue().contains(typeModel))
            .map(e -> e.getKey())
            .findFirst()
            .orElse(null);

        return getDocumentModel(indexName, typeModel.getName());
    }

    public Model getDocumentSearchResultModel(String index, String typeName) {
        if (getIndexDocumentSearchResultModelsMap().containsKey(index)) {
            return getIndexDocumentSearchResultModelsMap().get(index).stream()
                .filter(m -> m.getId().equals(getDocumentSearchResultModelId(index, typeName)))
                .findFirst()
                .orElse(null);
        }

        return null;
    }

    public Model getDocumentSearchResultModel(Model typeModel) {
        String indexName = getIndexTypeModelsMap().entrySet().stream()
            .filter(e -> e.getValue().contains(typeModel))
            .map(e -> e.getKey())
            .findFirst()
            .orElse(null);

        return getDocumentSearchResultModel(indexName, typeModel.getName());
    }
}
