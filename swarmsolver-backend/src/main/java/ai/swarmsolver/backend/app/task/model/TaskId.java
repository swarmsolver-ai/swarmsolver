package ai.swarmsolver.backend.app.task.model;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class TaskId {

    private String identifier;

    public static TaskId of(String identifier) {
        return new TaskId(identifier);
    }
}
