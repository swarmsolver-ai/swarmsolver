package ai.swarmsolver.backend.app.task.service;

import ai.swarmsolver.backend.app.agent.app.AgentService;
import ai.swarmsolver.backend.app.agent.app.AgentSummaryDTO;
import ai.swarmsolver.backend.app.agent.domain.Agent;
import ai.swarmsolver.backend.app.agent.domain.AgentCoordinate;
import ai.swarmsolver.backend.app.agent.domain.AgentId;
import ai.swarmsolver.backend.app.task.dto.TaskSummaryDTO;
import ai.swarmsolver.backend.app.task.model.Task;
import ai.swarmsolver.backend.app.task.model.TaskCoordinate;
import ai.swarmsolver.backend.app.task.model.TaskId;
import ai.swarmsolver.backend.app.task.model.TaskState;
import ai.swarmsolver.backend.app.task.repository.TaskRepository;
import ai.swarmsolver.backend.app.task.repository.TaskWorkspace;
import ai.swarmsolver.backend.infra.DirectoryStructure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class TaskService {

    private final DirectoryStructure directoryStructure;

    private final TaskRepository taskRepository;

    private final AgentService agentService;

    public TaskService(TaskRepository taskRepository, AgentService agentService, DirectoryStructure directoryStructure) {
        this.directoryStructure = directoryStructure;
        this.taskRepository = taskRepository;
        this.agentService = agentService;
    }

    public Task getMainTask(TaskCoordinate taskCoordinate) {
        return taskRepository.fetch(taskCoordinate);
    }

    public TaskCoordinate createMainTask(String workSpaceName, String title) {
        Task task = createTask(title);
        TaskCoordinate taskCoordinate = TaskCoordinate.of(workSpaceName, task.getId());
        taskRepository.store(taskCoordinate, task);
        TaskWorkspace workspace = TaskWorkspace.of(directoryStructure, taskCoordinate);
        workspace.getMainTaskDir().mkdirs();
        return taskCoordinate;
    }

    public void deleteMainTask(TaskCoordinate taskCoordinate) {
        TaskWorkspace taskWorkspace = TaskWorkspace.of(directoryStructure, taskCoordinate);
        taskWorkspace.getMainTaskFileLocation().delete();
        FileSystemUtils.deleteRecursively(taskWorkspace.getMainTaskDir());
    }

    private Task createTask(String title) {
        return Task.builder()
                .id(TaskId.of("task-" + UUID.randomUUID().toString()))
                .state(TaskState.CREATED)
                .title(title)
                .build();
    }

    public TaskCoordinate createSubTask(TaskCoordinate taskCoordinate, String title) {
        return this.createSubTask(taskCoordinate, taskCoordinate.getMainTaskId(), title);
    }

    public TaskCoordinate createSubTask(TaskCoordinate mainTaskCoordinate, TaskId parentTaskId, String title) {
        Task mainTask = taskRepository.fetch(mainTaskCoordinate);
        Task parentTask = findSubTask(mainTask, parentTaskId);
        Task subTask = createTask(title);
        if (parentTask.getSubTasks() == null) {
            parentTask.setSubTasks(new ArrayList<>());
        }
        parentTask.getSubTasks().add(subTask);
        taskRepository.store(mainTaskCoordinate, parentTask);

        TaskCoordinate subTaskCoordinate = TaskCoordinate.of(mainTaskCoordinate.getWorkSpaceName(), mainTaskCoordinate.getMainTaskId(), subTask.getId());

        TaskWorkspace workspace = TaskWorkspace.of(directoryStructure, subTaskCoordinate);
        workspace.getSubTaskDir().mkdirs();


        return subTaskCoordinate;
    }

    private Task findSubTask(Task task, TaskId taskId) {
        Task subTask = recursiveFindSubTask(task, taskId);
        if (subTask != null) return subTask;
        throw new IllegalArgumentException("no subtask");
    }

    private Task recursiveFindSubTask(Task task, TaskId taskId) {
        if (task.getId().equals(taskId)) {
            return task;
        }
        if (task.getSubTasks() != null) {
            for (Task subTask : task.getSubTasks()) {
                if (recursiveFindSubTask(subTask, taskId) != null) {
                    return subTask;
                }
            }
        }
        return null;
    }


    public void setDescription(TaskCoordinate taskCoordinate, String description) {
        Task mainTask = taskRepository.fetch(taskCoordinate);
        Task task = findSubTask(mainTask, taskCoordinate.getSubTaskId());
        task.setDescription(description);
        taskRepository.store(taskCoordinate, mainTask);
    }

    public void setAgentName(TaskCoordinate taskCoordinate, String agentName) {
        Task mainTask = taskRepository.fetch(taskCoordinate);
        Task task = findSubTask(mainTask, taskCoordinate.getSubTaskId());
        task.setAgentName(agentName);
        taskRepository.store(taskCoordinate, mainTask);
    }


    public Task getSubTask(TaskCoordinate subTaskCoordinate) {
        Task mainTask = taskRepository.fetch(subTaskCoordinate);
        return findSubTask(mainTask, subTaskCoordinate.getSubTaskId());
    }

    public List<TaskSummaryDTO> list(String workSpaceName) {
        return taskRepository.getMainTaskSummaries(TaskCoordinate.of(workSpaceName, null));
    }

    public void updateTaskTitle(TaskCoordinate taskCoordinate, String title) {
        taskRepository.updateTaskTitle(taskCoordinate, title);
    }

    public void updateSubTaskTitle(TaskCoordinate taskCoordinate, String title) {
        taskRepository.updateSubTaskTitle(taskCoordinate, title);
    }

    public AgentSummaryDTO getAgentSummary(TaskCoordinate taskCoordinate) {
        Agent agent = getAgent(taskCoordinate);
        return AgentSummaryDTO.builder()
                .agentId(agent.getAgentCoordinate().getAgentId())
                .conversationCoordinate(agent.getConversationCoordinate())
                .build();
    }

    private Agent getAgent(TaskCoordinate taskCoordinate) {
        Task mainTask = getMainTask(taskCoordinate);
        Task subTask = findSubTask(mainTask, taskCoordinate.getSubTaskId());
        AgentId agentId = subTask.getAgentId();
        String agentName = subTask.getAgentName();

        if (agentId == null) {
            agentId = agentService.createAgent(agentName, taskCoordinate).getAgentCoordinate().getAgentId();
            subTask.setAgentId(agentId);
            taskRepository.store(taskCoordinate, mainTask);
        }
        AgentCoordinate agentCoordinate = AgentCoordinate.of(taskCoordinate, agentId);
        return agentService.getAgent(agentCoordinate);
    }

    @Async
    public void userMessage(TaskCoordinate taskCoordinate, String message) {
        getAgent(taskCoordinate).handleMessage(message);
    }

    public List<String> getWorkSpaces() {
        return directoryStructure.getWorkSpaces().keySet().stream()
                .sorted()
                .toList();
    }
}
