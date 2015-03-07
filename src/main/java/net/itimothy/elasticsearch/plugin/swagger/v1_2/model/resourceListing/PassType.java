package net.itimothy.elasticsearch.plugin.swagger.v1_2.model.resourceListing;

/**
 * Used in Authorization to denote how an API key should be passed.
 */
public enum PassType {
    /**
     * Denotes a header value.
     */
    header,

    /**
     * Denotes a query value.
     */
    query
}