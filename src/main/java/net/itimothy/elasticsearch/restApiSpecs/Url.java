package net.itimothy.elasticsearch.restApiSpecs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Url {

    private String path;
    private List<String> paths = new ArrayList<String>();
    private Parts parts;
    private Map<String, Param> params;
}
