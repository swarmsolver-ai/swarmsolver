package ai.swarmsolver.backend.app.task;

import ai.swarmsolver.backend.app.task.model.TaskCoordinate;
import ai.swarmsolver.backend.app.task.service.TaskService;
import ai.swarmsolver.backend.app.TestBase;
import ai.swarmsolver.backend.app.task.model.Task;
import ai.swarmsolver.backend.app.task.model.TaskId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TaskServiceTest extends TestBase {

    @Autowired
    TaskService taskService;

    @Autowired
    TaskServiceTestUtils taskStepDefs;

    @Test
    public void create() {
        // given a main  task name
        String main_task_name = "main-task-" + UUID.randomUUID();

        // when I create a main task
        TaskId main_task_id = taskService.createMainTask(main_task_name).getId();
        printDirStructure("task created");

        // I can find the task by its's main_task_id
        Task task = taskService.getMainTask(main_task_id);

        assertNotNull(task);
        assertEquals(main_task_id, task.getId());
        assertEquals(main_task_name, task.getTitle());
    }
    @Test
    public void editTask() {
        // given a main  task name
        String main_task_name = "main-task-" + UUID.randomUUID();
        TaskId main_task_id = taskService.createMainTask(main_task_name).getId();
        printDirStructure("task created");

        // when I edit the task
        String edited_main_task_name = "edited-main-task-" + UUID.randomUUID();
        taskService.updateTaskTitle(main_task_id, edited_main_task_name);

        // then the task has been edited
        Task task = taskService.getMainTask(main_task_id);
        assertEquals(edited_main_task_name, task.getTitle());

    }

    @Test
    public void delete() {
        // given a main task
        TaskId mainTaskId = taskStepDefs.createMainTask();

        // when I delete it
        printDirStructure("task created");
        taskService.deleteMainTask(mainTaskId);
        printDirStructure("task deleted");


    }


    @Test
    public void createSubTask() {
        // given a main task
        TaskId mainTaskId = taskStepDefs.createMainTask();

        // and a subtask
        String sub_task_name = "sub-task-" + UUID.randomUUID();

        // when I add a subtask directly to the main task, ie parent = main task
        TaskId subTaskId = taskService.createSubTask(mainTaskId, sub_task_name).getId();
        printDirStructure("subtask created");

        // then I can find the sub task via mainTaskId and walk the sub task tree
        {
            Task task = taskService.getMainTask(mainTaskId);
            assertNotNull(task.getSubTasks());
            assertEquals(1, task.getSubTasks().size());

            Task subTask = task.getSubTasks().get(0);
            assertEquals(sub_task_name, subTask.getTitle());
        }

        // and I can find the sub task via mainTaskId and subTaskId
        {
            Task subTask = taskService.getSubTask(mainTaskId, subTaskId);
            assertEquals(sub_task_name, subTask.getTitle());
        }


    }

    @Test
    public void setDescription() {
        // given a task
        TaskId mainTaskId = taskStepDefs.createMainTask();
        TaskId taskId = taskService.createSubTask(mainTaskId, "subtask").getId();
        TaskCoordinate taskCoordinate = TaskCoordinate.of(mainTaskId, taskId);
        // when I set the description
        String description = "description-" + UUID.randomUUID();
        taskService.setDescription(taskCoordinate, description);

        // then the description field is filled in
        Task task = taskService.getSubTask(mainTaskId, taskId);
        assertEquals(description, task.getDescription());

    }

    @Test
    public void setAgentName()  {
        // given a task with a subtask
        TaskId mainTaskId = taskStepDefs.createMainTask();
        TaskId taskId = taskService.createSubTask(mainTaskId, "subtask").getId();
        TaskCoordinate taskCoordinate = TaskCoordinate.of(mainTaskId, taskId);

        // when i set the agent name
        String agentName = "agent-name-" + UUID.randomUUID();
        taskService.setAgentName(taskCoordinate, agentName);

        // then the agent name field is filled in
        Task task = taskService.getSubTask(mainTaskId, taskId);
        assertEquals(agentName, task.getAgentName());

    }


    @Test
    public void sendMessage()  {
        // given a task with a subtask
        TaskId mainTaskId = taskStepDefs.createMainTask();
        TaskId taskId = taskService.createSubTask(mainTaskId, "subtask").getId();
        TaskCoordinate taskCoordinate = TaskCoordinate.of(mainTaskId, taskId);

        // given i set the agent name
        String agentName = "agent-name-" + UUID.randomUUID();
        taskService.setAgentName(taskCoordinate, agentName);

        // when I send a message to the agent
        taskService.userMessage(taskCoordinate, "hello");

    }

}
