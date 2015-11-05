package net.itimothy.elasticsearch.routes;

import com.carrotsearch.hppc.cursors.ObjectCursor;
import com.google.common.base.MoreObjects;
import net.itimothy.elasticsearch.routes.model.Model;
import net.itimothy.elasticsearch.routes.model.Primitive;
import net.itimothy.elasticsearch.routes.model.Property;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import net.itimothy.util.SimpleCache;
import org.elasticsearch.common.inject.Inject;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;

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
        .id("object").build();
    public static final Model FILTER = Model.builder()
        .id("filter").build();
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
    private SimpleCache cache = new SimpleCache();

    @Inject
    public ModelsCatalog(Client client) {
        this.client = client;
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

    protected IndexModelsMap getIndexTypeModelsMap() {
        return cache.getOrResolve("getIndexTypeModelsMap",
            new Callable<IndexModelsMap>() {
                @Override
                public IndexModelsMap call() throws Exception {
                    IndexModelsMap result = new IndexModelsMap();

                    GetMappingsResponse getMappingsResponse = client.admin().indices().prepareGetMappings().get();

                    ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>> indexTypeMappings = getMappingsResponse.getMappings();
                    for (ObjectCursor<String> indexCursor : indexTypeMappings.keys()) {
                        String indexName = indexCursor.value;
                        List<Model> typeModels = new ArrayList<>();
                        result.put(indexName, typeModels);

                        ImmutableOpenMap<String, MappingMetaData> typeMappings = indexTypeMappings.get(indexName);
                        for (ObjectCursor<String> typeCursor : typeMappings.keys()) {
                            String typeName = typeCursor.value;

                            Map mappingProperties;
                            try {
                                mappingProperties = (Map) typeMappings.get(typeCursor.value).getSourceAsMap().get("properties");
                            } catch (IOException e) {
                                mappingProperties = Collections.emptyMap();
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
                                            .name(propertyName)
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
                    return result;
                }
            }
        );
    }

    protected IndexModelsMap getIndexDocumentModelsMap() {
        return cache.getOrResolve("getIndexDocumentModelsMap",
            new Callable<IndexModelsMap>() {
                @Override
                public IndexModelsMap call() throws Exception {
                    IndexModelsMap result = new IndexModelsMap();

                    for (Map.Entry<String, List<Model>> entry : getIndexTypeModelsMap().entrySet()) {
                        String indexName = entry.getKey();
                        List<Model> typeModels = entry.getValue();
                        List<Model> documentModels = new ArrayList<>();

                        for (Model typeModel : typeModels) {
                            documentModels.add(typeModelToDocumentModel(indexName, typeModel));
                        }

                        result.put(indexName, documentModels);
                    }

                    return result;
                }
            }
        );
    }

    protected IndexModelsMap getIndexDocumentSearchResultModelsMap() {
        return cache.getOrResolve("getIndexDocumentSearchResultModelsMap",
            new Callable<IndexModelsMap>() {
                @Override
                public IndexModelsMap call() throws Exception {
                    IndexModelsMap result = new IndexModelsMap();

                    for (Map.Entry<String, List<Model>> entry : getIndexTypeModelsMap().entrySet()) {
                        String indexName = entry.getKey();
                        List<Model> typeModels = entry.getValue();
                        List<Model> documentModels = new ArrayList<>();

                        for (Model typeModel : typeModels) {
                            documentModels.add(typeModelToDocumentSearchResultModel(indexName, typeModel));
                        }

                        result.put(indexName, documentModels);
                    }

                    return result;
                }
            }
        );
    }

    public List<Model> getIndexTypeModels(String indexName) {
        return MoreObjects.firstNonNull(
            getIndexTypeModelsMap().get(indexName),
            Collections.<Model>emptyList()
        );
    }

    public List<Model> getTypeModels() {
        return getIndexTypeModelsMap().getAllModels();
    }

    public Model getDocumentModel(String index, String typeName) {
        return getIndexDocumentModelsMap().getModel(getDocumentModelId(index, typeName));
    }

    public Model getDocumentModel(Model typeModel) {
        return getDocumentModel(
            getIndexTypeModelsMap().getIndexName(typeModel),
            typeModel.getName()
        );
    }

    public Model getDocumentSearchResultModel(String index, String typeName) {
        return getIndexDocumentSearchResultModelsMap().getModel(getDocumentSearchResultModelId(index, typeName));
    }

    public Model getDocumentSearchResultModel(Model typeModel) {
        return getDocumentSearchResultModel(
            getIndexTypeModelsMap().getIndexName(typeModel),
            typeModel.getName()
        );
    }

    /**
     * Creates a document representation of the given Type model
     *
     * @param indexName Name of the index the type is in
     * @param typeModel Type's model representation
     * @return a Document representation of the given Type model
     */
    protected Model typeModelToDocumentModel(String indexName, Model typeModel) {
        return Model.builder()
            .id(getDocumentModelId(indexName, typeModel.getName()))
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
                    .model(typeModel).build()
            )).build();
    }

    /**
     * Creates a document search result representation of the given Type model
     *
     * @param indexName Name of the index the type is in
     * @param typeModel Type's model representation
     * @return a Document Search Result representation of the given Type model
     */
    protected Model typeModelToDocumentSearchResultModel(String indexName, Model typeModel) {
        return Model.builder()
            .id(getDocumentSearchResultModelId(indexName, typeModel.getName()))
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
                                    .model(getDocumentModel(typeModel))
                                    .isCollection(true).build()
                            ).build()
                    ).build()
            ).build();
    }

    private class IndexModelsMap extends HashMap<String, List<Model>> {
        public List<Model> getAllModels() {
            List<Model> result = new ArrayList<>();
            for (List<Model> models : values()) {
                result.addAll(models);
            }
            return result;
        }

        public Model getModel(String id) {
            for (Model model : getAllModels()) {
                if (model.getId().equals(id)) {
                    return model;
                }
            }
            return null;
        }

        public String getIndexName(Model model) {
            for (Map.Entry<String, List<Model>> entry : this.entrySet()) {
                if (entry.getValue().contains(model)) {
                    return entry.getKey();
                }
            }
            return null;
        }
    }
}
