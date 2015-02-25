package org.elasticsearch.plugin.swagger.model.v1_2;

import org.elasticsearch.plugin.swagger.v1_2.model.HttpMethod;
import org.elasticsearch.plugin.swagger.v1_2.model.Operation;
import org.elasticsearch.plugin.swagger.v1_2.model.Parameter;
import org.elasticsearch.plugin.swagger.v1_2.model.ParameterType;
import org.junit.Test;

import static java.util.Arrays.asList;

public class ModelTests {
    @Test
    public void toJson() {
        Operation operation = Operation.builder()
            .method(HttpMethod.GET)
            .nickname("someOperation")
            .parameters(asList(
                Parameter.builder()
                    .paramType(ParameterType.path)
                    .name("someParam")
                    .build()
            ))
            .build();

        String actual = operation.toJson();
        org.junit.Assert.assertEquals("", actual);
    }
}
