package org.elasticsearch.swagger.v1_2.model;

import org.elasticsearch.swagger.v1_2.json.JsonConverter;

/**
 * The base class for all Swagger models with logic to serialize it according to the Swagger
 * schema.
 */
public abstract class SwaggerModel {
    /**
     * Returns a valid JSON representation of the model, according to the Swagger schema.
     */
    public String toJson() {
        return toJson(true);
    }

    /**
     * Returns a valid JSON representation of the model, according to the Swagger schema.
     */
    public String toJson(boolean prettyPrint) {
        return JsonConverter.convert(this, prettyPrint);
    }
}
