package net.itimothy.elasticsearch.plugin.swagger.v1_2.model;

import com.google.gson.GsonBuilder;

/**
 * The base class for all Swagger models with logic to serialize it according to the Swagger
 * schema.
 */
public abstract class SwaggerModel {
    /**
     * Returns a valid JSON representation of the model, according to the Swagger schema.
     */
    public String toJson() {
        GsonBuilder gson = new GsonBuilder();
        gson.setPrettyPrinting();
        return gson.create().toJson(this);
    }
}
