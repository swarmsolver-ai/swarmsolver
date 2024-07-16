package ai.swarmsolver.backend.app.task.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskCoordinate {

    private TaskId mainTaskId;

    private TaskId subTaskId;

    private TaskCoordinate() {
    }

    static public TaskCoordinate of(TaskId mainTaskId, TaskId subTaskId) {
        return new TaskCoordinate(mainTaskId, subTaskId);
    }


    static public TaskCoordinate of(TaskId mainTaskId) {
        return new TaskCoordinate(mainTaskId, null);
    }
}
