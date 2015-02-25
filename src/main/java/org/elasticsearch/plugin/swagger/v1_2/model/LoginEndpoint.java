package org.elasticsearch.plugin.swagger.v1_2.model;

import lombok.*;

/**
 * Provides details regarding the Implicit Grant's authorization endpoint.
 */
@Data
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
public class LoginEndpoint extends SwaggerModel {
    /**
     * The URL of the authorization endpoint for the implicit grant flow.
     */
    @NonNull
    private String url;

}