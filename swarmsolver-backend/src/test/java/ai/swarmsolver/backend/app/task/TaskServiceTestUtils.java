package ai.swarmsolver.backend.app.task;

import ai.swarmsolver.backend.app.task.model.TaskCoordinate;
import ai.swarmsolver.backend.app.task.model.TaskId;
import ai.swarmsolver.backend.app.task.service.TaskService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TaskServiceTestUtils {

    private final TaskService taskService;

    public TaskServiceTestUtils(TaskService taskService) {
        this.taskService = taskService;
    }

    public TaskId createMainTask() {
        String main_task_name = "main-task-" + UUID.randomUUID();
        return taskService.createMainTask(main_task_name).getId();
    }

    public TaskCoordinate createTaskWithSubTask() {
        String sub_task_name = "sub-task-" + UUID.randomUUID();
        TaskId maintaskId = createMainTask();
        TaskId subTaskId = taskService.createSubTask(maintaskId, maintaskId, sub_task_name).getId();
        return TaskCoordinate.of(maintaskId, subTaskId);
    }


}
