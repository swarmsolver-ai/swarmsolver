package ai.swarmsolver.backend.app.task;

import ai.swarmsolver.backend.app.TestBase;
import ai.swarmsolver.backend.app.task.dto.FilterDTO;
import ai.swarmsolver.backend.app.task.dto.TaskSummaryDTO;
import ai.swarmsolver.backend.app.task.model.Task;
import ai.swarmsolver.backend.app.task.model.TaskCoordinate;
import ai.swarmsolver.backend.app.task.service.TaskService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class TaskServiceTest extends TestBase {

    @Autowired
    TaskService taskService;

    @Autowired
    TaskServiceTestUtils taskStepDefs;

    String workSpaceName = "default";

    @Test
    public void create() {
        // given a main  task name
        String main_task_name = "main-task-" + UUID.randomUUID();

        // when I create a main task
        TaskCoordinate taskCoordinate = taskService.createMainTask(workSpaceName, main_task_name);
        printDirStructure("task created");

        // I can find the task by its's main_task_id
        Task task = taskService.getMainTask(taskCoordinate);

        assertNotNull(task);
        assertEquals(taskCoordinate.getMainTaskId(), task.getId());
        assertEquals(main_task_name, task.getTitle());
    }

    @Test
    public void editTask() {
        // given a main  task name
        String main_task_name = "main-task-" + UUID.randomUUID();
        TaskCoordinate main_task_id = taskService.createMainTask(workSpaceName, main_task_name);
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
        TaskCoordinate taskC = taskStepDefs.createMainTask();

        // when I delete it
        printDirStructure("task created");
        taskService.deleteMainTask(taskC);
        printDirStructure("task deleted");


    }


    @Test
    public void createSubTask() {
        // given a main task
        TaskCoordinate mainTaskCoordinate = taskStepDefs.createMainTask();

        // and a subtask
        String sub_task_name = "sub-task-" + UUID.randomUUID();
        String agent_name = "agent-name";

        // when I add a subtask directly to the main task, ie parent = main task
        TaskCoordinate subTaskCoordinate = taskService.createSubTask(mainTaskCoordinate, sub_task_name, agent_name);
        printDirStructure("subtask created");

        // then I can find the sub task via mainTaskId and walk the sub task tree
        {
            Task task = taskService.getMainTask(mainTaskCoordinate);
            assertNotNull(task.getSubTasks());
            assertEquals(1, task.getSubTasks().size());

            Task subTask = task.getSubTasks().get(0);
            assertEquals(sub_task_name, subTask.getTitle());
            assertEquals(agent_name, subTask.getAgentName());
        }

        // and I can find the sub task via mainTaskId and subTaskCoordinate
        {
            Task subTask = taskService.getSubTask(subTaskCoordinate);
            assertEquals(sub_task_name, subTask.getTitle());
        }


    }

    @Test
    public void setDescription() {
        // given a task
        TaskCoordinate mainTaskCoordinate = taskStepDefs.createMainTask();
        TaskCoordinate taskCoordinate = taskService.createSubTask(mainTaskCoordinate, "subtask", "agent-name");
        // when I set the description
        String description = "description-" + UUID.randomUUID();
        taskService.setDescription(taskCoordinate, description);

        // then the description field is filled in
        Task task = taskService.getSubTask(taskCoordinate);
        assertEquals(description, task.getDescription());

    }

    @Test
    public void updateTaskTitle() {
        // given a task
        TaskCoordinate mainTaskCoordinate = taskStepDefs.createMainTask();
        printDirStructure("task created");

        // when I update the title
        String title = "title-" + UUID.randomUUID();
        taskService.updateTaskTitle(mainTaskCoordinate, title);
        printDirStructure("title updated");


        // then the title is updated
        Task task = taskService.getMainTask(mainTaskCoordinate);
        assertEquals(title, task.getTitle());

    }

    @Test
    public void archiveTask() {

        String workSpaceName = "default";

        // given a task
        TaskCoordinate mainTaskCoordinate = taskStepDefs.createMainTask();
        printDirStructure("task created");

        {
            // then the task is not archived
            Task task = taskService.getMainTask(mainTaskCoordinate);
            assertFalse(task.isArchived());
            // and the task list filtered without archived filter shows the task because it is not archived
            assertEquals(1, taskService.list(workSpaceName, FilterDTO.builder().build()).size());
            // and the task list filtered with archived filter does show the task, archived filter shows both archived and non archived
            assertEquals(1, taskService.list(workSpaceName, FilterDTO.builder().archived(true).build()).size());
        }

        // when I archive the task
        taskService.updateTaskArchived(mainTaskCoordinate, true);
        printDirStructure("archived");


        {
            // then the task is archived
            Task task = taskService.getMainTask(mainTaskCoordinate);
            assertTrue(task.isArchived());
            // and the task list filtered without archived filter does not show the task
            assertEquals(0, taskService.list(workSpaceName, FilterDTO.builder().build()).size());
            // and the task list filtered with archived filter does show the task, archived filter shows both archived and non archived
            assertEquals(1, taskService.list(workSpaceName, FilterDTO.builder().archived(true).build()).size());
        }

    }

    @Test
    public void favoriteTask() {
        // given a task
        TaskCoordinate mainTaskCoordinate = taskStepDefs.createMainTask();
        printDirStructure("task created");

        // then the task is not favorite
        {
            Task task = taskService.getMainTask(mainTaskCoordinate);
            assertFalse(task.isFavorite());
            // and the task list filtered without "only favorites" filter shows the task (a false fav filter lists both favorite an non favorite)
            assertEquals(1, taskService.list(workSpaceName, FilterDTO.builder().build()).size());
            // and the task list filtered with fav filter does not show the task because it is not favorite
            assertEquals(0, taskService.list(workSpaceName, FilterDTO.builder().favorite(true).build()).size());
        }

        // when I mark the task as  favorite
        taskService.updateTaskFavorite(mainTaskCoordinate, true);
        printDirStructure("favorite");


        // then the task is favorite
        {
            Task task = taskService.getMainTask(mainTaskCoordinate);
            assertTrue(task.isFavorite());
            // and the task list filtered without fav filter shows the task (a false fav filter lists both favorite an non favorite)
            assertEquals(1, taskService.list(workSpaceName, FilterDTO.builder().build()).size());
            // and the task list filtered with fav filter does show the task
            assertEquals(1, taskService.list(workSpaceName, FilterDTO.builder().favorite(true).build()).size());

        }

    }



    @Test
    public void setAgentName() {
        // given a task with a subtask
        TaskCoordinate mainTaskCoordinate = taskStepDefs.createMainTask();
        TaskCoordinate subTaskCoordinate = taskService.createSubTask(mainTaskCoordinate, "subtask", "agent-name");

        // when i set the agent name
        String agentName = "agent-name-" + UUID.randomUUID();
        taskService.setAgentName(subTaskCoordinate, agentName);

        // then the agent name field is filled in
        Task task = taskService.getSubTask(subTaskCoordinate);
        assertEquals(agentName, task.getAgentName());

    }


    @Test
    public void sendMessage() {
        // given a task with a subtask
        TaskCoordinate mainTaskCoordinate = taskStepDefs.createMainTask();
        TaskCoordinate subTaskCoordinate = taskService.createSubTask(mainTaskCoordinate, "subtask", "agent-name");

        // given i set the agent name
        String agentName = "agent-name-" + UUID.randomUUID();
        taskService.setAgentName(subTaskCoordinate, agentName);

        // when I send a message to the agent
        taskService.userMessage(subTaskCoordinate, "hello");

    }

    @Test
    public void sortByName() {
        // given 10 random task names
        List<String> randomNames = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            randomNames.add(UUID.randomUUID().toString());
        }

        // and I create tasks for each name
        randomNames.forEach(name -> taskService.createMainTask("default", name));

        // when I list the main tasks
        List<TaskSummaryDTO> list = taskService.list("default", FilterDTO.builder().build());

        // then  the summaries are ordered alphabetically by their titles
        Collections.sort(randomNames);
        for (int i = 0; i < randomNames.size(); i++) {
            assertEquals(randomNames.get(i), list.get(i).getTitle());
        }
    }
}
