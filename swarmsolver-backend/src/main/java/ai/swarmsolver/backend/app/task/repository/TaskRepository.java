package ai.swarmsolver.backend.app.task.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ai.swarmsolver.backend.app.task.model.Task;
import ai.swarmsolver.backend.app.task.model.TaskCoordinate;
import ai.swarmsolver.backend.app.task.model.TaskId;
import ai.swarmsolver.backend.infra.DirectoryStructure;
import ai.swarmsolver.backend.app.task.dto.TaskSummaryDTO;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskRepository {

    private ObjectMapper objectMapper;

    private final DirectoryStructure directoryStructure;

    public TaskRepository(DirectoryStructure directoryStructure) {
        this.directoryStructure = directoryStructure;
        initObjectMapper();
    }

    private void initObjectMapper() {
        objectMapper = new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT);
    }

    @SneakyThrows
    public void store(Task task) {
        TaskWorkspace taskWorkspace = TaskWorkspace.of(directoryStructure, TaskCoordinate.of(task.getId()));
        objectMapper.writeValue(taskWorkspace.getMainTaskFileLocation(task.getId()), task);
    }

    @SneakyThrows
    public Task fetch(TaskId id) {
        TaskWorkspace taskWorkspace = TaskWorkspace.of(directoryStructure, TaskCoordinate.of(id));
        return objectMapper.readValue(taskWorkspace.getMainTaskFileLocation(id), Task.class);
    }


    @SneakyThrows
    public List<TaskSummaryDTO> getMainTaskSummaries() {
        TaskWorkspace taskWorkspace = TaskWorkspace.of(directoryStructure);

        List<File> taskFiles = taskWorkspace.listMainTaskFileLocations();

        return taskFiles.stream()
                .map(this::getSummary)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private TaskSummaryDTO getSummary(File file) {
        Task task = objectMapper.readValue(file, Task.class);
        TaskId taskId = task.getId();
        String title = task.getTitle();
        return new TaskSummaryDTO(taskId.getIdentifier(), title);
    }

    private Task findSubTask(Task task, TaskId taskId) {
        if (task.getId().equals(taskId)) {
            return task;
        }
        if (task.getSubTasks() != null) {
            for(Task subTask : task.getSubTasks()) {
                if (findSubTask(subTask, taskId) != null) {
                    return subTask;
                }
            }
        }
        return null;
    }

    public void updateTaskTitle(TaskId taskId, String title) {
        Task task = fetch(taskId);
        task.setTitle(title);
        this.store(task);
    }

    public void updateSubTaskTitle(TaskCoordinate taskCoordinate, String title) {
        Task mainTask = fetch(taskCoordinate.getMainTaskId());
        Task subTask = findSubTask(mainTask, taskCoordinate.getSubTaskId());
        subTask.setTitle(title);
        this.store(mainTask);
    }

}