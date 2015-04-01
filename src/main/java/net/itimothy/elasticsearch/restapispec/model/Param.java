package net.itimothy.elasticsearch.restapispec.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Param {
    public String name;
    public String type;
    public List<String> options;
    public String description;
    public boolean required;
    @SerializedName("default")
    public Object defaultValue;
}