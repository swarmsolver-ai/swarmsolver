package ai.swarmsolver.backend.app.task.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskCoordinate {

    private String workSpaceName;

    private TaskId mainTaskId;

    private TaskId subTaskId;

    private TaskCoordinate() {
    }

    static public TaskCoordinate of(String workSpaceName, TaskId mainTaskId, TaskId subTaskId) {
        return new TaskCoordinate(workSpaceName, mainTaskId, subTaskId);
    }


    static public TaskCoordinate of(String workSpaceName, TaskId mainTaskId) {
        return new TaskCoordinate(workSpaceName, mainTaskId, null);
    }
}
