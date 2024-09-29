package ai.swarmsolver.backend.app.task.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class TaskSummaryListDTO {
    private String workspace;
    private List<TaskSummaryDTO> summaries;
    private FilterDTO filtering;
    private SortingDTO sorting;
}
