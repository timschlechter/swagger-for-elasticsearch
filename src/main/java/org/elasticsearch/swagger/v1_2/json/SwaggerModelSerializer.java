package org.elasticsearch.swagger.v1_2.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.elasticsearch.swagger.v1_2.model.SwaggerModel;

import java.util.List;
import java.util.function.Function;

abstract class SwaggerModelSerializer<T> implements JsonSerializer<T> {
    public static <T extends SwaggerModel> JsonElement serializeToObject(List<T> list, Function<T, String> keySelector, JsonSerializationContext context) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        JsonObject result = new JsonObject();

        for (T item : list) {
            result.add(keySelector.apply(item), context.serialize(item));
        }

        return result;
    }
}

