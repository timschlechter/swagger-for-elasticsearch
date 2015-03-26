package net.itimothy.elasticsearch.description.restApiSpec;

import com.google.gson.Gson;
import net.itimothy.elasticsearch.description.ModelsCatalog;
import net.itimothy.elasticsearch.description.RoutesProvider;
import net.itimothy.elasticsearch.description.restApiSpec.model.Api;
import net.itimothy.elasticsearch.description.restApiSpec.model.Param;
import net.itimothy.elasticsearch.description.restApiSpec.model.RestApiSpecData;
import net.itimothy.elasticsearch.description.util.CollectionUtil;
import net.itimothy.rest.description.*;
import org.elasticsearch.Version;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.base.Function;
import org.elasticsearch.common.lang3.StringUtils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

/**
 * Provides the routes information derived from the official Elasticsearch REST API specs
 */
public class OfficialRestApiSpecRoutesProvider extends RoutesProvider {
    public OfficialRestApiSpecRoutesProvider(Client client) {
        super(null, client, new ModelsCatalog(client));
    }

    protected Map<String, Api> getOfficialSpecData() {
        Map<String, Api> data = new HashMap<>();

        Version version = getElasticsearchVersion();

        URL url = OfficialRestApiSpecRoutesProvider.class.getResource(
            String.format(
                "/rest-api-spec/%s.%s",
                version.major,
                version.minor
            )
        );

        try {
            File dir = new File(url.toURI());
            Gson gson = new Gson();

            for (File file : dir.listFiles()) {
                String json = new String(readAllBytes(get(file.getPath())));

                RestApiSpecData restApiSpecData = gson.fromJson(json, RestApiSpecData.class);
                Map.Entry<String, Api> entry = restApiSpecData.entrySet().iterator().next();
                data.put(entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    @Override
    protected List<Route> getRoutesInternal() {
        Map<String, Api> officialSpecData = getOfficialSpecData();

        //
        // Convert to description model
        //
        List<Route> routes = new ArrayList<>();
        for (Map.Entry<String, Api> entry : officialSpecData.entrySet()) {
            String name = entry.getKey();
            Api api = entry.getValue();
            for (String method : api.methods) {
                for (String path : api.url.paths) {
                    routes.add(
                        Route.builder()
                            .name(name)
                            .group(getRouteGroup(name))
                            .description("<a target=\"_new\" href=\"" + api.documentation + "\">Documentation for " + name + "</a>")
                            .method(toMethod(method))
                            .apiPath(path.substring(1))
                            .parameters(
                                toParameters(api)
                            )
                            .build()
                    );
                }
            }
        }

        CollectionUtil.sort(routes, new Function<Route, Comparable>() {
            @Override
            public Comparable apply(Route route) {
                return route.getGroup();
            }
        });

        return routes;
    }

    private String getRouteGroup(String name) {
        name = name.replace('_', '.')
            .replace(".get.", ".")
            .replace(".put.", ".")
            .replace(".post.", ".")
            .replace(".delete.", ".")
            .replace(".", " ");

        String[] parts = name.split(" ");

        return parts.length > 1
            ? parts[0] + ": " + StringUtils.join(parts, " ", 1, parts.length)
            : name;
    }

    private List<Parameter> toParameters(Api api) {
        List<Parameter> parameters = new ArrayList<>();

        if (api.url.parts != null) {
            for (Map.Entry<String, Param> entry : api.url.parts.entrySet()) {
                String name = entry.getKey();
                Param param = entry.getValue();
                parameters.add(
                    Parameter.builder()
                        .name(name)
                        .description(param.description)
                        .defaultValue(param.defaultValue)
                        .model(toModel(name, param))
                        .paramType(ParamType.PATH)
                        .build()
                );
            }
        }

        if (api.url.params != null) {
            for (Map.Entry<String, Param> entry : api.url.params.entrySet()) {
                String name = entry.getKey();
                Param param = entry.getValue();
                parameters.add(
                    Parameter.builder()
                        .name(name)
                        .description(param.description)
                        .defaultValue(param.defaultValue)
                        .model(toModel(name, param))
                        .paramType(ParamType.QUERY)
                        .build()
                );
            }
        }

        if (api.body != null) {
            parameters.add(
                Parameter.builder()
                    .name("body")
                    .description(api.body.description)
                    .defaultValue(api.body.defaultValue)
                    .model(toModel("body", api.body))
                    .paramType(ParamType.BODY)
                    .build()
            );
        }

        return parameters;
    }

    private Model toModel(String name, Param param) {
        return Primitive.STRING;
    }

    private HttpMethod toMethod(String method) {
        switch (method) {
            case "GET":
                return HttpMethod.GET;
            case "DELETE":
                return HttpMethod.DELETE;
            case "PUT":
                return HttpMethod.PUT;
            case "POST":
                return HttpMethod.POST;
            case "HEAD":
                return HttpMethod.HEAD;
            case "OPTIONS":
                return HttpMethod.OPTIONS;
            case "PATCH":
                return HttpMethod.PATCH;
        }
        return HttpMethod.UNKNOWN;
    }
}
