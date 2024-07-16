package ai.swarmsolver.backend.app.task.controller;

import ai.swarmsolver.backend.app.agent.app.AgentSummaryDTO;
import ai.swarmsolver.backend.app.task.dto.TaskSummaryDTO;
import ai.swarmsolver.backend.app.task.dto.UserMessageDTO;
import ai.swarmsolver.backend.app.task.model.Task;
import ai.swarmsolver.backend.app.task.model.TaskCoordinate;
import ai.swarmsolver.backend.app.task.model.TaskId;
import ai.swarmsolver.backend.app.task.service.TaskService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping(value = "/maintask", produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskId createMainTask(String title) {
        return taskService.createMainTask(title).getId();
    }

    @PutMapping(value = "/task/title", produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateTaskTitle(String taskId, String title) {
        taskService.updateTaskTitle(TaskId.of(taskId), title);
    }

    @PutMapping(value = "/task/remove", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteTask(String taskId) {
        taskService.deleteMainTask(TaskId.of(taskId));
    }

    @PutMapping(value = "/subtask/title", produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateSubTaskTitle(String mainTaskId, String subTaskId, String title) {
        taskService.updateSubTaskTitle(TaskCoordinate.of(TaskId.of(mainTaskId), TaskId.of(subTaskId)), title);
    }

    @PostMapping(value = "/subtask", produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskId createSubTask(String mainTaskId, String parentTaskId, String description) {
        return taskService.createSubTask(TaskId.of(mainTaskId), TaskId.of(parentTaskId), description).getId();
    }

    @GetMapping(value = "/maintask", produces = MediaType.APPLICATION_JSON_VALUE)
    public Task getMainTask(String taskId) {
        return taskService.getMainTask(TaskId.of(taskId));
    }

    @GetMapping(value= "/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskSummaryDTO> list() {
        return taskService.list();
    }

    @PostMapping(value="/agent", produces = MediaType.APPLICATION_JSON_VALUE)
    public AgentSummaryDTO getAgent(String mainTaskId, String taskId) {
        return taskService.getAgentSummary(TaskCoordinate.of(TaskId.of(mainTaskId), TaskId.of(taskId)));
    }

    @PostMapping(value="/usermsg", produces = MediaType.APPLICATION_JSON_VALUE)
    public void userMessage(@RequestBody UserMessageDTO userMessage) {
        taskService.userMessage(TaskCoordinate.of(TaskId.of(userMessage.getMainTaskId()), TaskId.of(userMessage.getSubTaskId())), userMessage.getMessage());
    }

}
