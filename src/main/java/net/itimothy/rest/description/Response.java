package net.itimothy.rest.description;

import lombok.Builder;
import lombok.Data;

/**
 * The Response Message Object describes a single possible response message that can be returned
 * from the operation call, and maps to the responses field in the Operation Object. Each
 * Response Message allows you to give further details as to why the HTTP status code may be
 * received.
 */
@Data
@Builder
public class Response {
    /**
     * The HTTP status code returned.
     */
    private Integer code;

    /**
     * The explanation for the status code.
     */
    private String message;

    /**
     * The return model for the given response.
     */
    private Model model;
}