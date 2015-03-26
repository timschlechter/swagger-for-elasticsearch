package net.itimothy.elasticsearch.description;

import net.itimothy.rest.description.*;
import org.elasticsearch.client.Client;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.lang3.StringUtils;
import net.itimothy.elasticsearch.description.util.SimpleCache;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import static java.util.Arrays.asList;

public abstract class RoutesProvider {

    private final Client client;
    private final ModelsCatalog modelsCatalog;
    private final String defaultGroup;
    private SimpleCache cache = new SimpleCache();

    protected RoutesProvider(String defaultGroup, Client client, ModelsCatalog modelsCatalog) {
        this.modelsCatalog = modelsCatalog;
        this.defaultGroup = defaultGroup;
        this.client = client;
    }

    protected Client getClient() {
        return client;
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

    protected abstract List<Route> getRoutesInternal();

    public Info getInfo() {
        DiscoveryNode currentNode = getCurrentNode();

        return Info.builder()
            .version(currentNode.getVersion().number())
            .title("Elasticsearch " + currentNode.getVersion().number() + " API")
            .build();
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
        List<String> typeModelNames = new ArrayList<>();
        for (Model typeModel : modelsCatalog.getTypeModels()) {
            if (!typeModelNames.contains(typeModel.getName())) {
                typeModelNames.add(typeModel.getName());
            }
        }

        Collections.sort(typeModelNames);

        return enumParam(name, paramType, typeModelNames)
            .allowMultiple(true);
    }

    public Parameter.ParameterBuilder fieldSelectParam(String name, ParamType paramType) {
        List<String> propertyNames = new ArrayList<>();
        for (Model typeModel : modelsCatalog.getTypeModels()) {
            if (typeModel.getProperties() != null) {
                for (Property property : typeModel.getProperties()) {
                    if (!propertyNames.contains(property.getName())) {
                        propertyNames.add(property.getName());
                    }
                }
            }
        }

        Collections.sort(propertyNames);

        return enumParam(name, paramType, propertyNames)
            .allowMultiple(true);
    }

    protected List<String> getAllIndices() {
        return cache.getOrResolve("getAllIndices",
            new Callable<List<String>>() {
                @Override
                public List<String> call() throws Exception {
                    List<String> allIndices = new ArrayList<String>();

                    allIndices.add("*");
                    allIndices.addAll(
                        asList(client.admin().indices()
                                .prepareGetIndex()
                                .get()
                                .getIndices()
                        )
                    );

                    Collections.sort(allIndices);

                    return allIndices;
                }
            }
        );
    }

    protected List<String> getAllAliases() {
        return cache.getOrResolve("getAllAliases",
            new Callable<List<String>>() {
                @Override
                public List<String> call() throws Exception {
                    List<String> allAliases = asList(
                        client.admin().indices()
                            .prepareGetAliases()
                            .get()
                            .getAliases()
                            .keys()
                            .toArray(String.class)
                    );

                    return allAliases;
                }
            }
        );
    }

    protected DiscoveryNode getCurrentNode() {
        return cache.getOrResolve("getCurrentNode",
            new Callable<DiscoveryNode>() {
                @Override
                public DiscoveryNode call() throws Exception {
                    return client.admin().cluster().prepareNodesInfo().get().getNodes()[0].getNode();
                }
            }
        );
    }

    protected List<String> getAllIndicesAndAliases() {
        return cache.getOrResolve("getAllIndicesAndAliases",
            new Callable<List<String>>() {
                @Override
                public List<String> call() throws Exception {
                    List<String> result = new ArrayList<>();

                    result.addAll(getAllIndices());
                    result.addAll(getAllAliases());
                    Collections.sort(result);

                    return result;
                }
            });
    }

    protected List<Parameter> defaultUriSearchParams() {
        return asList(
            queryParam("q")
                .description("The query string").build(),
            queryParam("df")
                .description("The default field to use when no field prefix is defined within the query").build(),
            queryParam("analyzer")
                .description("The analyzer name to be used when analyzing the query string").build(),
            queryParam("default_operator")
                .description("The default operator to be used, can be AND or OR. Defaults to OR").build(),
            queryParam("explain", Primitive.BOOLEAN)
                .defaultValue("false")
                .description("For each hit, contain an explanation of how scoring of the hits was computed").build(),
            queryParam("_source", Primitive.BOOLEAN)
                .defaultValue("true")
                .description("Set to false to disable retrieval of the _source field. You can also retrieve part of the document by using _source_include & _source_exclude (see the request body documentation for more details)").build(),
            queryParam("fields")
                .description("The selective stored fields of the document to return for each hit, comma delimited. Not specifying any value will cause no fields to return").build(),
            queryParam("sort")
                .description("Sorting to perform. Can either be in the form of fieldName, or fieldName:asc/fieldName:desc. The fieldName can either be an actual field within the document, or the special _score name to indicate sorting based on scores. There can be several sort parameters (order is important)").build(),
            queryParam("track_scores", Primitive.BOOLEAN)
                .defaultValue("false")
                .description("When sorting, set to true in order to still track scores and return them as part of each hit").build(),
            queryParam("timeout", Primitive.LONG)
                .description("A search timeout, bounding the search request to be executed within the specified time value and bail with the hits accumulated up to that point when expired. Defaults to no timeout").build(),
            queryParam("terminate_after", Primitive.LONG)
                .minVersion("1.4.0.Beta1")
                .description("The maximum number of documents to collect for each shard, upon reaching which the query execution will terminate early. If set, the response will have a boolean field terminated_early to indicate whether the query execution has actually terminated_early. Defaults to no terminate_after").build(),
            queryParam("from", Primitive.LONG)
                .description("The starting from index of the hits to return. Defaults to 0").build(),
            queryParam("size", Primitive.LONG)
                .description("The number of hits to return. Defaults to 10").build(),
            queryParam("search_type")
                .enumValues("dfs_query_then_fetch", "dfs_query_and_fetch", "query_then_fetch", "query_and_fetch", "count", "scan")
                .description("The type of the search operation to perform. Can be dfs_query_then_fetch, dfs_query_and_fetch, query_then_fetch, query_and_fetch, count, scan").build(),
            queryParam("lowercase_expanded_terms", Primitive.BOOLEAN)
                .defaultValue("true")
                .description("Should terms be automatically lowercased or not. Defaults to true").build(),
            queryParam("analyze_wildcard", Primitive.BOOLEAN)
                .defaultValue("false")
                .description("Should wildcard and prefix queries be analyzed or not. Defaults to false").build()
        );
    }
}
