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

    @GetMapping(value = "/workspaces", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getWorkSpaces() {
        return taskService.getWorkSpaces();
    }

    @PostMapping(value = "/maintask", produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskId createMainTask(String workSpaceName, String title) {
        return taskService.createMainTask(workSpaceName, title).getMainTaskId();
    }

    @PutMapping(value = "/task/title", produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateTaskTitle(String workSpaceName, String taskId, String title) {
        taskService.updateTaskTitle(TaskCoordinate.of(workSpaceName, TaskId.of(taskId)), title);
    }

    @PutMapping(value = "/task/remove", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteTask(String workSpaceName, String taskId) {
        taskService.deleteMainTask(TaskCoordinate.of(workSpaceName, TaskId.of(taskId)));
    }

    @PutMapping(value = "/subtask/title", produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateSubTaskTitle(String workSpaceName, String mainTaskId, String subTaskId, String title) {
        taskService.updateSubTaskTitle(TaskCoordinate.of(workSpaceName, TaskId.of(mainTaskId), TaskId.of(subTaskId)), title);
    }

    @PostMapping(value = "/subtask", produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskId createSubTask(String workSpaceName, String mainTaskId, String parentTaskId, String description) {
        TaskCoordinate mainTaskCoordinate = TaskCoordinate.of(workSpaceName, TaskId.of(mainTaskId));
        return taskService.createSubTask(mainTaskCoordinate, TaskId.of(parentTaskId), description).getSubTaskId();
    }

    @GetMapping(value = "/maintask", produces = MediaType.APPLICATION_JSON_VALUE)
    public Task getMainTask(String workSpaceName, String taskId) {
        return taskService.getMainTask(TaskCoordinate.of(workSpaceName, TaskId.of(taskId)));
    }

    @GetMapping(value= "/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskSummaryDTO> list(String workSpaceName) {
        return taskService.list(workSpaceName);
    }

    @PostMapping(value="/agent", produces = MediaType.APPLICATION_JSON_VALUE)
    public AgentSummaryDTO getAgent(String workSpaceName, String mainTaskId, String taskId) {
        return taskService.getAgentSummary(TaskCoordinate.of(workSpaceName, TaskId.of(mainTaskId), TaskId.of(taskId)));
    }

    @PostMapping(value="/usermsg", produces = MediaType.APPLICATION_JSON_VALUE)
    public void userMessage(@RequestBody UserMessageDTO userMessage) {
        taskService.userMessage(TaskCoordinate.of(userMessage.getWorkSpaceName(), TaskId.of(userMessage.getMainTaskId()), TaskId.of(userMessage.getSubTaskId())), userMessage.getMessage());
    }

}
