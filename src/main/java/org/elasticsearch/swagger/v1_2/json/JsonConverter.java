package org.elasticsearch.swagger.v1_2.json;

import com.google.gson.GsonBuilder;
import org.elasticsearch.swagger.v1_2.model.SwaggerModel;

public class JsonConverter {
    public static String convert(SwaggerModel model) {
        return convert(model, true);
    }

    public static String convert(SwaggerModel model, boolean prettyPrint) {
        GsonBuilder gson = new GsonBuilder();
        //gson.registerTypeAdapter(Operation.class, new OperationSerializer());
        return gson.create().toJson(model);
    }
}
