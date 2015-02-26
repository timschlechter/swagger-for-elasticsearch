package org.elasticsearch.swagger.v1_2.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import org.elasticsearch.swagger.v1_2.model.resourceListing.ResourceListing;

import java.lang.reflect.Type;

class ResourceListingSerializer extends SwaggerModelSerializer<ResourceListing> {
    public JsonElement serialize(ResourceListing src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }
}
