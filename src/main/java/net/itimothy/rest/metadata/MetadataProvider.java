package net.itimothy.rest.metadata;

import net.itimothy.rest.metadata.model.Route;

import java.util.List;

public interface MetadataProvider {
    String getVersion();
    
    List<Route> getRoutes();
}
