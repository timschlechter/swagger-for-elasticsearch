package net.itimothy.rest.metadata.model;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Model {
    @NonNull
    private String id;

    @NonNull
    @Singular("property")
    private List<Property> properties;

    private String description;
    
    public boolean isPrimitive() {
        return false;
    }
}
