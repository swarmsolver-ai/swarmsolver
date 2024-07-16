package ai.swarmsolver.backend.app.task.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserMessageDTO {
    private String mainTaskId;
    private String subTaskId;
    private String message;

}
