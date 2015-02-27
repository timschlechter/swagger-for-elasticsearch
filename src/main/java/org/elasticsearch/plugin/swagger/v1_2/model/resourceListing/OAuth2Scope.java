package org.elasticsearch.plugin.swagger.v1_2.model.resourceListing;

import lombok.*;
import org.elasticsearch.plugin.swagger.v1_2.model.SwaggerModel;

/**
 * Describes an OAuth2 authorization scope.
 */
@Data
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class OAuth2Scope extends SwaggerModel {
    /**
     * The name of the scope.
     */
    @NonNull
    private String scope;

    /**
     * A short description of the scope.
     */
    private String description;
}