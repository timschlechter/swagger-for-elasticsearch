package org.elasticsearch.plugin.swagger.model;

/**
 * The Response Message Object describes a single possible response message that can be returned
 * from the operation call, and maps to the responseMessages field in the Operation Object. Each
 * Response Message allows you to give further details as to why the HTTP status code may be
 * received.
 */
public class ResponseMessage extends SwaggerModel {
    /**
     * The HTTP status code returned.
     */
    private Integer code;

    /**
     * The explanation for the status code.
     */
    private String message;

    /**
     * The return type for the given response.
     */
    private String responseModel;
}