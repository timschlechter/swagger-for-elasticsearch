package net.itimothy.elasticsearch.description.restApiSpec.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Param {
    public String name;
    public String type;
    public List<String> options;
    public String description;
    @SerializedName("default")
    public Object defaultValue;
}