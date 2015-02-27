package net.itimothy.rest.description;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Info {
    @NonNull
    private String version;

    private String title;

    private String description;
}
