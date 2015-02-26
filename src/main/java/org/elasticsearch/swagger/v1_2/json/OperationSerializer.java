package org.elasticsearch.swagger.v1_2.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import org.elasticsearch.swagger.v1_2.model.apiDeclaration.Operation;

import java.lang.reflect.Type;

class OperationSerializer extends SwaggerModelSerializer<Operation> {
    public JsonElement serialize(Operation operation, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();

        result.add("type", context.serialize(operation.getType()));
        result.add("method", context.serialize(operation.getMethod()));
        result.add("nickname", context.serialize(operation.getNickname()));
        result.add("parameters", serializeToObject(operation.getParameters(), p -> p.getName(), context));
        result.add("summary", context.serialize(operation.getSummary()));
        result.add("notes", context.serialize(operation.getNotes()));
        result.add("authorizations", context.serialize(operation.getAuthorizations()));
        result.add("responses", context.serialize(operation.getResponseMessages()));
        result.add("produces", context.serialize(operation.getProduces()));
        result.add("consumes", context.serialize(operation.getConsumes()));
        result.add("deprecated", context.serialize(operation.getDeprecated()));

        return result;
    }
}
