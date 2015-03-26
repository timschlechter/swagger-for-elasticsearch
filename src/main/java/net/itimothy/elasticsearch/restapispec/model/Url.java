package net.itimothy.elasticsearch.restapispec.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Url {
    public String path;
    public List<String> paths = new ArrayList<>();
    public Map<String, Param> parts;
    public Map<String, Param> params;
}
