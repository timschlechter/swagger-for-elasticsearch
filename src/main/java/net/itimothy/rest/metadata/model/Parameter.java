package net.itimothy.rest.metadata.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Parameter {
    @NonNull
    public ParameterType paramType;

    public String name;
    
    public String description;

    public Boolean required;

    public Object defaultValue;

    public Model model;
}
