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
import java.util.Comparator;
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
    public void store(TaskCoordinate taskCoordinate, Task task) {
        TaskWorkspace taskWorkspace = TaskWorkspace.of(directoryStructure, taskCoordinate);
        objectMapper.writeValue(taskWorkspace.getMainTaskFileLocation(), task);
    }

    @SneakyThrows
    public Task fetch(TaskCoordinate taskCoordinate) {
        TaskWorkspace taskWorkspace = TaskWorkspace.of(directoryStructure, taskCoordinate);
        return objectMapper.readValue(taskWorkspace.getMainTaskFileLocation(), Task.class);
    }


    @SneakyThrows
    public List<TaskSummaryDTO> getMainTaskSummaries(TaskCoordinate taskCoordinate) {
        TaskWorkspace taskWorkspace = TaskWorkspace.of(directoryStructure, taskCoordinate);

        List<File> taskFiles = taskWorkspace.listMainTaskFileLocations();

        return taskFiles.stream()
                .map(this::getSummary)
                .sorted(Comparator.comparing(TaskSummaryDTO::getTitle))
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

    public void updateTaskTitle(TaskCoordinate taskCoordinate, String title) {
        Task task = fetch(taskCoordinate);
        task.setTitle(title);
        this.store(taskCoordinate, task);
    }

    public void updateSubTaskTitle(TaskCoordinate taskCoordinate, String title) {
        Task mainTask = fetch(taskCoordinate);
        Task subTask = findSubTask(mainTask, taskCoordinate.getSubTaskId());
        subTask.setTitle(title);
        this.store(taskCoordinate, mainTask);
    }

}
