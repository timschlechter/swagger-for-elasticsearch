package org.elasticsearch.plugin.swagger.v1_2.model.apiDeclaration;

import lombok.*;
import org.elasticsearch.plugin.swagger.v1_2.model.SwaggerModel;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Api extends SwaggerModel {
    /**
     * The relative path to the operation, from the basePath, which this operation describes.
     */
    @NonNull
    private String path;

    /**
     * A short description of the resource.
     */
    private String description;

    /**
     * A list of the API operations available on this path.
     */
    private List<Operation> operations;
}
