package org.elasticsearch.plugin.swagger.model;

import com.wordnik.swagger.core.util.JsonSerializer;

/**
 * The base class for all Swagger models with logic to serialize it according to the Swagger
 * schema.
 */
public class SwaggerModel {
    /**
     * Returns a valid JSON representation of the model, according to the Swagger schema.
     */
    public String toJson() {
        return JsonSerializer.asJson(this);
    }
}
