package ai.swarmsolver.backend.app.task.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class FilterDTO {
    private boolean archived;
    private boolean favorite;
    private String name;
}
