package net.itimothy.elasticsearch.restApiSpecs;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class Param {
    String name;
    String type;
    List<String> options;
    String description;
    @SerializedName("default")
    String defaultValue;
}