package org.elasticsearch.description;

import net.itimothy.rest.description.Parameter;
import net.itimothy.rest.description.ParameterType;
import net.itimothy.rest.description.Primitive;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

class ParametersFactory {
    private final DataProvider dataProvider;
    private ModelsCatalog modelsCatalog;

    public ParametersFactory(DataProvider dataProvider, ModelsCatalog modelsCatalog) {
        this.dataProvider = dataProvider;
        this.modelsCatalog = modelsCatalog;
    }

    public Parameter.ParameterBuilder buildIndexPathParam(String name) {
        return pathParam(name)
            .description("Name of the index")
            .allowMultiple(true);
    }

    public Parameter.ParameterBuilder typePathParam(String name) {
        return buildEnumPathParam(
            name,
            modelsCatalog.getTypeModels().stream()
                .map(m -> m.getName())
                .distinct()
                .collect(Collectors.toList())
        ).allowMultiple(true);
    }

    public Parameter.ParameterBuilder indexEnumPathParam(String name) {
        return buildEnumPathParam(name, dataProvider.getAllIndices());
    }

    public Parameter.ParameterBuilder buildEnumPathParam(String name, List<String> values) {
        return pathParam(name)
            ._enum(values);
    }

    public Parameter.ParameterBuilder indexAliasOrWildcardExpressionsPathParam(String name) {
        return buildIndexPathParam(name)
                .description("blank | * | _all | glob pattern | name1, name2, …")
                ._enum(dataProvider.getAllIndicesAliasesAndWildcardExpessions());
    }
    
    public Parameter.ParameterBuilder pathParam(String name) {
        return Parameter.builder()
            .paramType(ParameterType.PATH)
            .model(Primitive.STRING)
            .name(name);
    }
    
    public List<String> getFeatures() {
        return asList("_settings", "_mappings", "_warmers", "_aliases");
    }
}
