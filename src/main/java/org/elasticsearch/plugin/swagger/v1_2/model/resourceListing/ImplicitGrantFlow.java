package org.elasticsearch.plugin.swagger.v1_2.model.resourceListing;

import lombok.*;
import org.elasticsearch.plugin.swagger.v1_2.model.SwaggerModel;

/**
 * Provides details regarding the OAuth2's Implicit Grant flow model.
 */
@Data
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class ImplicitGrantFlow extends SwaggerModel {
    /**
     * The login endpoint definition.
     */
    @NonNull
    private LoginEndpoint loginEndpoint;

    /**
     * An optional alternative name to standard "access_token" OAuth2 parameter.
     */
    private String tokenName;
}