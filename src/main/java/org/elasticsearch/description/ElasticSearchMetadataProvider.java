package org.elasticsearch.description;

import net.itimothy.rest.description.*;
import org.elasticsearch.client.Client;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.lang3.StringUtils;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public abstract class ElasticSearchMetadataProvider {

    private final Client client;
    private final String indexOrAlias;
    private final ModelsCatalog modelsCatalog;
    private final String defaultGroup;

    protected ElasticSearchMetadataProvider(String defaultGroup, Client client, ModelsCatalog modelsCatalog, String indexOrAlias) {
        this.modelsCatalog = modelsCatalog;
        this.defaultGroup = defaultGroup;
        this.client = client;
        this.indexOrAlias = indexOrAlias;
    }

    protected String getIndexOrAlias() {
        return indexOrAlias;
    }

    protected ModelsCatalog getModelsCatalog() {
        return modelsCatalog;
    }

    public List<Route> getRoutes() {
        List<Route> result = getRoutesInternal();

        for (Route route : result) {
            if (StringUtils.isBlank(route.getGroup())) {
                route.setGroup(defaultGroup);
            }
        }

        // TODO: modify the result to remove every Description object which does not matches 
        //       the current node's version of Elasticsearch

        return result;
    }

    public abstract List<Route> getRoutesInternal();

    public Info getInfo() {
        DiscoveryNode currentNode = getCurrentNode();

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

    public Parameter.ParameterBuilder param(String name, ParamType paramType) {
        return Parameter.builder()
            .name(name)
            .paramType(paramType)
            .model(Primitive.STRING);
    }

    public Parameter.ParameterBuilder pathParam(String name) {
        return param(name, ParamType.PATH);
    }

    public Parameter.ParameterBuilder bodyParam(Model model) {
        return param("body", ParamType.BODY)
            .model(model);
    }

    public Parameter.ParameterBuilder queryParam(String name) {
        return param(name, ParamType.QUERY);
    }

    public Parameter.ParameterBuilder queryParam(String name, Model model) {
        return queryParam(name)
            .model(model);
    }

    public Parameter.ParameterBuilder indexParam(String name, ParamType paramType) {
        return param(name, paramType)
            .description("Name of the index")
            .allowMultiple(true);
    }

    public Parameter.ParameterBuilder indexSelectParam(String name, ParamType paramType) {
        return indexParam(name, paramType)
            .enumValues(getAllIndices());
    }

    public Parameter.ParameterBuilder indexOrAliasParam(String name, ParamType paramType) {
        return param(name, paramType)
            .description("blank | * | _all | glob pattern | name1, name2, …")
            .allowMultiple(true);
    }

    public Parameter.ParameterBuilder indexOrAliasSelectParam(String name, ParamType paramType) {
        return indexParam(name, paramType)
            .enumValues(getAllIndicesAndAliases());
    }

    public Parameter.ParameterBuilder enumParam(String name, ParamType paramType, List<String> values) {
        return param(name, paramType)
            .enumValues(values);
    }

    public Parameter.ParameterBuilder enumParam(String name, ParamType paramType, String... values) {
        return enumParam(name, paramType, asList(values));
    }

    public Parameter.ParameterBuilder typeSelectParam(String name, ParamType paramType) {
        return enumParam(
            name,
            paramType,
            modelsCatalog.getTypeModels().stream()
                .map(m -> m.getName())
                .distinct()
                .collect(Collectors.toList())
        ).allowMultiple(true);
    }

    private Map<String, Object> cache = new HashMap<>();
    protected <T> T getOrResolve(String cacheKey, Supplier<T> resolveFn) {
        if (!cache.containsKey(cacheKey)) {
            cache.put(cacheKey, resolveFn.get());
        }
        
        return (T)cache.get(cacheKey);
    }
    
    protected List<String> getAllIndices() {
        return getOrResolve("getAllIndices", () -> {
            List<String> allIndices = new ArrayList<>();

            allIndices.add("*");
            allIndices.addAll(
                asList(client.admin().indices()
                        .prepareGetIndex()
                        .get()
                        .getIndices()
                )
            );

            allIndices.sort(Comparator.naturalOrder());

            return allIndices;
        });
    }

    protected List<String> getAllAliases() {
        return getOrResolve("getAllAliases", () -> {
            List<String> allAliases = asList(
                client.admin().indices()
                    .prepareGetAliases()
                    .get()
                    .getAliases()
                    .keys()
                    .toArray(String.class)
            );

            return allAliases;
        });
    }

    protected DiscoveryNode getCurrentNode() {
        return getOrResolve("getCurrentNode", () -> client.admin().cluster().prepareNodesInfo().get().getNodes()[0].getNode());
    }

    protected List<String> getAllIndicesAndAliases() {
        List<String> result = new ArrayList<>();

        result.addAll(getAllIndices());
        result.addAll(getAllAliases());
        result.sort(Comparator.comparing(s -> s));

        return result;
    }
}
