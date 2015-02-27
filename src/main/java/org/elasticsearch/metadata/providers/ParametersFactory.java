package org.elasticsearch.metadata.providers;

import org.elasticsearch.metadata.Parameter;
import org.elasticsearch.metadata.ParameterType;
import org.elasticsearch.metadata.Primitive;

import java.util.List;

import static java.util.Arrays.asList;

class ParametersFactory {
    private final DataProvider dataProvider;

    public ParametersFactory(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    public Parameter.ParameterBuilder buildIndexPathParam(String name) {
        return buildPathParam(name)
            .description("Name of the index")
            .allowMultiple(true);
    }

    public Parameter.ParameterBuilder builDocumentTypePathParam(String name) {
        return buildEnumPathParam(name, dataProvider.getAllDocumentTypes())
            .allowMultiple(true);
    }

    public Parameter.ParameterBuilder buildIndexEnumPathParam(String name) {
        return buildEnumPathParam(name, dataProvider.getAllIndices());
    }

    public Parameter.ParameterBuilder buildEnumPathParam(String name, List<String> values) {
        return buildPathParam(name)
            ._enum(values);
    }

    public Parameter.ParameterBuilder buildIndexAliasOrWildcardExpressionsPathParam(String name) {
        return buildIndexPathParam(name)
                .description("Name of the index, an alias or a wildcard expression")
                ._enum(dataProvider.getAllIndicesAliasesAndWildcardExpessions());
    }
    
    public Parameter.ParameterBuilder buildPathParam(String name) {
        return Parameter.builder()
            .paramType(ParameterType.PATH)
            .model(Primitive.STRING)
            .name(name);
    }
    
    public List<String> getFeatures() {
        return asList("_settings", "_mappings", "_warmers", "_aliases");
    }
}
