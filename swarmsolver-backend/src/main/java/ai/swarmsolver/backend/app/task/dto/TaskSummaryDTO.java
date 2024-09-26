package ai.swarmsolver.backend.app.task.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class TaskSummaryDTO {
    private String id;
    private String title;
    private boolean archived;
    private boolean favorite;
    private LocalDateTime created;
    private LocalDateTime lastUpdated;
}
