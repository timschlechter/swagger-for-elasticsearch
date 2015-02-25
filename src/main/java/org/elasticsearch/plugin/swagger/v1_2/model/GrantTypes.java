package org.elasticsearch.plugin.swagger.v1_2.model;

import lombok.*;

/**
 * Provides details regarding the OAuth2 grant types that are supported by the API.
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class GrantTypes extends SwaggerModel {
    /**
     * The Implicit Grant flow definition.
     */
    private ImplicitGrantFlow implicit;

    /**
     * The Authorization Code Grant flow definition.
     */
    private AuthorizationCodeGrantFlow authorization_code;
}