package org.elasticsearch.swagger.v1_2.model.resourceListing;

import lombok.*;
import org.elasticsearch.swagger.v1_2.model.SwaggerModel;

/**
 * Provides details regarding the OAuth2's Authorization Endpoint.
 */
@Data
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class TokenRequestEndpoint extends SwaggerModel {
    /**
     * The URL of the authorization endpoint for the authentication code grant flow.
     */
    @NonNull
    private String url;

    /**
     * An optional alternative name to standard "client_id" OAuth2 parameter.
     */
    private String clientIdName;

    /**
     * An optional alternative name to standard "client_secret" OAuth2 parameter.
     */
    private String clientSecretName;
}