package ai.swarmsolver.backend.app.task.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class TaskSummaryDTO {
    private String id;
    private String title;
}