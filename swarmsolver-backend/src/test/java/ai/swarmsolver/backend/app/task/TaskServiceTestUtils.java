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

    public TaskCoordinate createMainTask() {
        String main_task_name = "main-task-" + UUID.randomUUID();
        return taskService.createMainTask("default", main_task_name);
    }

    public TaskCoordinate createTaskWithSubTask() {
        String sub_task_name = "sub-task-" + UUID.randomUUID();
        TaskCoordinate mainTaskCoordinate = createMainTask();
        return taskService.createSubTask(mainTaskCoordinate, sub_task_name);
    }


}
