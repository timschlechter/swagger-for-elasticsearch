package org.elasticsearch.swagger.v1_2.model.apiDeclaration;

import lombok.*;
import org.elasticsearch.swagger.v1_2.model.SwaggerModel;

/**
 * The Response Message Object describes a single possible response message that can be returned
 * from the operation call, and maps to the responses field in the Operation Object. Each
 * Response Message allows you to give further details as to why the HTTP status code may be
 * received.
 */
@Data
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class ResponseMessage extends SwaggerModel {
    /**
     * The HTTP status code returned.
     */
    @NonNull
    private Integer code;

    /**
     * The explanation for the status code.
     */
    @NonNull
    private String message;

    /**
     * The return model for the given response.
     */
    private String responseModel;
}