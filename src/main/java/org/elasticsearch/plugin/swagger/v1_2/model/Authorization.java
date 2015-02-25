package org.elasticsearch.plugin.swagger.v1_2.model;

import lombok.*;

import java.util.List;

/**
 * The Authorization object provides information about a specific authorization scheme.
 */
@Data
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Authorization extends SwaggerModel {
    /**
     * The model of the authorization scheme.
     */
    @NonNull
    private AuthorizationType type;

    /**
     * Denotes how the API key must be passed.
     */
    @NonNull
    private PassType passAs;

    /**
     * The name of the header or query parameter to be used when passing the API key.
     */
    @NonNull
    private String keyname;

    /**
     * A list of supported OAuth2 scopes.
     */
    private List<OAuth2Scope> scopes;

    /**
     * Detailed information about the grant types supported by the oauth2 authorization scheme.
     */
    private GrantTypes grantTypes;
}